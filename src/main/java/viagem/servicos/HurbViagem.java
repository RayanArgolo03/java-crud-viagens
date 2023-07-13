package viagem.servicos;

import java.time.LocalDate;
import viagem.entidades.Destino;
import viagem.entidades.ViagemAbstrata;

public class HurbViagem extends ViagemAbstrata {

    public HurbViagem(Destino destino, LocalDate dataEmbarqueDate, LocalDate dataVolta) {
        super(destino, dataEmbarqueDate, dataVolta);
    }

    @Override
    public void imprimirDadosViagem(Destino d) {
        
         System.out.println("Viagem HURB - " +d);
         System.out.println("Embarque - " +getDTF().format(getDataEmbarque())+ ", Retorno - " +getDTF().format(getDataRetorno()));
    }

    @Override
    public void viajar(Destino d) {
        
        System.out.println();
        System.out.println("Viagem confirmada!");
        System.out.println( "Embarcará " +getDTF().format(getDataEmbarque())+ " para " +d.getCidade().getSiglaEstado()+ ", " +d + " com a HURB");
        System.out.println("Retorno previsto: " +getDTF().format(getDataRetorno()));
    }
    
}
