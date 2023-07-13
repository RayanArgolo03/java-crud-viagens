package viagem.servicos;

import java.time.LocalDate;
import java.util.*;
import viagem.entidades.*;
import viagem.enums.*;

public class GerenciadorViagens {

    private GerenciarAcoes ga;
    private ServicoIniciarDestinos sid;

    private List<ViagemAbstrata> viagensRealizadas = new ArrayList<>();

    //Injeta dependências
    public GerenciadorViagens(GerenciarAcoes ga, ServicoIniciarDestinos sid) {

        this.ga = ga;
        this.sid = sid;
        iniciarGerenciadorAcoes();
        
    }

    //Relação bidirecional para iniciar gerenciador de ações
    private void iniciarGerenciadorAcoes() {
        ga.setGv(this);
    }
    
    
    public List<ViagemAbstrata> getViagensRealizadas() {
        return viagensRealizadas;
    }

    public GerenciarAcoes getGa() {
        return ga;
    }

    public ServicoIniciarDestinos getSid() {
        return sid;
    }

    //Inicia destinos dado serviço escolhido
    protected List<Destino> iniciarDestinos(SiglaEstado se, TipoServicoViagem tsv) {
        return getSid().iniciarDestinos(se, tsv);
    }

    public void exibirOpcoesAcao() {

        String[] opcoesAcao = new String[]
        {"Viajar", "Alterar viagem realizada", "Excluir viagem realizada", "Imprimir viagens realizadas"};
        
        int i = 0;
        System.out.println();
        for (String opcao : opcoesAcao) {
            System.out.println((i + 1) + " - " + opcao);
            i++;
        }

    }

    protected void viajar(TipoServicoViagem tsv, Destino d, LocalDate dataEmbarque, LocalDate dataRetorno) {

        ViagemAbstrata v = tsv.equals(TipoServicoViagem.HURB) 
                ? new HurbViagem(d, dataEmbarque, dataRetorno)
                : new MilhasViagem(d, dataEmbarque, dataRetorno);

        v.viajar(d);
        adicionarViagem(v);
    }
    
    private void adicionarViagem(ViagemAbstrata v){
        getViagensRealizadas().add(v);
    }

    protected void imprimirViagens() {

        int i = 0;

        System.out.println();
        System.out.println("Viagens realizadas: ");
        
        for (ViagemAbstrata v : getViagensRealizadas()) {

            System.out.println();
            System.out.print((i + 1) + ": ");
            v.imprimirDadosViagem(v.getDestino());

            i++;
        }

    }

    protected void alterarViagem(AlteracaoViagem opcaoAlteracao, int indexViagemAntiga, ViagemAbstrata novaViagem) {
        
        //Formatando exibição de alteração feita
        String aux = opcaoAlteracao.toString();
        String alteracaoFeita = aux.substring(aux.indexOf("_") + 1, aux.length()).toLowerCase();
        
        viagensRealizadas.set(indexViagemAntiga, novaViagem);
        
        System.out.println();
        exibirAcaoRealizada("Viagem alterada! Viagem com novo " +alteracaoFeita+ ":");
        
        novaViagem.imprimirDadosViagem(novaViagem.getDestino());
    }

    protected void excluirViagem(TipoServicoViagem tsv, SiglaEstado se, ViagemAbstrata viagemExcluida) {

        Destino d = viagemExcluida.getDestino();
        getViagensRealizadas().remove(viagemExcluida);
        
        System.out.println();
        exibirAcaoRealizada("A viagem feita pelo serviço " + tsv.toString() + " para " +se.toString()+ ", " +d+ " foi excluída!");

    }
    
    private void exibirAcaoRealizada(String acao){
        System.out.println(acao);
    }

}
