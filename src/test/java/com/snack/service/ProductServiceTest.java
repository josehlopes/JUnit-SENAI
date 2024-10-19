package com.snack.service;

import com.snack.entities.Product;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    private Product product1;
    private Product product2;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductService();
        product1 = new Product(1, "Hot Dog", 10.4f, "src/main/java/com/snack/images/polar-bear-8703952_1920.jpg");
        product2 = new Product(2, "Freeze Dog", 5.2f, "");
        productService.save(product1);

    }


    @Test
    public void deveSalvarProdutoComImagemVálida() {
        Product product = new Product(5, "YinYang Dog", 50.0f, "src/main/java/com/snack/images/polar-bear-8703952_1920.jpg");

        assertTrue(productService.save(product));
    }


    @Test
    public void testarSalvarProdutoComImagemInválida() {
        Product product = new Product(5, "YinYang Dog", 50.0f, "src/main/java/com/snack/images/polar-dawdawdbear-8703952_1920.jpg");

        assertFalse(productService.save(product));
    }

    @Test
    public void deveAtualizarProdutoExistente() {
        Product produtoOriginal = new Product(10, "Hot Dog", 10.4f, "src/main/java/com/snack/images/polar-bear-8703952_1920.jpg");
        productService.save(produtoOriginal);

        Product produtoAAtualizar = new Product(10, "Hot Dog", 10.4f, "src/main/java/com/snack/images/robo.png");
        productService.update(produtoAAtualizar);

        String image = productService.getImagePathById(10);

        Assertions.assertTrue(image.contains("10.png"));
    }

    @Test
    public void deveRemoverProdutoExistente() {
        productService.remove(1);
        assertThrows(NoSuchElementException.class, () -> productService.getImagePathById(1) );
    }

    @Test
    public void deveObterCaminhoDaImagemPorId(){
        assertEquals("C:\\Users\\aluno\\Documents\\JUnit-SENAI\\src\\main\\java\\com\\snack\\images\\1.jpg", productService.getImagePathById(1));
    }
}
