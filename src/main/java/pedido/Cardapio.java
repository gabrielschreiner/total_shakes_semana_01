package pedido;

import ingredientes.Fruta;
import ingredientes.Ingrediente;
import ingredientes.Topping;

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

    public void adicionarIngrediente(Fruta fruta, Double preco) {
        verificaPrecoMaiorQueZero(preco);
        this.precos.put(fruta, preco);
    }

    public void adicionarIngrediente(Topping topping, Double preco) {
        verificaPrecoMaiorQueZero(preco);
        precos.put(topping, preco);
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

    private void verificaPrecoMaiorQueZero(Double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preco invalido.");
        }
    }

    private void verificaSeIngredienteExiste(Ingrediente ingrediente) {
        if (!precos.containsKey(ingrediente)) {
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        }
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
