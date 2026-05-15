package com.solvd.fooddelivery.services;

import com.solvd.fooddelivery.models.DeliveryPerson;
import com.solvd.fooddelivery.models.Order;
import com.solvd.fooddelivery.models.OrderPlaces;

public interface DeliveryAssigner {

    void assignDelivery(Order order, DeliveryPerson deliveryPerson, OrderPlaces location);
}

