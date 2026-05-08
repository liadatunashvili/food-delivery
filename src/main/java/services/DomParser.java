package services;
import enums.FoodCategory;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DomParser implements Parser {
    private static final Logger logger = LogManager.getLogger(DomParser.class);
    @Override
    public Order parse(String resource) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resource)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            XPath xpath = XPathFactory.newInstance().newXPath();
            String orderId = xpath.evaluate("/order/@id", doc);
            logger.info("[DOM] order id: " + orderId);
            String customerName = xpath.evaluate("/order/customer/name", doc);
            logger.info("[DOM] customer name: " + customerName);
            NodeList foodNodes = (NodeList) xpath.evaluate("/order/items/food", doc, XPathConstants.NODESET);
            logger.info("[DOM] food items count: " + foodNodes.getLength());
            String firstFoodPrice = xpath.evaluate("/order/items/food[1]/price", doc);
            logger.info("[DOM] first food price: " + firstFoodPrice);
            String paymentSuccess = xpath.evaluate("/order/payment/success", doc);
            logger.info("[DOM] payment success: " + paymentSuccess);
            Element customerEl = (Element) doc.getElementsByTagName("customer").item(0);
            int customerId = Integer.parseInt(customerEl.getAttribute("customerId"));
            String email    = getTagValue("email", customerEl);
            String phone    = getTagValue("phone", customerEl);
            Element addressEl = (Element) customerEl.getElementsByTagName("address").item(0);
            String city    = getTagValue("city", addressEl);
            String street  = getTagValue("street", addressEl);
            String details = getTagValue("details", addressEl);
            Customer customer = new Customer(customerName, email, phone, "N/A", customerId, null, city, street, details);
            Map<Food, Integer> items = new HashMap<>();
            List<Food> foodList = new ArrayList<>();
            for (int i = 0; i < foodNodes.getLength(); i++) {
                Element foodEl = (Element) foodNodes.item(i);
                String foodName   = getTagValue("name", foodEl);
                BigDecimal price  = new BigDecimal(getTagValue("price", foodEl));
                int expiration    = Integer.parseInt(getTagValue("expiration", foodEl));
                FoodCategory cat  = FoodCategory.valueOf(getTagValue("category", foodEl));
                Food food = new Food(foodName, price, expiration, cat);
                items.put(food, 1);
                foodList.add(food);
            }
            Element orderEl = doc.getDocumentElement();
            BigDecimal total = new BigDecimal(getTagValue("total", orderEl));
            Order order = new Order(customer, items, total);
            Element paymentEl = (Element) doc.getElementsByTagName("payment").item(0);
            BigDecimal amount  = new BigDecimal(getTagValue("amount", paymentEl));
            String methodStr   = getTagValue("method", paymentEl);
            boolean success    = Boolean.parseBoolean(getTagValue("success", paymentEl));
            Payment.Method method = Payment.Method.valueOf(methodStr);
            Payment payment = new Payment(order, amount, method);
            if (success) payment.markSuccess();
            order.attachPayment(payment);
            logger.info("[DOM] parsed order #{} for customer '{}' with {} food item(s), payment success={}",
                    order.getId(), customer.getName(), foodList.size(), payment.isSuccess());
            return order;
        } catch (Exception e) {
            logger.error("[DOM] parse failed: " + e.getMessage(), e);
            return null;
        }
    }
    private String getTagValue(String tag, Element parent) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list.getLength() == 0) return "";
        return list.item(0).getTextContent().trim();
    }
}
