package exceptions;

public class IngredienteInexistenteException extends IllegalArgumentException {
    private static final String MENSAGEM = "Ingrediente nao existe no cardapio.";

    public IngredienteInexistenteException() {
        super(MENSAGEM);
    }
}
