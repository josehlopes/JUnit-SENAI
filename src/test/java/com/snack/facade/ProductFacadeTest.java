package com.snack.facade;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFacadeTest {
    private ProductFacade productFacade;
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
        productApplication = new ProductApplication(productRepository ,productService);
        productFacade = new ProductFacade(productApplication);

        product1 = new Product(1, "Hot Dog", 10.4f, "");
        product2 = new Product(2, "Freeze Dog", 5.2f, "");
        productFacade.append(product1);
        productFacade.append(product2);

    }

    @Test
    public void deveRetornarTodosOsProdutos(){
        List<Product> produtos = productFacade.getAll();
        assertEquals(2, produtos.size());
    }

    @Test
    public void deveRetornarProdutoVálidoAoPassarIdExistente() {
       Product produtoBuscado = productFacade.getById(1);
         assertEquals(product1, produtoBuscado);
    }

    @Test
    public void deveRetornarTrueParaIdExistenteEFalseParaNãoExistente() {
        assertTrue(productFacade.exists(1));
        assertFalse(productFacade.exists(3));
    }

    @Test
    public void deveInserirProdutoCorretamenteAoPassarOAppend() {
        Product product10 = new Product(10, "Hot Dog3512315", 10.4f, "");
        productFacade.append(product10);
        Product produtoBuscado = productFacade.getById(10);
        assertEquals(product10, produtoBuscado);

    }

    @Test
    public void deveRemoverProdutoComIdVálido(){
        productApplication.remove(1);
        assertFalse(productFacade.exists(1));
    }
}
