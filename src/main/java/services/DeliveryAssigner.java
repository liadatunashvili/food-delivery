package services;

import models.DeliveryPerson;
import models.Order;
import models.OrderPlaces;

public interface DeliveryAssigner {
    void assignDelivery(Order order, DeliveryPerson deliveryPerson, OrderPlaces location);
}

