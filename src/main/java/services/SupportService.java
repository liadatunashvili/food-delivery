package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.InvalidIndexException;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class SupportService implements TicketResolver, AutoCloseable {
    private static final Logger logger = LogManager.getLogger(SupportService.class);


    private final List<Support> tickets = new ArrayList<>();
    private RoleDescribable currentActor;

    public List<Support> getTickets() {
        return tickets;
    }

    public void makeComplaint(Customer customer, String message) {
        makeComplaint(customer, null, message);
    }

    public void makeComplaint(Customer customer, Order relatedOrder, String message) {
        Support ticket = new Support(customer, relatedOrder, message);
        tickets.add(ticket);
        customer.addSupportTicket(ticket);
        logger.info("Thank you, support will answer shortly");
    }

    public SupportResolution resolveTicket(int index, String message) {
        return resolveTicket(index, message, currentActor);
    }

    public SupportResolution resolveTicket(int index, String message, RoleDescribable resolvedBy) {
        if (index < 0 || index >= tickets.size()) {
            throw new InvalidIndexException("Invalid index");
        }
        Support ticket = tickets.get(index);
        SupportResolution resolution = new SupportResolution(ticket, resolvedBy != null ? resolvedBy : currentActor, message);
        ticket.close(resolution);
        tickets.remove(index);
        logger.info("Ticket was resolved successfully (hopefully): \nREASON:" + resolution.message());
        return resolution;
    }

    public SupportResolution resolveTicket(Support ticket, String message) {
        SupportResolution resolution = new SupportResolution(ticket, currentActor, message);
        ticket.close(resolution);
        tickets.remove(ticket);
        logger.info("Ticket was resolved successfully (hopefully): \nREASON:" + resolution.message());
        return resolution;
    }

    private List<Support> removeElement(List<Support> array, int index) {
        array.remove(index);
        return array;
    }

    private List<Support> removeElement(List<Support> array, Support element) {
        array.remove(element);
        return array;
    }

    public void setCurrentActor(RoleDescribable currentActor) {
        this.currentActor = currentActor;
    }

    public String describeCurrentActor() {
        if (currentActor == null) {
            return "No actor selected";
        }
        return currentActor.getRoleName();
    }

    @Override
    public void close() {
        currentActor = null;
    }
}
