package viagem.servicos;

import viagem.enums.TipoServicoViagem;
import java.time.LocalDate;
import java.util.*;
import viagem.entidades.*;
import viagem.enums.SiglaEstado;

public class GerenciadorViagens {

    private GerenciarAcoes ga;
    private ServicoIniciarDestinos sid;

    private List<ViagemAbstrata> viagensRealizadas = new ArrayList<>();

    public GerenciadorViagens(GerenciarAcoes ga, ServicoIniciarDestinos sid) {

        this.ga = ga;
        this.sid = sid;
        iniciarGerenciadorAcoes();
    }

    //Relação bidirecional para iniciar gerenciador de ações
    private void iniciarGerenciadorAcoes() {
        ga.setGv(this);
    }

    protected List<Destino> iniciarDestinos(SiglaEstado se, TipoServicoViagem tsv) {
        return sid.iniciarDestinos(se, tsv);
    }

    public List<ViagemAbstrata> getViagensRealizadas() {
        return viagensRealizadas;
    }

    public GerenciarAcoes getGav() {
        return ga;
    }

    public void exibirOpcoesAcao() {

        System.out.println("1 - Viajar");
        System.out.println("2 - Alterar viagem realizada");
        System.out.println("3 - Excluir viagem realizada");
        System.out.println("4 - Imprimir viagens realizadas");

    }

    public void viajar(TipoServicoViagem tsv, Destino d, LocalDate dataEmbarque, LocalDate dataRetorno) {

        ViagemAbstrata v = tsv.equals(TipoServicoViagem.HURB) 
                ? new HurbViagem(d, dataEmbarque, dataRetorno)
                : new MilhasViagem(d, dataEmbarque, dataRetorno);

        v.viajar(d);
        adicionarViagem(v);
    }
    
    private void adicionarViagem(ViagemAbstrata v){
        viagensRealizadas.add(v);
    }

    public void imprimirViagens() {

        int i = 0;

        for (ViagemAbstrata v : getViagensRealizadas()) {

            System.out.println();
            System.out.print((i + 1) + ": ");
            v.imprimirDadosViagem(v.getDestino());

            i++;
        }

    }

    public void alterarViagem(int i, ViagemAbstrata novaViagem) {
        
        viagensRealizadas.set(i, novaViagem);
        
        System.out.println();
        System.out.println("Viagem alterada!");
        
        novaViagem.imprimirDadosViagem(novaViagem.getDestino());
    }

    public void excluirViagem(Destino d) {
        getViagensRealizadas().remove(d);
    }

}
