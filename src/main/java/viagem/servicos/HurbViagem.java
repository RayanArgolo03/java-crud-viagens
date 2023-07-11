package viagem.servicos;

import java.time.LocalDate;
import viagem.entidades.Destino;
import viagem.entidades.ViagemAbstrata;

public class HurbViagem extends ViagemAbstrata {

    public HurbViagem(Destino destino, LocalDate dataIda, LocalDate dataVolta) {
        super(destino, dataIda, dataVolta);
    }

    @Override
    public void imprimirDadosViagem(Destino d) {
        
         System.out.println("Viagem HURB - " +d);
         System.out.println("Embarque - " +DTF.format(getDataEmbarque())+ ", Retorno - " +DTF.format(getDataRetorno()));
    }

    @Override
    public void viajar(Destino d) {
        
        System.out.println();
        System.out.println("Viagem confirmada!");
        System.out.println( "Embarcará " +DTF.format(getDataEmbarque())+ " para " +d + " com a HURB");
        System.out.println("Retorno previsto: " +DTF.format(getDataRetorno()));
    }
    
}
