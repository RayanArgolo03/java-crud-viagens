package viagem.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import viagem.servicos.Viajavel;

public abstract class ViagemAbstrata implements Viajavel {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Destino destino;
    private LocalDate dataEmbarque;
    private LocalDate dataRetorno;

    public ViagemAbstrata(Destino destino, LocalDate dataEmbarque, LocalDate dataRetorno) {
        this.destino = destino;
        this.dataEmbarque = dataEmbarque;
        this.dataRetorno = dataRetorno;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public LocalDate getDataEmbarque() {
        return dataEmbarque;
    }

    public void setDataEmbarque(LocalDate dataEmbarque) {
        this.dataEmbarque = dataEmbarque;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public abstract void imprimirDadosViagem(Destino d);

    @Override
    public abstract void viajar(Destino d);

}
