package armazem;

import exceptions.IngredienteCadastradoException;
import exceptions.IngredienteNaoEncontradoException;
import exceptions.QuantidadeIngredienteInvalidaException;
import ingredientes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ArmazemTest {
    static Armazem armazem;
    static Base base;
    static Fruta fruta;
    static Topping topping;

    @BeforeAll
    static void beforeAll() {
        base = new Base(TipoBase.LEITE);
        fruta = new Fruta(TipoFruta.ABACATE);
        topping = new Topping(TipoTopping.MEL);
    }

    @BeforeEach
    void setup() {
        armazem = new Armazem();
    }

    @Test
    @DisplayName("Teste de cadastro de ingredientes")
    public void testCadastrarIngredienteEmEstoque() throws IngredienteCadastradoException {

        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.cadastrarIngredienteEmEstoque(fruta);
        armazem.cadastrarIngredienteEmEstoque(topping);

        assertAll(
                () -> assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(base)),
                () -> assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(fruta)),
                () -> assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(topping))
        );

        IngredienteCadastradoException thrownException = assertThrows(
                IngredienteCadastradoException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(base)
        );

        assertEquals("Ingrediente já cadastrado", thrownException.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Teste de consulta da quantidade de ingredientes em estoque")
    @ValueSource(ints = {2, 3, 5})
    public void testConsultarQuantidadeDoIngredienteEmEstoque(Integer quantidade)
            throws QuantidadeIngredienteInvalidaException, IngredienteNaoEncontradoException, IngredienteCadastradoException {

        armazem.cadastrarIngredienteEmEstoque(topping);
        assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(topping));

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(topping, quantidade);
        assertEquals(quantidade, armazem.consultarQuantidadeDoIngredienteEmEstoque(topping));

        armazem.descadastrarIngredienteEmEstoque(topping);
        assertNull(armazem.getEstoque().get(topping));

        IngredienteNaoEncontradoException thrownException = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(topping)
        );

        assertEquals("Ingrediente não encontrado", thrownException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 4", "2, 3"})
    public void testAdicionarQuantidadeDoIngredienteEmEstoque(Integer quantidade1, Integer quantidade2)
            throws IngredienteCadastradoException, QuantidadeIngredienteInvalidaException, IngredienteNaoEncontradoException {

        armazem.cadastrarIngredienteEmEstoque(fruta);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(fruta, quantidade1);

        assertEquals(quantidade1, armazem.getEstoque().get(fruta));

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(fruta, quantidade2);

        assertEquals(quantidade1 + quantidade2, armazem.getEstoque().get(fruta));

        armazem.descadastrarIngredienteEmEstoque(fruta);
        assertNull(armazem.getEstoque().get(fruta));

        IngredienteNaoEncontradoException thrownException = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.descadastrarIngredienteEmEstoque(fruta)
        );

        assertEquals("Ingrediente não encontrado", thrownException.getMessage());
    }

    @Test
    public void testDescadastrarIngredienteEmEstoque()
            throws QuantidadeIngredienteInvalidaException, IngredienteNaoEncontradoException, IngredienteCadastradoException {

        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(base, 2);

        assertEquals(2, armazem.getEstoque().get(base));

        armazem.descadastrarIngredienteEmEstoque(base);
        assertNull(armazem.getEstoque().get(base));

        IngredienteNaoEncontradoException thrownException = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(topping)
        );

        assertEquals("Ingrediente não encontrado", thrownException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {"4, 1", "3, 2"})
    public void testReduzirQuantidadeDoIngredienteEmEstoque(Integer quantidade1, Integer quantidade2)
            throws QuantidadeIngredienteInvalidaException, IngredienteNaoEncontradoException, IngredienteCadastradoException {
        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(base, quantidade1);
        assertEquals(quantidade1, armazem.getEstoque().get(base));

        armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, quantidade2);
        assertEquals(quantidade1 - quantidade2, armazem.getEstoque().get(base));

        armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, quantidade1 - quantidade2);
        assertNull(armazem.getEstoque().get(base));

        QuantidadeIngredienteInvalidaException thrownException = assertThrows(
                QuantidadeIngredienteInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, 0)
        );

        assertEquals("Quantidade invalida", thrownException.getMessage());

        IngredienteNaoEncontradoException thrownException2 = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(topping)
        );

        assertEquals("Ingrediente não encontrado", thrownException2.getMessage());
    }
}
