package viagem.servicos;

import java.time.LocalDate;
import viagem.entidades.Destino;
import viagem.entidades.ViagemAbstrata;
import static viagem.entidades.ViagemAbstrata.*;

public class MilhasViagem extends ViagemAbstrata {

    public MilhasViagem(Destino destino, LocalDate dataIda, LocalDate dataVolta) {
        super(destino, dataIda, dataVolta);
    }

    @Override
    public void imprimirDadosViagem(Destino d) {

        System.out.println("Viagem MILHAS - " + d);
        System.out.println("Embarque - " + getDTF().format(getDataEmbarque()) + ", Retorno - " + getDTF().format(getDataRetorno()));
    }

    @Override
    public void viajar(Destino d) {

        System.out.println();
        System.out.println("Viagem confirmada!");
        System.out.println("Irá " + getDTF().format(getDataEmbarque()) + " para " +d.getCidade().getSiglaEstado()+ ", " +d + " com a MILHAS");
        System.out.println("Retorno previsto: " + getDTF().format(getDataRetorno()));
    }

}
