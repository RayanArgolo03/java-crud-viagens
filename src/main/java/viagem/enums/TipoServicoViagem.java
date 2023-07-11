
package viagem.enums;


public enum TipoServicoViagem {
    
    HURB(1), MILHAS(2);
    
    private int id;

    private TipoServicoViagem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
