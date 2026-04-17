package org.example;

import enums.FoodCategory;
import enums.OrderStatus;
import exceptions.ExpiredFoodException;
import models.*;
import services.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws ExpiredFoodException {
        // setup models
        Address customerAddress = new Address("Tbilisi", "Ortachala", "Building 5, Apt 12");
        Customer customer = new Customer("Lika", "likadatvi17@example.com", "+995568332112", "password", customerAddress);
        DeliveryPerson courier1 = new DeliveryPerson("Dato", "dato@example.com", "+995555555", "password", "gldani IV mikro");
        DeliveryPerson courier2 = new DeliveryPerson("Bob", "Bobbob@example.com", "+995111111", "password", "Varketili");
        Set<Address> addressSet = new HashSet<>();
        addressSet.add(new Address("Tbilisi", "Ortaachala", "Building 5, Apt 12"));
        addressSet.add((new Address("Batumi", "sanapiro", "building 6, Apt 13")));
        logger.info("Set size: " + addressSet.size());

        List<Food> demoList = new ArrayList<>();
        logger.info("List is empty? " + demoList.isEmpty());
        Food burger = new Food();
        Food strawberry = new Food();
        demoList.add(burger);
        demoList.add(strawberry);
        logger.info("List size after adding: " + demoList.size());
        Food firstFood = demoList.get(0);
        logger.info("First food in list: " + firstFood.getName());
        demoList.remove(burger);
        logger.info("List size after removing burger: " + demoList.size());

        logger.info("Address set is empty? " + addressSet.isEmpty());
        addressSet.remove(new Address("Batumi", "sanapiro", "building 6, Apt 13"));
        logger.info("Address set size after removal: " + addressSet.size());

        Map<Address, Customer> addressCustomerMap = new HashMap<>();
        addressCustomerMap.put(customerAddress, customer);
        logger.info("Map size after put: " + addressCustomerMap.size());
        Customer foundCustomer = addressCustomerMap.get(customerAddress);
        logger.info("Customer found by address: " + (foundCustomer != null ? foundCustomer.getName() : "Not found"));
        addressCustomerMap.remove(customerAddress);
        logger.info("Map size after removal: " + addressCustomerMap.size());
        logger.info("Map is empty? " + addressCustomerMap.isEmpty());

        //iteration part
        logger.info("iteration through customer invoice");

        customer.getInvoices().stream().map(Invoice::generateSummary).forEach(logger::info);

        logger.info("iteration though addresses");
        addressSet.stream().sorted(Comparator.comparing(Address::toString)).forEach(logger::info);

        logger.info("iteration through customer address");
        addressCustomerMap.entrySet().stream().map(entry -> "key: " + entry.getKey() + " - value: " + entry.getValue().getName())
                .forEach(logger::info);


        //retrive elementss


        demoList.stream().findFirst()
                .map(Food::getName)
                .ifPresent(name -> logger.info("first food from list: " + name));


        if (!addressSet.isEmpty()) {
            Address firstAddresInList = addressSet.iterator().next();
            logger.info("first addres in set: " + firstAddresInList);
        }

        if (addressCustomerMap.isEmpty()) {
            logger.info("address customer map is empty");
        }

        // using generics
        FoodBox<Food> foodBox = new FoodBox<>(strawberry);
        logger.info("What is in box: " + foodBox.get().getName());
        foodBox.set(burger);
        logger.info("what is in box now: " + foodBox.get().getName());

        Pair<Address, Customer> customerAndAddress = new Pair<>(customerAddress, customer);
        logger.info("pair: " + customerAndAddress);


        // setup services
        CartOperations cartService = new CartService(customer.getCart());
        PaymentProcessor paymentService = new PaymentService();
        DeliveryService deliveryService = new DeliveryService();
        DeliveryAssigner deliveryAssigner = deliveryService;
        OrderService orderService = new OrderService(cartService, paymentService, deliveryAssigner);
        RoleDescribable currentMember = customer;

        deliveryService.addDeliveryPerson(courier1);
        deliveryService.addDeliveryPerson(courier2);

        burger = new Food("Burger", BigDecimal.valueOf(8.50), 2, FoodCategory.BURGER);
        Food fries = new Food();
        fries.setName("Fries");
        fries.setFoodprice(BigDecimal.valueOf(3.00));
        fries.setExpiration(1);
        fries.setCategory(FoodCategory.FRIES);

        // cart operations
        cartService.addItem(burger);
        cartService.addItem(fries);
        logger.info(CartService.SERVICE_NAME + " TOTAL: " + cartService.calculateTotal());
        logger.info("CURRENT MEMBER: " + currentMember.getRoleName());
        logger.info("MEMBER STATE: " + customer.describeMemberState());

        // create order
        Order order = orderService.createOrder(customer);
        logger.info("ORDER CREATED: ORDER " + order.getId());

        // pay order
        Payment payment = orderService.payForOrder(order, Payment.Method.CARD);
        logger.info("GOOD PAYMENT: " + payment.isSuccess() + "  :  " + payment.getAmount());
        logger.info("PAYMENT RECORD: " + orderService.describeFinancialRecord(payment));
        logger.info("LATEST RECORD AFTER PAYMENT: " + orderService.describeLatestRecord());

        Invoice invoice = orderService.createInvoice(order);
        if (invoice != null) {
            logger.info("THIS IS THE INVOICE:" + invoice.generateSummary());
            logger.info("INVOICE RECORD: " + orderService.describeFinancialRecord(invoice));
            logger.info("LATEST RECORD AFTER INVOICE: " + orderService.describeLatestRecord());
        }
        // finish Order this prints itself
        orderService.finishOrder(order);

        // now SOLO delivery stuff
        OrderPlaces dropOff = new OrderPlaces();
        dropOff.setAddress(customerAddress);
        dropOff.setPlaceName("Alice's Place");
        dropOff.setOrder(order);
        deliveryAssigner.assignDelivery(order, null, dropOff);

        //lambdazz
        FoodDiscount discount = (food, percent) -> food.getFoodPrice().doubleValue() * (1 - percent / 100);
        double discounted = discount.applyDiscount(burger, 10);
        logger.info("discounted price: " + discounted);

        FoodFormatter formatter = food -> "Food: " + food.getName() + ", price: " + food.getFoodPrice();
        logger.info(formatter.format(burger));

        FoodChecker isExpiringSoon = food -> food.getExpiration() < 3;
        logger.info("is it  expiring ? " + isExpiringSoon.check(burger));

        //NEW CART METHOD WITH FUNCTIONS
        java.util.ArrayList<Food> expiringItems = cartService.getFilteredItems(isExpiringSoon);
        logger.info("Expiring items in cart: " + expiringItems.size());
        expiringItems.stream()
                .filter(isExpiringSoon::check)
                .map(f -> " - " + f.getName() + " expires in " + f.getExpiration() + " days")
                .forEach(logger::info);


        BigDecimal totalAfterDiscount = cartService.calculateDiscount(discount, 10);
        logger.info("Total cart price after 10% discount: " + totalAfterDiscount);

        String cartCategorySnapshot = List.of(burger, fries).stream()
                .filter(food -> food.getCategory() != null)
                .map(food -> food.getName() + "=" + food.getCategory().getCategory())
                .collect(Collectors.joining(", "));
        logger.info("Cart categories snapshot: " + cartCategorySnapshot);


        FoodFormatter printingFormatter = food -> {
            String s = "Cart item: " + food.getName() + " - " + food.getFoodPrice();
            logger.info(s);
            return s;
        };
        logger.info("Displaying cart with displayCart(formatter):");
        cartService.displayCart(printingFormatter);

        Runnable r = () -> logger.info("executed");
        r.run();

        FoodSummary summary = new FoodSummary(burger.getName(), burger.getFoodPrice().doubleValue(), burger.getExpiration());
        logger.info("food summary: " + summary);

        logger.info(OrderStatus.PAID.getDescription());

        // food cat
        logger.info("Burger category: " + burger.getCategory().getCategory());
        logger.info("Fries category: " + fries.getCategory().getCategory());

        // user role
        logger.info("Customer role: " + customer.getUserRole().getLabel());
        logger.info("Courier role: " + courier1.getUserRole().getLabel());

        // payment type
        logger.info("Payment type: " + payment.getPaymentType().getType());

        //reflection use
        runReflectionDemo();
        try (SupportService supportService = new SupportService()) {
            TicketResolver ticketResolver = supportService;

            // make a support ticket
            supportService.setCurrentActor(customer);
            logger.info("CURRENT ACTOR: " + supportService.describeCurrentActor());
            supportService.setCurrentActor(courier1);
            logger.info("CURRENT ACTOR: " + supportService.describeCurrentActor());

            supportService.makeComplaint(customer, order, "My fries were cold :(");
            logger.info("Open tickets: " + supportService.getTickets().size());
            logger.info("Ticket type: " + supportService.getTickets().get(0).getType().getKind());
            logger.info("Customer invoices: " + customer.getInvoices().size());
            logger.info("Customer support history: " + customer.getSupportTickets().size());

            // resolve ticket
            supportService.setCurrentActor(courier1);
            SupportResolution resolution = ticketResolver.resolveTicket(0, "No problem, we'll send a fresh portion.");
            if (resolution != null) {
                logger.info("Resolved by: " + resolution.getResolvedByLabel());
            }
            logger.info("Open tickets: " + supportService.getTickets().size());
        } catch (RuntimeException exception) {
            logger.info("Support error: " + exception.getMessage());
        }
    }
    //reflection method
    private static void runReflectionDemo() {
        try {
            Class<?> foodClass = Class.forName("models.Food");
            logger.info(" refleciton ");
            printReflectionDetails(foodClass);

            java.util.Optional.ofNullable(foodClass.getAnnotation(annotations.ReflectInfo.class))
                    .ifPresent(annotation -> logger.info("annotation -> owner=" + annotation.owner() + ", purpose=" + annotation.purpose()));

            // Object creation and method calls are done only with reflection in this block.
            java.lang.reflect.Constructor<?> constructor = foodClass.getDeclaredConstructor();
            Object reflectedFood = constructor.newInstance();

            java.lang.reflect.Method setName = foodClass.getMethod("setName", String.class);
            java.lang.reflect.Method setPrice = foodClass.getMethod("setFoodprice", java.math.BigDecimal.class);
            java.lang.reflect.Method setExpiration = foodClass.getMethod("setExpiration", int.class);
            java.lang.reflect.Method getName = foodClass.getMethod("getName");
            java.lang.reflect.Method getPrice = foodClass.getMethod("getFoodPrice");

            setName.invoke(reflectedFood, "reflection Burger");
            setPrice.invoke(reflectedFood, java.math.BigDecimal.valueOf(5.55));
            setExpiration.invoke(reflectedFood, 2);

            logger.info("reflected object -> name=" + getName.invoke(reflectedFood) + ", price=" + getPrice.invoke(reflectedFood));
        } catch (ReflectiveOperationException reflectionError) {
            logger.info("reflection error: " + reflectionError.getMessage());
        }
    }

    private static void printReflectionDetails(Class<?> type) {
        java.util.Arrays.stream(type.getDeclaredFields())
                .sorted(java.util.Comparator.comparing(java.lang.reflect.Field::getName))
                .forEach(field -> logger.info("Field: "
                        + java.lang.reflect.Modifier.toString(field.getModifiers())
                        + " " + field.getType().getSimpleName()
                        + " " + field.getName()));

        java.util.Arrays.stream(type.getDeclaredConstructors())
                .sorted(java.util.Comparator.comparing(java.lang.reflect.Constructor::toString))
                .forEach(constructor -> logger.info("CTOR: "
                        + java.lang.reflect.Modifier.toString(constructor.getModifiers())
                        + " " + constructor.getName()
                        + "(" + formatParameters(constructor) + ")"));

        java.util.Arrays.stream(type.getDeclaredMethods())
                .sorted(java.util.Comparator.comparing(java.lang.reflect.Method::getName))
                .forEach(method -> logger.info("Method: "
                        + java.lang.reflect.Modifier.toString(method.getModifiers())
                        + " " + method.getReturnType().getSimpleName()
                        + " " + method.getName()
                        + "(" + formatParameters(method) + ")"));
    }

    private static String formatParameters(java.lang.reflect.Executable executable) {
        return java.util.Arrays.stream(executable.getParameters())
                .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
                .collect(java.util.stream.Collectors.joining(", "));
    }
}
