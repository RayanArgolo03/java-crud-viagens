package viagem.servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;
import viagem.entidades.Cidade;
import viagem.entidades.Destino;
import viagem.entidades.Local;
import viagem.entidades.ViagemAbstrata;
import viagem.enums.AlteracoesViagem;
import viagem.enums.OpcoesMenu;
import viagem.enums.SiglaEstado;
import viagem.enums.TipoServicoViagem;
import viagem.exceptions.GerenciadorException;

public final class GerenciarAcoes {

    private GerenciadorViagens gv;

    public static final Scanner sc = new Scanner(System.in);

    public GerenciarAcoes() {
    }

    public GerenciadorViagens getGv() {
        return gv;
    }

    //Injeta a dependência diretamente no GV
    public void setGv(GerenciadorViagens gv) {
        this.gv = gv;
    }

    public void gerenciarAcoes(int opcAcao) {

        if (opcAcao == OpcoesMenu.VIAJAR.getId()) {
            gerenciarViagem();
        }
        
        else if (opcAcao == OpcoesMenu.ALTERAR_VIAGEM.getId()) {
            gerenciarAlteracao();
        }
        
        else if (opcAcao == OpcoesMenu.EXLUIR_VIAGEM.getId()) {
            gerenciarExclusao();
        }
        
        //Imprimir viagem
        else {
            gerenciarImpressaoViagens();
        }

    }

    public void gerenciarViagem() {

        int qtdServicosViagem = TipoServicoViagem.values().length;
        imprimirOpcoesServico();
        

        System.out.print("Sua escolha: ");
        int tipoServicoViagem = sc.nextInt();

        if (!opcValida(tipoServicoViagem, qtdServicosViagem)) {
            throw new GerenciadorException("Selecione serviço de viagem existente! (1 a " + qtdServicosViagem + ")");
        }

        TipoServicoViagem tsv = gerarServicoViagem(tipoServicoViagem);

        //Informações do destino
        System.out.println();
        System.out.println("Informações do destino: ");
        System.out.println("Estados disponíveis: ");

        int qtdSiglasEstado = SiglaEstado.values().length;
        imprimirOpcoesEstado();

        System.out.print("Sua escolha: ");
        int opcSiglaEstado = sc.nextInt();

        //È um estado válido
        if (!opcValida(opcSiglaEstado, qtdSiglasEstado)) {
            throw new GerenciadorException("Estado inválido! (1 a " + qtdSiglasEstado + ")");
        }

        //Gerou sigla de estado dada escolha 
        SiglaEstado se = gerarSiglaEstado(opcSiglaEstado);

        //Gerar cidades possíveis no estado escolhido
        List<Destino> possiveisDestinos = gv.iniciarDestinos(se, tsv);

        List<Cidade> cidadesDisponiveis = possiveisDestinos.stream().map(Destino::getCidade).toList();

        int qtdCidadesDisponiveis = cidadesDisponiveis.size();
        imprimirCidadesDisponiveis(cidadesDisponiveis, se, tsv);

        System.out.print("Sua escolha: ");
        int opcCidade = sc.nextInt();

        if (!opcValida(opcCidade, qtdCidadesDisponiveis)) {
            throw new GerenciadorException("Cidade inválida! (1 a " + qtdCidadesDisponiveis + ")");
        }

        //Filtro na lista cidade escolhida
        Cidade cidadeEscolhida = cidadesDisponiveis.stream()
                .filter(cidade -> cidadesDisponiveis.indexOf(cidade) == opcCidade - 1)
                .findFirst()
                .orElse(null);

        //Imprimindo Locais disponíveis
        List<Local> locaisDisponiveis = cidadeEscolhida.getLocais();

        int qtdLocaisDisponiveis = locaisDisponiveis.size();
        cidadeEscolhida.imprimirLocaisDisponiveis(cidadeEscolhida.getNome());

        System.out.print("Sua escolha: ");
        int opcLocal = sc.nextInt();

        if (!opcValida(opcLocal, qtdLocaisDisponiveis)) {
            throw new GerenciadorException("Local inválido! (1 a " + qtdLocaisDisponiveis + ")");
        }

        Local localEscolhido = locaisDisponiveis.stream()
                .filter(local -> locaisDisponiveis.indexOf(local) == opcLocal - 1)
                .findFirst()
                .orElse(null);

        Destino d = new Destino(cidadeEscolhida, localEscolhido);

        System.out.println();
        System.out.println("Data de ida: ");
        LocalDate dataEmbarque = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println();
        System.out.println("Data de retorno: ");
        LocalDate dataRetorno = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //Verificar se datas são iguais ou ida antes de retorno
        if (!datasValidas(dataEmbarque, dataRetorno)) {
            throw new GerenciadorException("Datas inválidas! Data de ida não pode ser igual nem depois da data de retorno!");
        }

        //Verificar se viagem já existe
        if (!viagemNaoExiste(dataEmbarque, dataRetorno)) {
            throw new GerenciadorException("Já existe viagem com data de ida ou de retorno confirmada nessa data!");
        }

        System.out.println();
        gv.viajar(tsv, d, dataEmbarque, dataRetorno);

    }

    private boolean opcValida(int opc, int total) {
        return opc > 0 && opc <= total;
    }

    private boolean viagemNaoExiste(LocalDate dataIda, LocalDate dataRetorno) {

        ViagemAbstrata dataIdaViagemRealizada = gv.getViagensRealizadas()
                .stream()
                .filter(viagem -> viagem.getDataEmbarque().equals(dataIda) || viagem.getDataEmbarque().equals(dataRetorno))
                .findFirst().orElse(null);

        return dataIdaViagemRealizada == null;
    }

