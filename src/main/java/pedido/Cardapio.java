package pedido;

import exceptions.IngredienteInexistenteException;
import exceptions.PrecoInvalidoException;
import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Cardapio {
    private final TreeMap<Ingrediente, Double> precos;

    public Cardapio() {
        this.precos = new TreeMap<>(Ingrediente::compareTo);
    }


    public TreeMap<Ingrediente, Double> getPrecos() {
        return this.precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente, Double preco) {
        verificaPrecoMaiorQueZero(preco);
        precos.put(ingrediente, preco);
    }

    public void atualizarIngrediente(Ingrediente ingrediente, Double preco) {
        verificaSeIngredienteExiste(ingrediente);
        verificaPrecoMaiorQueZero(preco);
        precos.replace(ingrediente, preco);
    }

    public void removerIngrediente(Ingrediente ingrediente) {
        verificaSeIngredienteExiste(ingrediente);
        precos.remove(ingrediente);
    }

    public Double buscarPreco(Ingrediente ingrediente) {
        verificaSeIngredienteExiste(ingrediente);
        return precos.get(ingrediente);
    }

    private void verificaPrecoMaiorQueZero(Double preco) throws PrecoInvalidoException {
        if (preco <= 0) {
            throw new PrecoInvalidoException();
        }
    }

    private void verificaSeIngredienteExiste(Ingrediente ingrediente) throws IngredienteInexistenteException {
        if (!precos.containsKey(ingrediente)) {
            throw new IngredienteInexistenteException();
        }
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
