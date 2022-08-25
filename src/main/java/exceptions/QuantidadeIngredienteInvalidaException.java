package exceptions;

public class QuantidadeIngredienteInvalidaException extends Throwable {
    private static final String MENSAGEM = "Quantidade invalida";

    public QuantidadeIngredienteInvalidaException() {
        super(MENSAGEM);
    }

}

