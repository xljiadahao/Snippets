package com.snippet.java8.feature.functionalinterface.lambda.streamapi;

import com.snippet.java8.feature.Category;
import com.snippet.java8.feature.Product;
import com.snippet.java8.feature.ShoppingCart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * knowledge point 1. Functional Interface, sun interface, single abstract method interface, 
 * Lambda Expression can be only used for Functional Interface;
 * 
 * knowledge point 2. Java 8 introduces the default method in the interface for the default implementation;
 * 
 * knowledge point 3. 6 types of functional interfaces, 
 * a. Predicate, (I) -> boolean, replace if else
 * b. Function, (I) -> R, transformer, one type to another type
 * c. Consumer, (I) -> void, consumer the input data
 * d. Supplier, () -> new (), factory pattern
 * e. BinaryOperator, (I, I) -> I, reducers, accumulation
 * f. UnaryOperator, (I) -> I
 * 
 * knowledge point 4. Lambda Expression;
 * 
 * knowledge point 5. Stream API, for collections, stream API allows iterate each item of the collection, 
 * and enqueue each item within the pipeline to process, such as filter, for-each, map-reduce;
 * 
 * @author xulei
 */
@FunctionalInterface
public interface Payment {
    
    public void pay(ShoppingCart shoppingCart, Predicate<Integer> isReceiptRequired);
    
    /**
     * for the shopping cart, iterate the product list parallelly, 
     * filter out the illegal items which id is blank or price is not larger than 0, 
     * map each product to its price,
     * reduce to accumulate each product price for the checkout total amount.
     * 
     * @param shoppingCart
     * @return totalCheckoutAmount
     */
    public default Optional<Integer> calculateTotalAmount(ShoppingCart shoppingCart) {
        Predicate<Product> filterCondition = (Product p) -> 
                StringUtils.isNotBlank(p.getId()) && p.getPrice() > 0;
        Function<Product, Integer> mapToPrice = (Product p) -> p.getPrice() * p.getQuantity();
        BinaryOperator<Integer> reduceCheckoutAmount = 
                (Integer price0, Integer price1) -> price0 + price1;
        /**
         * stream (for Collections) 1. filter, 2. map, 3. reduce.
         * fork join pool with concurrent processing
         */
        return shoppingCart.getProducts().stream().parallel()
                .filter(filterCondition).map(mapToPrice).reduce(reduceCheckoutAmount);
    }
    
    /**
     * for the shopping cart, classify the products by category, 
     * list down the products under each category, then calculate total amount,
     * 
     * @param shoppingCart
     * @return receipt
     */
    public default String printReceipt(ShoppingCart shoppingCart) {
        StringBuilder receipt = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(Category category : Category.values()) {
            receipt.append("$$$$$$$$$").append(category.name()).append("$$$$$$$$$").append("\n");
            shoppingCart.getProducts().stream()
                .filter((Product p) -> p.getCategory() == category).sorted((Product o1, Product o2)  
                    -> o1.getId().equals(o2.getId()) ? 0 : (o1.getPrice() > o2.getPrice() ? 1 : -1))
                .forEach((Product p) -> receipt.append(p.getId()).append("  ").append(p.getName())
                    .append("  ").append("X").append(p.getQuantity()).append("  ").append("SGD")
                    .append(p.getPrice()).append("\n"));
        }
        receipt.append("^^^^^^^^^^^^^^^^^^^^").append("\n");
        receipt.append("Checkout Total Amount: ").append(calculateTotalAmount(shoppingCart).get())
                .append(" SGD").append("\n");
        receipt.append("Transaction Time: ").append(sdf.format(new Date())).append("\n");
        return receipt.toString();
    }
    
}
