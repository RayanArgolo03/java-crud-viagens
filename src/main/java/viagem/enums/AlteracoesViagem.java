
package viagem.enums;


public enum AlteracoesViagem {
    
    ALTERAR_LOCAL (1), ALTERAR_EMBARQUE (2), ALTERAR_RETORNO (3);
    
    private int id;

    private AlteracoesViagem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
