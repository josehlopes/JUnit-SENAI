package com.snack.repositories;

import com.snack.entities.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private ProductRepository productRepository;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();

        product1 = new Product(1, "Hot Dog", 10.4f, "");
        product2 = new Product(2, "Freeze Dog", 5.2f, "");
        productRepository.append(product1);
        productRepository.append(product2);

    }

    @Test
    public void verificarSeOProdutoFoiAdicionadoALista() {
        productRepository.append(product1);
        assertTrue(productRepository.getAll().contains(product1));
    }

    @Test
    public void verificarSeOProdutoEstaNoArray() {
        productRepository.append(product1);
        Product productId1 = productRepository.getById(1);
        assertNotNull(productId1);
    }

    @Test
    public void confirmarSeOProdutoEstáNaLista() {
        productRepository.append(product1);
        assertTrue(productRepository.exists(product1.getId()));
    }

    @Test
    public void testarSeOProdutoÉRemovido() {
        productRepository.remove(2);
        assertFalse(productRepository.exists(2));
    }

    @Test
    public void verificarSeOProdutoÉAtualizado() {
        Product produtoAAtualizar = new Product(2, "YinYang Dog", 50.0f, "");
        productRepository.update(2, produtoAAtualizar);
        Product produtoAtualizado = productRepository.getById(2);
        assertEquals(product2, produtoAtualizado);
    }

    @Test
    public void testarSeTodosOsProdutosSãoRecuperadosCorretamente() {
        productRepository.append(product1);

        List<Product> todosOsProdutos = productRepository.getAll();

        assertNotNull(todosOsProdutos);
        assertEquals(2, todosOsProdutos.size());

        for (Product produto : todosOsProdutos) {
            Product produtoRetornado = productRepository.getById(produto.getId());
            assertNotNull(produtoRetornado);
            assertEquals(produto.getId(), produtoRetornado.getId());
            assertEquals(produto.getDescription(), produtoRetornado.getDescription());
            assertEquals(produto.getPrice(), produtoRetornado.getPrice(), 0.01f);
            assertEquals(produto.getImage(), produtoRetornado.getImage());
        }

    }

    @Test
    public void testRemoveNonExistentProduct() {
        productRepository.append(product1);
        productRepository.append(product2);
        int initialSize = productRepository.getAll().size();
        productRepository.remove(3);
        List<Product> allProducts = productRepository.getAll();
        assertEquals(initialSize, allProducts.size());
        assertFalse(allProducts.stream().anyMatch(p -> p.getId() == 3));
    }

    @Test
    public void testUpdateNonExistentProduct() {
        Product produtoAAtualizar = new Product(3, "YinYang Dog", 50.0f, "");
        assertThrows(NoSuchElementException.class, () -> productRepository.update(3, new Product(3, "YinYang Dog", 50.0f, "")));
    }

    @Test //TODO: Código de jefté permite essa criação
    public void testAddProductWithExistingId() {
        Product productWithExistingId = new Product(1, "YinYang Dog", 50.0f, "");
        productRepository.append(productWithExistingId);
        System.out.println(productRepository.getAll());
    }

    @Test
    public void verificarSeAListaÉIniciadaVazia() {
        ProductRepository productRepository = new ProductRepository();
        assertTrue(productRepository.getAll().isEmpty());
    }




}