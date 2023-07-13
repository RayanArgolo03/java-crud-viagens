
package viagem.enums;


public enum AlteracaoViagem {
    
    ALTERAR_DESTINO (1), ALTERAR_EMBARQUE (2), ALTERAR_RETORNO (3);
    
    private int id;

    private AlteracaoViagem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
