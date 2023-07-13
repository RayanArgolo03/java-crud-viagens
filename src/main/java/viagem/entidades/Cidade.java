package viagem.entidades;

import java.util.*;
import viagem.enums.SiglaEstado;

public class Cidade {

    private String nome;
    private SiglaEstado siglaEstado;

    private List<Local> locais = new ArrayList<>();

    public Cidade(String nome, SiglaEstado siglaEstado) {

        this.nome = nome;
        this.siglaEstado = siglaEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SiglaEstado getSiglaEstado() {
        return siglaEstado;
    }

    public List<Local> getLocais() {
        return locais;
    }

    //Imprime locais disponíveis pelo nome da cidade
    public void imprimirLocaisDisponiveis(String nome) {

        System.out.println();
        int i = 0;

        System.out.println("Locais disponíveis para a cidade de " +nome);
        for (Local local : locais) {
            System.out.println((i + 1) + " - " + local);
            i++;
        }

    }

    @Override
    public String toString() {
        return nome;
    }

}
