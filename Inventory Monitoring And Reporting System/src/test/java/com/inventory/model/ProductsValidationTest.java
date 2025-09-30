package com.inventory.model;
import org.junit.Assert;
import org.junit.Test;

import java.util.InputMismatchException;


public class ProductsValidationTest {
    @Test
    public void testProductsValidation() throws InputMismatchException {
        Product p=new Product(1,"mouse","electronic",10,300);
        Assert.assertEquals(1,p.getId());
        Assert.assertEquals("mouse",p.getName());
    }
    @Test
    public void testInvalidPriceThrowsException() {
        Product p = new Product(1,"mouse","electronic",10,300);
        Exception ex = Assert.assertThrows(
                InputMismatchException.class,
                () -> p.setPrice(-300)
        );
        Assert.assertEquals("Price cannot be negitive", ex.getMessage());
    }
    @Test
    public void testInvalidQuantityThrowsException() {
        Product p = new Product(1,"mouse","electronic",10,300);
        Exception ex = Assert.assertThrows(
                InputMismatchException.class,
                () -> p.setQuantity(-5)
        );
        Assert.assertEquals("Quantity must be greater than 0", ex.getMessage());
    }


}
