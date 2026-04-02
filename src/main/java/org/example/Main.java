package org.example;

import enums.FoodCategory;
import enums.OrderStatus;
import exceptions.ExpiredFoodException;
import models.*;
import services.*;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ExpiredFoodException {
        // setup models
        Address customerAddress = new Address("Tbilisi", "Gldani", "Building 5, Apt 12");
        Customer customer = new Customer("Lika", "likadatvi17@example.com", "+995568332112", "password", customerAddress);
        DeliveryPerson courier1 = new DeliveryPerson("Dato", "dato@example.com", "+995555555", "password", "gldani IV mikro");
        DeliveryPerson courier2 = new DeliveryPerson("Bob", "Bobbob@example.com", "+995111111", "password", "Varketili");
        Set<Address> addressSet = new HashSet<>();
        addressSet.add(new Address("Tbilisi", "Gldami", "Building 5, Apt 12"));
        addressSet.add((new Address("Batumi", "sanapiro", "building 6, Apt 13")));
        System.out.println("Set size: " + addressSet.size());

        List<Food> demoList = new ArrayList<>();
        System.out.println("List is empty? " + demoList.isEmpty());
        Food burger = new Food();
        Food strawberry = new Food();
        demoList.add(burger);
        demoList.add(strawberry);
        System.out.println("List size after adding: " + demoList.size());
        Food firstFood = demoList.get(0);
        System.out.println("First food in list: " + firstFood.getName());
        demoList.remove(burger);
        System.out.println("List size after removing burger: " + demoList.size());

        System.out.println("Address set is empty? " + addressSet.isEmpty());
        addressSet.remove(new Address("Batumi", "sanapiro", "building 6, Apt 13"));
        System.out.println("Address set size after removal: " + addressSet.size());

        Map<Address, Customer> addressCustomerMap = new HashMap<>();
        addressCustomerMap.put(customerAddress, customer);
        System.out.println("Map size after put: " + addressCustomerMap.size());
        Customer foundCustomer = addressCustomerMap.get(customerAddress);
        System.out.println("Customer found by address: " + (foundCustomer != null ? foundCustomer.getName() : "Not found"));
        addressCustomerMap.remove(customerAddress);
        System.out.println("Map size after removal: " + addressCustomerMap.size());
        System.out.println("Map is empty? " + addressCustomerMap.isEmpty());

        //iteration part
        System.out.println("iteration through customer invoice");
        for (Invoice invoice : customer.getInvoices()) {
            System.out.println(invoice.generateSummary());
        }

        System.out.println("iteration though addresses");
        for (Address address : addressSet) {
            System.out.println(address);
        }

        System.out.println("iteration through customer address");
        for (Map.Entry<Address, Customer> entry : addressCustomerMap.entrySet()) {
            System.out.println("key: " + entry.getKey() + " - value: " + entry.getValue().getName());
        }

        //retrive elementss

        if (!demoList.isEmpty()) {
            Food firstFoodInList = demoList.getFirst();
            System.out.println("first food from list: " + firstFoodInList.getName());
        }

        if (!addressSet.isEmpty()) {
            Address firstAddresInList = addressSet.iterator().next();
            System.out.println("first addres in set: " + firstAddresInList);
        }

        if (addressCustomerMap.isEmpty()) {
            Map.Entry<Address, Customer> firstPair = addressCustomerMap.entrySet().iterator().next();
            System.out.println("first pair's key: " + firstPair.getKey());
            System.out.println("first pair in map: " + firstPair.getValue().getName());
        }

        // using generics
        FoodBox<Food> foodBox = new FoodBox<>(strawberry);
        System.out.println("What is in box: " + foodBox.get().getName());
        foodBox.set(burger);
        System.out.println("what is in box now: " + foodBox.get().getName());

        Pair<Address, Customer> customerAndAddress = new Pair<>(customerAddress, customer);
        System.out.println("pair: " + customerAndAddress);


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
        System.out.println(CartService.SERVICE_NAME + " TOTAL: " + cartService.calculateTotal());
        System.out.println("CURRENT MEMBER: " + currentMember.getRoleName());
        System.out.println("MEMBER STATE: " + customer.describeMemberState());

        // create order
        Order order = orderService.createOrder(customer);
        System.out.println("ORDER CREATED: ORDER " + order.getId());

        // pay order
        Payment payment = orderService.payForOrder(order, Payment.Method.CARD);
        System.out.println("GOOD PAYMENT: " + payment.isSuccess() + "  :  " + payment.getAmount());
        System.out.println("PAYMENT RECORD: " + orderService.describeFinancialRecord(payment));
        System.out.println("LATEST RECORD AFTER PAYMENT: " + orderService.describeLatestRecord());

        Invoice invoice = orderService.createInvoice(order);
        if (invoice != null) {
            System.out.println("THIS IS THE INVOICE:" + invoice.generateSummary());
            System.out.println("INVOICE RECORD: " + orderService.describeFinancialRecord(invoice));
            System.out.println("LATEST RECORD AFTER INVOICE: " + orderService.describeLatestRecord());
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
        System.out.println("discounted price: " + discounted);

        FoodFormatter formatter = food -> "Food: " + food.getName() + ", price: " + food.getFoodPrice();
        System.out.println(formatter.format(burger));

        FoodChecker isExpiringSoon = food -> food.getExpiration() < 3;
        System.out.println("is it  expiring ? " + isExpiringSoon.check(burger));

        //NEW CART METHOD WITH FUNCTIONS
        java.util.ArrayList<Food> expiringItems = cartService.getFilteredItems(isExpiringSoon);
        System.out.println("Expiring items in cart: " + expiringItems.size());
        for (Food f : expiringItems) {
            System.out.println(" - " + f.getName() + " expires in " + f.getExpiration() + " days");
        }

        BigDecimal totalAfterDiscount = cartService.calculateDiscount(discount, 10);
        System.out.println("Total cart price after 10% discount: " + totalAfterDiscount);

        FoodFormatter printingFormatter = food -> {
            String s = "Cart item: " + food.getName() + " - " + food.getFoodPrice();
            System.out.println(s);
            return s;
        };
        System.out.println("Displaying cart with displayCart(formatter):");
        cartService.displayCart(printingFormatter);

        Runnable r = () -> System.out.println("executed");
        r.run();

        FoodSummary summary = new FoodSummary(burger.getName(), burger.getFoodPrice().doubleValue(), burger.getExpiration());
        System.out.println("food summary: " + summary);

        System.out.println(OrderStatus.PAID.getDescription());

        // food cat
        System.out.println("Burger category: " + burger.getCategory().getCategory());
        System.out.println("Fries category: " + fries.getCategory().getCategory());

        // user role
        System.out.println("Customer role: " + customer.getUserRole().getLabel());
        System.out.println("Courier role: " + courier1.getUserRole().getLabel());

        // payment type
        System.out.println("Payment type: " + payment.getPaymentType().getType());


        try (SupportService supportService = new SupportService()) {
            TicketResolver ticketResolver = supportService;

            // make a support ticket
            supportService.setCurrentActor(customer);
            System.out.println("CURRENT ACTOR: " + supportService.describeCurrentActor());
            supportService.setCurrentActor(courier1);
            System.out.println("CURRENT ACTOR: " + supportService.describeCurrentActor());

            supportService.makeComplaint(customer, order, "My fries were cold :(");
            System.out.println("Open tickets: " + supportService.getTickets().size());
            System.out.println("Ticket type: " + supportService.getTickets().get(0).getType().getKind());
            System.out.println("Customer invoices: " + customer.getInvoices().size());
            System.out.println("Customer support history: " + customer.getSupportTickets().size());

            // resolve ticket
            supportService.setCurrentActor(courier1);
            SupportResolution resolution = ticketResolver.resolveTicket(0, "No problem, we'll send a fresh portion.");
            if (resolution != null) {
                System.out.println("Resolved by: " + resolution.getResolvedByLabel());
            }
            System.out.println("Open tickets: " + supportService.getTickets().size());
        } catch (RuntimeException exception) {
            System.out.println("Support error: " + exception.getMessage());
        }
    }
}