    private boolean datasValidas(LocalDate dataEmbarque, LocalDate dataRetorno) {
        return dataEmbarque.isBefore(dataRetorno) && !dataEmbarque.isEqual(dataRetorno);
    }

    public void gerenciarAlteracao() {

        if (!temViagem()) {
            throw new GerenciadorException("Você ainda não realizou nenhuma viagem!");
        }

        System.out.println();
        System.out.println("Viagens realizadas: ");
        
        int qtdViagensRealizadas = gv.getViagensRealizadas().size();
        gv.imprimirViagens();

        System.out.println();
        System.out.print("Sua escolha: ");
        int opcViagem = sc.nextInt();

        if (!opcValida(opcViagem, qtdViagensRealizadas)) {
            throw new GerenciadorException("Selecione opção válido! (1 a " + qtdViagensRealizadas + ")");
        }

        //Extraindo viagem antiga dado valor digitado - 1 (index)
        ViagemAbstrata viagemAntiga = gv.getViagensRealizadas()
                .stream().filter(viagem -> gv.getViagensRealizadas().indexOf(viagem) == opcViagem - 1)
                .findFirst().orElse(null);
        
        
        imprimirOpcoesAlteracao();

        System.out.print("Sua escolha: ");
        int opcAlteracaoViagem = sc.nextInt();

        //Hard code pois não teriam mais opções
        if (!opcValida(opcAlteracaoViagem, 3)) {
            throw new GerenciadorException("Digite opção válida! (1 a 3)");
        }
        
        //Índice de viagem antiga
        int indexViagemAntiga = IntStream.range(0, gv.getViagensRealizadas().size())
                .filter(indice -> gv.getViagensRealizadas().get(indice).equals(viagemAntiga)).
                findFirst()
                .orElse(-1);
                
        AlteracoesViagem av = gerarAlteracaoViagem(opcAlteracaoViagem);
        
        ViagemAbstrata novaViagem = escolhaViagem(av, viagemAntiga, opcAlteracaoViagem);
        
        gv.alterarViagem(indexViagemAntiga, novaViagem);
    }

    private boolean temViagem() {
        return !gv.getViagensRealizadas().isEmpty();
    }

    private ViagemAbstrata escolhaViagem(AlteracoesViagem av, ViagemAbstrata viagemAntiga, int opcAlteracaoViagem) {

        ViagemAbstrata novaViagem = null;
        
        if (av.equals(AlteracoesViagem.ALTERAR_LOCAL)) {
            novaViagem = gerarNovaViagem(viagemAntiga);
        }
        
            //Alterar Data!!
        else {

            //Altera embarque!!
            if (opcAlteracaoViagem == AlteracoesViagem.ALTERAR_EMBARQUE.getId()) {
                novaViagem = gerarNovaViagem(viagemAntiga.getDataEmbarque());
            }
            
            //Altera retorno!!
            else {
                novaViagem = gerarNovaViagem(viagemAntiga.getDataRetorno());
            }

        }

        return novaViagem;
    }

    private void gerenciarExclusao() {

    }

    private void gerenciarImpressaoViagens() {

    }
    
    private void imprimirOpcoesEstado() {

        int i = 0;
        for (SiglaEstado s : SiglaEstado.values()) {

            System.out.print((i + 1) + " - " + s);
            System.out.println();

            i++;
        }

    }

    private void imprimirOpcoesServico() {

        String[] servicos = new String[]{"Hurb", "Milhas"};
        
        System.out.println();
        int i = 0;
        for (String servico : servicos) {
            System.out.println((i + 1) + " - " + servico);
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

        String[] opcoes = new String[]{"cidade e local", "data de embarque", "data de retorno"};
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

    private AlteracoesViagem gerarAlteracaoViagem(int opcAlteracaoViagem) {

        AlteracoesViagem av = null;

        switch (opcAlteracaoViagem) {

            case 1 -> {
                av = AlteracoesViagem.ALTERAR_LOCAL;
            }
            case 2 -> {
                av = AlteracoesViagem.ALTERAR_EMBARQUE;
            }
            case 3 -> {
                av = AlteracoesViagem.ALTERAR_RETORNO;
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

    //Altera local da cidade
    private ViagemAbstrata gerarNovaViagem(ViagemAbstrata viagemAntiga) {

        ViagemAbstrata novaViagem = viagemAntiga;
        
        System.out.println();
        System.out.println("Dados da cidade e local antigos: ");
        System.out.println(viagemAntiga.getDestino());
        
        List<Local> locaisSemAtual = gv.getViagensRealizadas().stream()
                .map(ViagemAbstrata::getDestino)
                .map(Destino::getCidade)
                .flatMap(cidade -> cidade.getLocais().stream())
                .filter(local -> !local.equals(viagemAntiga.getDestino().getLocal()))
                .distinct()
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
        
        System.out.println("Sua escolha: ");
        int opcNovoLocal = sc.nextInt();
        
        if (!opcValida(opcNovoLocal, qtdLocais)){
            throw new GerenciadorException("Escolha novo local válido! ( 1 a " +qtdLocais+ ")");
        }
       
        //Pega novo local
        Local novoLocal = locaisSemAtual.get(opcNovoLocal - 1);
        novaViagem.getDestino().setLocal(novoLocal);
        
        return novaViagem;
    }

    //Altera data de embarque ou data de retorno
    private ViagemAbstrata gerarNovaViagem(LocalDate dataAntiga) {
        return null;
    }

}
