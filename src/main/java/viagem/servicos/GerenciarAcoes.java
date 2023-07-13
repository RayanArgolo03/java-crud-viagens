package viagem.servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;
import viagem.entidades.*;
import viagem.enums.*;
import viagem.exceptions.GerenciadorException;

public final class GerenciarAcoes {

    private GerenciadorViagens gv;

    private static final Scanner SC = new Scanner(System.in);

    public GerenciarAcoes() {
    }

    public GerenciadorViagens getGv() {
        return gv;
    }

    //Injeta a dependência diretamente no GV
    public void setGv(GerenciadorViagens gv) {
        this.gv = gv;
    }

    //Hub para todas as ações
    public void gerenciarAcoes(int opcAcao) {

        if (opcAcao == OpcoesMenu.VIAJAR.getId()) {
            gerenciarViagem();
        } else if (opcAcao == OpcoesMenu.ALTERAR_VIAGEM.getId()) {
            gerenciarAlteracao();
        } else if (opcAcao == OpcoesMenu.EXLUIR_VIAGEM.getId()) {
            gerenciarExclusao();
        } //Imprimir viagem
        else {
            gerenciarImpressaoViagens();
        }

    }

    //Caso opção escolhida seja de viajar
    private void gerenciarViagem() {
        
        int qtdServicosViagem = TipoServicoViagem.values().length;
        imprimirOpcoesServico();
        
        System.out.print("Sua escolha: ");
        int opcServicoViagem = SC.nextInt();
        
        if (!opcValida(opcServicoViagem, qtdServicosViagem)) {
            throw new GerenciadorException("Selecione serviço de viagem existente! (1 a " + qtdServicosViagem + ")");
        }

        //Gera enum dada opção de serviço escolhida
        TipoServicoViagem tsv = gerarServicoViagem(opcServicoViagem);

        //Informações do destino
        System.out.println();
        System.out.println("Informações do destino: ");
        System.out.println("Estados disponíveis: ");

        //Imprimir todos os estados disponíveis
        int qtdSiglasEstado = SiglaEstado.values().length;
        imprimirOpcoesEstado();
        
        System.out.print("Sua escolha: ");
        int opcSiglaEstado = SC.nextInt();

        //È um estado válido
        if (!opcValida(opcSiglaEstado, qtdSiglasEstado)) {
            throw new GerenciadorException("Estado inválido! (1 a " + qtdSiglasEstado + ")");
        }

        //Gerou sigla de estado dada escolha 
        SiglaEstado se = gerarSiglaEstado(opcSiglaEstado);

        //Gerar cidades possíveis no estado escolhido
        List<Destino> possiveisDestinos = getGv().iniciarDestinos(se, tsv);
        
        List<Cidade> cidadesDisponiveis = possiveisDestinos.stream().map(Destino::getCidade).toList();
        
        int qtdCidadesDisponiveis = cidadesDisponiveis.size();
        imprimirCidadesDisponiveis(cidadesDisponiveis, se, tsv);
        
        System.out.print("Sua escolha: ");
        int opcCidade = SC.nextInt();
        
        if (!opcValida(opcCidade, qtdCidadesDisponiveis)) {
            throw new GerenciadorException("Cidade inválida! (1 a " + qtdCidadesDisponiveis + ")");
        }

        //Filtro na lista cidade escolhida
        Cidade cidadeEscolhida = cidadesDisponiveis.stream()
                .filter(cidade -> cidadesDisponiveis.indexOf(cidade) == opcCidade - 1)
                .findFirst()
                .get();

        //Imprimindo Locais disponíveis
        List<Local> locaisDisponiveis = cidadeEscolhida.getLocais();
        
        int qtdLocaisDisponiveis = locaisDisponiveis.size();
        cidadeEscolhida.imprimirLocaisDisponiveis(cidadeEscolhida.getNome());
        
        System.out.print("Sua escolha: ");
        int opcLocal = SC.nextInt();
        
        if (!opcValida(opcLocal, qtdLocaisDisponiveis)) {
            throw new GerenciadorException("Local inválido! (1 a " + qtdLocaisDisponiveis + ")");
        }
        
        Local localEscolhido = locaisDisponiveis.stream()
                .filter(local -> locaisDisponiveis.indexOf(local) == opcLocal - 1)
                .findFirst()
                .get();
        
        Destino d = new Destino(cidadeEscolhida, localEscolhido);
        
        System.out.println();
        System.out.println("Data de ida: (DD/MM/AAAA)");
        LocalDate dataEmbarque = LocalDate.parse(SC.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.println();
        System.out.println("Data de retorno: (DD/MM/AAAA)");
        LocalDate dataRetorno = LocalDate.parse(SC.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //Verificar se datas são iguais ou ida antes de retorno
        if (!datasValidas(dataEmbarque, dataRetorno)) {
            throw new GerenciadorException("Datas inválidas! Data de ida não pode ser igual nem depois da data de retorno!");
        }

        //Verificar se viagem já existe
        if (!dataViagemNaoExiste(dataEmbarque, dataRetorno)) {
            throw new GerenciadorException("Já existe viagem com data de ida ou de retorno confirmada nessa data!");
        }
        
        System.out.println();
        getGv().viajar(tsv, d, dataEmbarque, dataRetorno);
        
    }

    private boolean opcValida(int opc, int qtd) {
        return opc > 0 && opc <= qtd;
    }

    //Verifica se existe viagem com data embarque e retorno na lista de viagens 
    private boolean dataViagemNaoExiste(LocalDate dataEmbarque, LocalDate dataRetorno) {

        ViagemAbstrata dataIdaViagemRealizada = getGv().getViagensRealizadas()
                .stream()
                .filter(viagem -> viagem.getDataEmbarque().equals(dataEmbarque) || viagem.getDataEmbarque().equals(dataRetorno) ||
                        viagem.getDataRetorno().equals(dataEmbarque) || viagem.getDataRetorno().equals(dataRetorno))
                .findFirst().orElse(null);

        return dataIdaViagemRealizada == null;
    }
    
    //Verifica se existe viagem com data alterada na lista de viagens sem ser a própria viagem alterada
    private boolean dataViagemNaoExiste(LocalDate dataAlterada, ViagemAbstrata viagemAntiga) {

        ViagemAbstrata dataIdaViagemRealizada = getGv().getViagensRealizadas()
                .stream()
                .filter(viagem -> viagem.getDataEmbarque().equals(dataAlterada) && !viagem.equals(viagemAntiga))
                .findFirst().orElse(null);

        return dataIdaViagemRealizada == null;
    }

    //Usada para comparar datas ao instanciar nova viagem
    private boolean datasValidas(LocalDate dataEmbarque, LocalDate dataRetorno) {
        return dataEmbarque.isBefore(dataRetorno) && !dataEmbarque.isEqual(dataRetorno);
    }
    
    //Usada para comparar datas ao modificar viagem realizada - Nova data não pode ser antes nem na mesma data da antiga
    private boolean dataValida(LocalDate novaData, LocalDate dataAntiga) {
        return novaData.isAfter(dataAntiga) && !novaData.isEqual(dataAntiga);
    }
    
    
    public void gerenciarAlteracao() {

        if (!temViagem()) {
            throw new GerenciadorException("Você ainda não realizou nenhuma viagem!");
        }

        System.out.println();
        
        int qtdViagensRealizadas = getGv().getViagensRealizadas().size();
        getGv().imprimirViagens();

        System.out.println();
        System.out.print("Sua escolha: ");
        int opcViagem = SC.nextInt();

        if (!opcValida(opcViagem, qtdViagensRealizadas)) {
            throw new GerenciadorException("Selecione opção válido! (1 a " + qtdViagensRealizadas + ")");
        }

        //Filtra viagem escolhida pelo index
        ViagemAbstrata viagem = getGv().getViagensRealizadas().stream()
                .filter(v -> getGv().getViagensRealizadas().indexOf(v) == opcViagem - 1)
                .findFirst().get();
        
        
         //Índice de viagem antiga
        int indexViagemAntiga = IntStream.range(0, getGv().getViagensRealizadas().size())
                .filter(indice -> getGv().getViagensRealizadas().get(indice).equals(viagem))
                .findFirst().getAsInt();
        
        
        imprimirOpcoesAlteracao();

        System.out.print("Sua escolha: ");
        int opcAlteracaoViagem = SC.nextInt();

        //Hard code pois não teriam mais opções
        if (!opcValida(opcAlteracaoViagem, 3)) {
            throw new GerenciadorException("Digite opção válida! (1 a 3)");
        }
                
        AlteracaoViagem av = gerarAlteracaoViagem(opcAlteracaoViagem);
        
        //Gerar nova viagem data escolha feita
        ViagemAbstrata novaViagem = gerenciarEscolhaAlteracaoViagem(av, viagem);
        
        getGv().alterarViagem(av, indexViagemAntiga, novaViagem);
    }

    private boolean temViagem() {
        return !getGv().getViagensRealizadas().isEmpty();
    }

    private ViagemAbstrata gerenciarEscolhaAlteracaoViagem(AlteracaoViagem av, ViagemAbstrata viagem) {

        ViagemAbstrata novaViagem = null;

        switch (av) {
            case ALTERAR_DESTINO -> {
                novaViagem = gerarNovaViagem(viagem);
            }
            case ALTERAR_EMBARQUE -> {
                novaViagem = gerarNovaViagem(av, viagem, viagem.getDataEmbarque());
            }
            case ALTERAR_RETORNO -> {
                novaViagem = gerarNovaViagem(av, viagem, viagem.getDataRetorno());
            }
        }

        return novaViagem;
    }

    private void gerenciarExclusao() {

        if (!temViagem()){
            throw new GerenciadorException("Não existem viagens para serem excluídas!");
        }
        
        int qtdViagens = getGv().getViagensRealizadas().size();
        getGv().imprimirViagens();
        
        System.out.print("Sua escolha: ");
        int opcExclusaoViagem = SC.nextInt();
        
        if (!opcValida(opcExclusaoViagem, qtdViagens)){
            throw new GerenciadorException("Selecione viagem válida! (1 a" +qtdViagens+")");
        }
        
        ViagemAbstrata viagemExcluida = getGv().getViagensRealizadas().stream().
                filter(viagem -> getGv().getViagensRealizadas().indexOf(viagem) == opcExclusaoViagem - 1)
                .findFirst().get();
        
        
        TipoServicoViagem tsv = (viagemExcluida instanceof HurbViagem) 
                ? TipoServicoViagem.HURB
                : TipoServicoViagem.MILHAS;
        
        getGv().excluirViagem(tsv, viagemExcluida.getDestino().getCidade().getSiglaEstado(), viagemExcluida);
        
    }

    private void gerenciarImpressaoViagens() {
        
        if (!temViagem()){
            throw new GerenciadorException("Ainda não foram feitas viagens!");
        }
        
        getGv().imprimirViagens();

    }
    
    private void imprimirOpcoesEstado() {

        int i = 0;
        for (SiglaEstado s : SiglaEstado.values()) {

            System.out.print((i + 1) + " - " + s);
            System.out.println();

            i++;
        }

    }

    //Método imprimir opções de serviços para viajar
    private void imprimirOpcoesServico() {
        
        System.out.println();
        int i = 0;
        for (TipoServicoViagem tsv : TipoServicoViagem.values()) {
            System.out.println((i + 1) + " - " + tsv);
            i++;
        }
    }

    private void imprimirCidadesDisponiveis(List<Cidade> cidadesDisponiveis, SiglaEstado se, TipoServicoViagem tsv) {

        int i = 0;
        
        System.out.println();
        System.out.println("Cidades disponíveis para viagem no estado " + se + " com o serviço de viagens " + tsv);

        for (Cidade cidade : cidadesDisponiveis) {
            System.out.println((i + 1) + " - " + cidade);
            i++;
        }

    }

    
    private void imprimirOpcoesAlteracao() {

        String[] opcoes = new String[]{"destino", "data de embarque", "data de retorno"};
        int i = 0;
        
        System.out.println();
        for (String o : opcoes) {

            System.out.println((i + 1) + " - Alterar " + o);

            i++;
        }

    }

    private SiglaEstado gerarSiglaEstado(int opcSiglaEstado) {

        SiglaEstado se = null;

        switch (opcSiglaEstado) {

            case 1 -> {
                se = SiglaEstado.RJ;
            }
            case 2 -> {
                se = SiglaEstado.SP;
            }
            case 3 -> {
                se = SiglaEstado.BSB;
            }
            case 4 -> {
                se = SiglaEstado.BA;
            }
        }

        return se;
    }

    private AlteracaoViagem gerarAlteracaoViagem(int opcAlteracaoViagem) {

        AlteracaoViagem av = null;

        switch (opcAlteracaoViagem) {

            case 1 -> {
                av = AlteracaoViagem.ALTERAR_DESTINO;
            }
            case 2 -> {
                av = AlteracaoViagem.ALTERAR_EMBARQUE;
            }
            case 3 -> {
                av = AlteracaoViagem.ALTERAR_RETORNO;
            }

        }

        return av;
    }

    private TipoServicoViagem gerarServicoViagem(int opcServicoViagem) {

        TipoServicoViagem tv = (opcServicoViagem == TipoServicoViagem.HURB.getId())
                ? TipoServicoViagem.HURB
                : TipoServicoViagem.MILHAS;

        return tv;
    }

    //Gerar nova viagem com novo local
    private ViagemAbstrata gerarNovaViagem(ViagemAbstrata viagem) {
        
        System.out.println();
        System.out.println("Dados da cidade e local antigos: ");
        System.out.println(viagem.getDestino());
        
        List<Local> locaisSemAtual = getGv().getViagensRealizadas().stream()
                .map(ViagemAbstrata::getDestino)
                .map(Destino::getCidade)
                .flatMap(cidade -> cidade.getLocais().stream())
                .filter(local -> !local.equals(viagem.getDestino().getLocal()))
                .toList();
        
        int qtdLocais = locaisSemAtual.size();
        int i = 0;
        
        System.out.println();
        System.out.println("Opções disponíveis: ");
        for (Local l : locaisSemAtual) {
            
            System.out.print((i + 1) + " - " + l);
            System.out.println();

            i++;
        }
        
        System.out.println();
        System.out.print("Sua escolha: ");
        int opcNovoLocal = SC.nextInt();
        
        if (!opcValida(opcNovoLocal, qtdLocais)){
            throw new GerenciadorException("Escolha novo local válido! ( 1 a " +qtdLocais+ ")");
        }
       
        //Pega novo local
        Local novoLocal = locaisSemAtual.get(opcNovoLocal - 1);
        Destino d = new Destino(viagem.getDestino().getCidade(), novoLocal);
        
        viagem.setDestino(d);
        return viagem;
    }

    //Gerar nova viagem com nova data embarque || nova data retorno
    public ViagemAbstrata gerarNovaViagem(AlteracaoViagem av, ViagemAbstrata viagem, LocalDate dataAntiga) {
        
        ViagemAbstrata novaViagem = viagem;
        
        String nomeAlteracao = (av.equals(AlteracaoViagem.ALTERAR_EMBARQUE))
                ? "embarque" 
                : "retorno";
        
        System.out.println();
        System.out.print("Data de " +nomeAlteracao+ " anterior: ");
        System.out.println(viagem.getDTF().format(dataAntiga));
        
        
        System.out.println();
        System.out.print("Entre com nova data de " +nomeAlteracao+ ": ");
        LocalDate novaData = LocalDate.parse(SC.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        
        
        if (!dataViagemNaoExiste(novaData, viagem)){
            throw new GerenciadorException("A data de " +nomeAlteracao+ " passada já está ocupada por outra viagem!");
        }
        
        //Se a data de embarque / retorno foi realmente modificada
        if (!dataValida(novaData, dataAntiga)){
            throw new GerenciadorException("Alteração feita inválida!");
        }
        
       
        if (av.equals(AlteracaoViagem.ALTERAR_EMBARQUE)) {

            if (!datasValidas(novaData, viagem.getDataRetorno())) {
                throw new GerenciadorException("A nova data de " +nomeAlteracao+ " conflita com a data de retorno desta viagem!");
            }

            novaViagem.setDataEmbarque(novaData);

        }
        
        //Altero retorno
        else {

            if (!datasValidas(viagem.getDataEmbarque(), novaData)) {
                throw new GerenciadorException("A nova data de " +nomeAlteracao+ " conflita com a data de embarque desta viagem!");
            }

            novaViagem.setDataRetorno(novaData);
        }
    
        return novaViagem;
    }

}
