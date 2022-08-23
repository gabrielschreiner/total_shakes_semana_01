package armazem;

import exceptions.IngredienteCadastradoException;
import exceptions.IngredienteNaoEncontradoException;
import exceptions.QuantidadeIngredienteInvalidaException;
import ingredientes.Ingrediente;

import java.util.Objects;
import java.util.TreeMap;

public class Armazem {

    private TreeMap<Ingrediente, Integer> estoque;

    public Armazem() {
        setEstoque(new TreeMap<>(Ingrediente::compareTo));
    }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) throws IngredienteCadastradoException {
        if (estoque.containsKey(ingrediente)) throw new IngredienteCadastradoException();
        estoque.put(ingrediente, 0);
    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) throws IngredienteNaoEncontradoException {
        estoque.remove(checaIngredienteEncontrado(ingrediente));
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantidade)
            throws QuantidadeIngredienteInvalidaException, IngredienteNaoEncontradoException {
        if (quantidade <= 0) throw new QuantidadeIngredienteInvalidaException();
        estoque.replace(checaIngredienteEncontrado(ingrediente), estoque.get(ingrediente) + quantidade);
    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantidade)
            throws IngredienteNaoEncontradoException, QuantidadeIngredienteInvalidaException {
        if (quantidade <= 0) throw new QuantidadeIngredienteInvalidaException();

        if (estoque.get(checaIngredienteEncontrado(ingrediente)).equals(quantidade)) {
            estoque.remove(ingrediente);
        } else {
            estoque.replace(ingrediente, estoque.get(ingrediente) - quantidade);
        }
    }

    public Integer consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente) throws IngredienteNaoEncontradoException {
        return estoque.get(checaIngredienteEncontrado(ingrediente));
    }

    private Ingrediente checaIngredienteEncontrado(Ingrediente ingrediente) throws IngredienteNaoEncontradoException {
        if (!estoque.containsKey(ingrediente)) {
            throw new IngredienteNaoEncontradoException();
        }
        return ingrediente;
    }

    public TreeMap<Ingrediente, Integer> getEstoque() {
        return estoque;
    }

    public void setEstoque(TreeMap<Ingrediente, Integer> estoque) {
        this.estoque = estoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Armazem)) return false;
        Armazem armazem = (Armazem) o;
        return Objects.equals(estoque, armazem.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estoque);
    }
}
