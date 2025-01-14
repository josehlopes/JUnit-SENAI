package com.snack.application;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class ProductApplicationTest {
    private Product product1;
    private Product product2;
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
        productApplication = new ProductApplication(productRepository, productService);
        product1 = new Product(1, "Hot Dog", 10.4f, "src/main/java/com/snack/images/polar-bear-8703952_1920.jpg");
        product2 = new Product(2, "Freeze Dog", 5.2f, "");
    }

    @Test
    public void devePegarTodosOsItensExistentes(){
        productApplication.append(product1);
        productApplication.append(product2);
        assertEquals(2, productApplication.getAll().size());

    }

    @Test
    public void devePegarProdutoComIdVálido(){
        productApplication.append(product1);
        assertEquals(product1, productApplication.getById(1));
    }

    @Test
   public void deveRetornarNuloAoPassarProdutoComIdInválido(){
        productApplication.append(product1);
        assertThrows(NoSuchElementException.class, () -> productApplication.getById(2));
    }

    @Test
    public void deveRetornarTrueSeOIdDoProdutoExistir() {
        productApplication.append(product1);
        assertTrue(productApplication.exists(1));
    }
    @Test
    public void deveRetornarFalseSeOIdDoProdutoNãoExistir() {
        productApplication.append(product1);
        assertFalse(productApplication.exists(2));
    }

    @Test
    public void deveSalvarUmNovoProdutoESalvarSuaImagem() {
        productApplication.append(product1);
        assertEquals("C:\\Users\\aluno\\Documents\\JUnit-SENAI\\src\\main\\java\\com\\snack\\images\\1.jpg", product1.getImage());
    }

    @Test
    public void deveRemoverUmProdutoExistenteERemoverSuaImagem() {
        productApplication.append(product1);
        productApplication.remove(1);
        assertFalse(productApplication.exists(product1.getId()));
    }

    @Test
    public void nãoDeveAlterarOSistemaAoTentarRemoverUmProdutoComIdInexistente(){
        productApplication.remove(1);
        assertThrows(NoSuchElementException.class, () -> productApplication.remove(1));
    }

    @Test
    public void deveAtualizarProdutoExistenteEMudarImagem() {
        Product produtoOriginal = new Product(10, "Hot Dog", 10.4f, "src/main/java/com/snack/images/polar-bear-8703952_1920.jpg");
        productApplication.append(produtoOriginal);

        Product produtoAAtualizar = new Product(10, "Hot Dog", 10.4f, "src/main/java/com/snack/images/robo.png");
        productApplication.update(10, produtoAAtualizar);

        String image = productApplication.getById(10).getImage();

        assertTrue(image.contains("robo.png"));
    }

}
