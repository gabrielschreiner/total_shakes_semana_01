package pedido;

import ingredientes.Adicional;
import ingredientes.Base;
import produto.TipoTamanho;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final int id;
    private final ArrayList<ItemPedido> itens;
    private final Cliente cliente;

    private int quantidade = 0;
    private int indice = 0;

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
            Base baseDoPedido = itemDoPedido.getShake().getBase();
            Double precoDaBase = cardapio.buscarPreco(baseDoPedido);
            TipoTamanho tamanho = itemDoPedido.getShake().getTipoTamanho();

            if (tamanho == TipoTamanho.G) {
                total += (precoDaBase * 1.5) * itemDoPedido.getQuantidade();
            } else if (tamanho == TipoTamanho.M) {
                total += (precoDaBase * 1.3) * itemDoPedido.getQuantidade();
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
        if (checaSeItemExiste(itemPedidoAdicionado)) {
            itemPedidoAdicionado.setQuantidade(quantidade + itemPedidoAdicionado.getQuantidade());
            itens.set(indice, itemPedidoAdicionado);
        } else {
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) {
        if (!checaSeItemExiste(itemPedidoRemovido)) {
            throw new IllegalArgumentException("Item nao existe no pedido.");
        } else {
            itemPedidoRemovido.setQuantidade(quantidade - 1);
            itens.set(indice, itemPedidoRemovido);
            if (quantidade - 1 <= 0) {
                itens.remove(itemPedidoRemovido);
            }
        }
    }

    private boolean checaSeItemExiste(ItemPedido itemParaChecar) {
        for (ItemPedido item : itens) {
            if (item.getShake().toString().equals(itemParaChecar.getShake().toString())) {
                quantidade = item.getQuantidade();
                indice = itens.indexOf(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
