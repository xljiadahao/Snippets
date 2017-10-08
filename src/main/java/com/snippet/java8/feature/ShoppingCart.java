package com.snippet.java8.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulei
 */
public class ShoppingCart {
    
    private List<Product> products = new ArrayList<>();

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
