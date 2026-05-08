package services;
import models.Order;
public interface Parser {
    Order parse(String resource);
}
