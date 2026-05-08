package services;
import enums.FoodCategory;
import jaxb.*;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
public class JaxbParser implements Parser {
    private static final Logger logger = LogManager.getLogger(JaxbParser.class);
    @Override
    public Order parse(String resource) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resource)) {
            JAXBContext context = JAXBContext.newInstance(OrderXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            OrderXml orderXml = (OrderXml) unmarshaller.unmarshal(is);
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
            logger.info("[JaxB] parsed order for customer='{}', items={}, success={}",
                    customer.getName(), items.size(), payment.isSuccess());
            return order;
        } catch (Exception e) {
            logger.error("[JaxB] parse failed: " + e.getMessage(), e);
            return null;
        }
    }
}
