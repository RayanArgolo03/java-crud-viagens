
package viagem.entidades;


public class Destino {
    
    private Cidade cidade;
    private Local local;

    public Destino(Cidade cidade, Local local) {
        this.cidade = cidade;
        this.local = local;
    }
    
    public Destino(Cidade cidade){
        this.cidade = cidade;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    

    @Override
    public String toString() {
        return cidade+ ", " +local;
    }
    
}
