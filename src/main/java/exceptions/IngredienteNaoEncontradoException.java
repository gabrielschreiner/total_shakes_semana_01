package exceptions;

public class IngredienteNaoEncontradoException extends Exception {

    private static final String MENSAGEM = "Ingrediente não encontrado";

    public IngredienteNaoEncontradoException() {
        super(MENSAGEM);
    }
}
