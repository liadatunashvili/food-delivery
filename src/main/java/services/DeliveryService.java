package services;

import java.util.Arrays;

import Exceptions.InvalidAddressException;
import models.DeliveryPerson;
import models.Order;
import models.OrderPlaces;

public final class DeliveryService implements DeliveryAssigner {

    private DeliveryPerson[] deliveryPeople = new DeliveryPerson[0];

    public DeliveryService(DeliveryPerson[] deliveryPeople) {
        this.deliveryPeople = deliveryPeople;
    }

    public DeliveryService() {
        this.deliveryPeople = new DeliveryPerson[0];
    }

    //same here had list :( other way would be to have arrays with fixed sizes like 100 lets say
    public void addDeliveryPerson(DeliveryPerson person) {
        DeliveryPerson[] next = Arrays.copyOf(deliveryPeople, deliveryPeople.length + 1);
        next[next.length - 1] = person;
        deliveryPeople = next;
    }

    public void assignDelivery(Order order, DeliveryPerson deliveryPerson, OrderPlaces location) {
        if (location == null) {
            throw new InvalidAddressException("location is not known");
        }
        DeliveryPerson assignee = deliveryPerson;
        if (assignee == null && deliveryPeople.length > 0) {
            assignee = deliveryPeople[deliveryPeople.length - 1];
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

    public DeliveryPerson[] getDeliveryPeople() {
        return Arrays.copyOf(deliveryPeople, deliveryPeople.length);
    }
}
