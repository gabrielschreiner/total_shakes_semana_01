package exceptions;

public class PrecoInvalidoException extends IllegalArgumentException {
    private static final String MENSAGEM = "Preco invalido.";

    public PrecoInvalidoException() {
        super(MENSAGEM);
    }
}
