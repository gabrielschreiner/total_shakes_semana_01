package exceptions;

public class IngredienteCadastradoException extends Throwable {
    private static final String MENSAGEM = "Ingrediente já cadastrado";

    public IngredienteCadastradoException() {
        super(MENSAGEM);
    }

}
