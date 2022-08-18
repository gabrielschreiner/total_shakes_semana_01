package pedido;

import exceptions.ItemInexistenteException;

import java.util.ArrayList;

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
        return itens.stream()
                .map(itemPedido -> {
                    final var shake = itemPedido.getShake();
                    final var adicionais = shake.getAdicionais();
                    final var precoBase = cardapio.buscarPreco(shake.getBase()) * shake.getTipoTamanho().multiplicador;
                    final var precoAdicionais = adicionais.stream().map(cardapio::buscarPreco).reduce(0.0, Double::sum);
                    return ((precoBase + precoAdicionais) * itemPedido.getQuantidade());
                })
                .reduce(0.0, Double::sum);
    }

    int quantidade = 0;
    int index = 0;

    public boolean doesItemExiste(ItemPedido itemPedido) {
        for (ItemPedido item : itens) {
            var itemStringified = item.getShake().toString();
            var itemPedidoStringified = itemPedido.getShake().toString();
            if (itemStringified.equals(itemPedidoStringified)) {
                quantidade = item.getQuantidade();
                index = itens.indexOf(item);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {
        if (doesItemExiste(itemPedidoAdicionado)) {
            itemPedidoAdicionado.setQuantidade(quantidade + itemPedidoAdicionado.getQuantidade());
            itens.set(index, itemPedidoAdicionado);
        } else {
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) throws ItemInexistenteException {
        var shakeIndex = itens.indexOf(itemPedidoRemovido);

        if (shakeIndex == -1) {
            throw new ItemInexistenteException();
        } else {
            var shake = itens.get(shakeIndex);
            if (shake.getQuantidade() == 1)
                itens.remove(itemPedidoRemovido);
            else
                shake.setQuantidade(shake.getQuantidade() - 1);
        }
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
