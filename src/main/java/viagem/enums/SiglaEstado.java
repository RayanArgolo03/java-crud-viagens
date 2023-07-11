package viagem.enums;

public enum SiglaEstado {

    RJ(1), SP(2), BSB(3), BA(4);

    private int id;

    private SiglaEstado(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
