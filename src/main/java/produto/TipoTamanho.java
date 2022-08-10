package produto;

public enum TipoTamanho {
    P,
    M,
    G;

    public final double multiplicador;

    TipoTamanho() {
        multiplicador = 0;
    }

}
