package com.kaampay.service;

import com.kaampay.entity.SubscriptionPlan;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final SubscriptionPlanService planService;

    public PaymentService(SubscriptionPlanService planService) {
        this.planService = planService;
    }

    public String createRazorpayOrder(String planId) throws RazorpayException {
        SubscriptionPlan plan = planService.getPlanById(planId);
        RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

        int amountInPaise = plan.getPrice().multiply(new BigDecimal("100")).intValue();

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", plan.getCurrency());
        orderRequest.put("receipt", "txn_" + UUID.randomUUID().toString().substring(0, 8));

        Order order = razorpay.orders.create(orderRequest);
        return order.get("id");
    }

    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", signature);

            return Utils.verifyPaymentSignature(options, keySecret);
        } catch (RazorpayException e) {
            return false;
        }
    }
}