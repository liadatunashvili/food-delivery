package com.solvd.fooddelivery.services;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.fooddelivery.enums.FoodCategory;
import com.solvd.fooddelivery.jaxb.*;
import com.solvd.fooddelivery.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
public class JacksonParser implements Parser {
    private static final Logger logger = LogManager.getLogger(JacksonParser.class);
    @Override
    public Order parse(String resource) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resource)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OrderXml orderXml = mapper.readValue(is, OrderXml.class);
            CustomerXml cx = orderXml.getCustomer();
            AddressXml  ax = cx.getAddress();
            Customer customer = new Customer(
                    cx.getName(), cx.getEmail(), cx.getPhone(), "N/A",
                    cx.getCustomerId(), null,
                    ax.getCity(), ax.getStreet(), ax.getDetails()
            );
            Map<Food, Integer> items = new HashMap<>();
            for (FoodXml fx : orderXml.getItems().getFoods()) {
                Food food = new Food(
                        fx.getName(),
                        BigDecimal.valueOf(fx.getPrice()),
                        fx.getExpiration(),
                        FoodCategory.valueOf(fx.getCategory())
                );
                items.put(food, 1);
            }
            Order order = new Order(customer, items, BigDecimal.valueOf(orderXml.getTotal()));
            PaymentXml px = orderXml.getPayment();
            Payment payment = new Payment(order, BigDecimal.valueOf(px.getAmount()),
                    Payment.Method.valueOf(px.getMethod()));
            if (px.isSuccess()) payment.markSuccess();
            order.attachPayment(payment);
            logger.info("[Jackson] parsed order for customer='{}', items={}, success={}",
                    customer.getName(), items.size(), payment.isSuccess());
            return order;
        } catch (Exception e) {
            logger.error("[Jackson] parse failed: " + e.getMessage(), e);
            return null;
        }
    }
}
