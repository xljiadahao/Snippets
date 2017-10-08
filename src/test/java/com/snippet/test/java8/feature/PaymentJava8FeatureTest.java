package com.snippet.test.java8.feature;

import com.snippet.java8.feature.Category;
import com.snippet.java8.feature.Product;
import com.snippet.java8.feature.ShoppingCart;
import com.snippet.java8.feature.functionalinterface.lambda.streamapi.Checkout;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test Java8 features: functional interfaces, lambda expression, 
 * stream API, effective final, method reference.
 *
 * @author xulei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class PaymentJava8FeatureTest {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private Checkout checkout;
    
    @Before
    public void setUp() {
        assertTrue("spring container should not be null", ctx != null);
        assertTrue("checkout bean should not be null", ctx.getBean(Checkout.class) != null);
        assertTrue("onlinePayment bean should not be null", ctx.getBean("onlinePayment") != null);
        assertTrue("shoppingCart bean should not be null", ctx.getBean("shoppingCart") != null);

        ShoppingCart shoppingCart = checkout.getShoppingCart();
        shoppingCart.getProducts().add(assembleProduct("Samsonite Luggage", Category.BAG_SUITCASES, 280, 1));
        shoppingCart.getProducts().add(assembleProduct("CK Shirt", Category.CLOSING, 180, 2));
        shoppingCart.getProducts().add(assembleProduct("Tommy Tie", Category.CLOSING, 80, 2));
        shoppingCart.getProducts().add(assembleProduct("MI HiFi", Category.ELECTRONIC_EQUIPMENT, 50, 3));
        shoppingCart.getProducts().add(assembleProduct("MI Handset", Category.ELECTRONIC_EQUIPMENT, 200, 1));
        shoppingCart.getProducts().add(assembleProduct("Yangchenghu Crab", Category.FOOD, 10, 5));
        shoppingCart.getProducts().add(assembleProduct("IKEA Table Lamp", Category.HOME_DESIGN, 45, 1));
    }
    
    @Test
    public void paymentJava8Test() {
        checkout.checkout();
    }
    
    private Product assembleProduct(String name, Category category, int price, int quantity) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }

}
