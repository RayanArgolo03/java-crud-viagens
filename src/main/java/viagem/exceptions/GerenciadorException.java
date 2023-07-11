
package viagem.exceptions;

public class GerenciadorException extends RuntimeException {

    @java.io.Serial
    static final long serialVersionUID = 1L;

    
    public GerenciadorException(String messagem) {
        super(messagem);
    }

}
