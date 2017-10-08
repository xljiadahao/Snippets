package com.snippet.java8.feature.functionalinterface.lambda.streamapi;

import com.snippet.java8.feature.OnlinePaymentMethod;
import com.snippet.java8.feature.ShoppingCart;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xulei
 */
public class OnlinePayment implements Payment {

    private OnlinePaymentMethod paymentMethod;
    
    @Autowired
    public OnlinePayment(OnlinePaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public void pay(ShoppingCart shoppingCart, Predicate<Integer> isReceiptRequired) {
        Optional<Integer> totalAmount = calculateTotalAmount(shoppingCart);
        StringBuilder strPay = new StringBuilder();
        strPay.append("Pay SGD").append(totalAmount.get())
                .append(" with ").append(paymentMethod.name())
                .append(" on ").append(new Date());
        System.out.println(strPay.toString());
        System.out.println();
        System.out.println("RECEIPT");
        if (isReceiptRequired.test(totalAmount.get())) {
            System.out.println(printReceipt(shoppingCart));
        }
    }
    
}
