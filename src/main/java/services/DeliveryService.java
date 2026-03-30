package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.InvalidAddressException;
import models.DeliveryPerson;
import models.Order;
import models.OrderPlaces;

public final class DeliveryService implements DeliveryAssigner {

    private List<DeliveryPerson> deliveryPeople = new ArrayList<>();

    public DeliveryService(List<DeliveryPerson> deliveryPeople) {
        this.deliveryPeople = deliveryPeople;
    }

    public DeliveryService() {
        this.deliveryPeople = new ArrayList<>();
    }

    //same here had list :( other way would be to have arrays with fixed sizes like 100 lets say
    public void addDeliveryPerson(DeliveryPerson person) {
        deliveryPeople.add(person);
    }

    public void assignDelivery(Order order, DeliveryPerson deliveryPerson, OrderPlaces location) {
        if (location == null) {
            throw new InvalidAddressException("location is not known");
        }
        DeliveryPerson assignee = deliveryPerson;
        if (assignee == null && !deliveryPeople.isEmpty()) {
            assignee = deliveryPeople.get(deliveryPeople.size() - 1);
        }
        if (assignee == null) {
            System.out.println("No delivery avail " + order.getId());
            return;
        }
        location.setOrder(order);
        order.assignDeliveryPlace(location);
        assignee.setDestinationAddress(location.getAddress());
        assignee.addOrder(location);
        System.out.println("Order " + order.getId() + " assigned to " + assignee.getName() + " for " + location.getPlaceName());
    }


    public List<DeliveryPerson> getDeliveryPeople() {
        return new ArrayList<>(deliveryPeople);
    }
}
