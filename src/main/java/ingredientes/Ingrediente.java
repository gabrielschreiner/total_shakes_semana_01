package ingredientes;

public interface Ingrediente {

    Enum<?> obterTipo();

    int compareTo(Ingrediente ingrediente2);
}