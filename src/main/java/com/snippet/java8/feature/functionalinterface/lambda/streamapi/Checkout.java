package com.snippet.java8.feature.functionalinterface.lambda.streamapi;

import com.snippet.java8.feature.ShoppingCart;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * knowledge point 6. Effective Final, for the local variable for lambda expression, 
 * it will be changed to the final variable automatically;
 * 
 * knowledge point 7. Method Reference.
 * 
 * @author xulei
 */
@Component
public class Checkout {
    
    @Autowired
    private Payment payment;
    @Autowired
    private ShoppingCart shoppingCart;
    
    public void checkout() {
        System.out.println("---------Checkout with Cash---------");
        int cash = 10;
        Payment cashPay = (ShoppingCart shoppingCart, Predicate<Integer> isReceiptRequired) -> {
            /**
             * cannot change, effective final;
             * local variable for lambda expression, auto change to final variable.
             * 
             * cash = 2000;
             */
            System.out.println("Cash is NOT support");
        };
        cashPay.pay(getShoppingCart(), (Integer amount) -> false);
        System.out.println();
        
        System.out.println("---------Checkout with Cashless---------");
        /**
         * method reference
         */
        payment.pay(getShoppingCart(), this::isAskingReceipt);
    }
    
    private boolean isAskingReceipt(Integer amount) {
        return amount > 10;
    }

    /**
     * @return the shoppingCart
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * @param shoppingCart the shoppingCart to set
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
}
