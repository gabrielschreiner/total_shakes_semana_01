package exceptions;

public class ItemInexistenteException extends IllegalArgumentException {

    private static final String MENSAGEM = "Item nao existe no pedido.";

    public ItemInexistenteException() {
        super(MENSAGEM);
    }
}
