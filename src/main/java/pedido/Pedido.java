package pedido;

import exceptions.ItemInexistenteException;
import ingredientes.Adicional;
import produto.TipoTamanho;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final int id;
    private final ArrayList<ItemPedido> itens;
    private final Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens, Cliente cliente) {
        this.id = id;
        this.itens = itens;
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio) {
        double total = 0;

        for (ItemPedido itemDoPedido : itens) {
            var baseDoPedido = itemDoPedido.getShake().getBase();
            var precoDaBase = cardapio.buscarPreco(baseDoPedido);
            var tamanho = itemDoPedido.getShake().getTipoTamanho();

            if (tamanho == TipoTamanho.G) {
                total += tamanho.multiplicador * itemDoPedido.getQuantidade();
            } else if (tamanho == TipoTamanho.M) {
                total += tamanho.multiplicador * itemDoPedido.getQuantidade();
            } else {
                total += precoDaBase * itemDoPedido.getQuantidade();
            }
        }
        total += this.calcularAdicionais(cardapio);
        return total;
    }

    private double calcularAdicionais(Cardapio cardapio) {
        double valorTotalAdicionais = 0;

        for (ItemPedido itemDoPedido : itens) {
            double precoDosAdicionais = 0;
            List<Adicional> adicionais = itemDoPedido.getShake().getAdicionais();

            for (Adicional adicional : adicionais) {
                precoDosAdicionais += cardapio.buscarPreco(adicional);
            }
            valorTotalAdicionais += precoDosAdicionais * itemDoPedido.getQuantidade();
        }
        return valorTotalAdicionais;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {
        var shakeIndex = itens.indexOf(itemPedidoAdicionado); // retorna -1 caso ñ exista ou o índice se presente

        if (shakeIndex != -1) {
            var shake = itens.get(shakeIndex);
            shake.setQuantidade(itemPedidoAdicionado.getQuantidade() + shake.getQuantidade());
        } else {
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) throws ItemInexistenteException {
        var shakeIndex = itens.indexOf(itemPedidoRemovido);

        if (shakeIndex != -1) {
            var shake = itens.get(shakeIndex);
            if (shake.getQuantidade() == 1)
                itens.remove(itemPedidoRemovido);
            else
                shake.setQuantidade(shake.getQuantidade() - 1);
            return;
        }
        throw new ItemInexistenteException();
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
