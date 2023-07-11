
package viagem.enums;

public enum OpcoesMenu {
    
    VIAJAR (1), ALTERAR_VIAGEM (2), EXLUIR_VIAGEM (3), IMPRIMIR_VIAGENS(4);
    
    private int id;

    private OpcoesMenu(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    
    
}
