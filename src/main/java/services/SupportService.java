package services;

import Exceptions.InvalidIndexException;
import models.Customer;
import models.Order;
import models.RoleDescribable;
import models.Support;
import models.SupportResolution;

import java.util.ArrayList;
import java.util.List;

public class SupportService implements TicketResolver, AutoCloseable {

    private RoleDescribable currentActor;
    private List<Support> tickets = new ArrayList<>();

    public List<Support> getTickets() {
        return new ArrayList<>(tickets);
    }

    public void makeComplaint(Customer customer, String message) {
        makeComplaint(customer, null, message);
    }

    public void makeComplaint(Customer customer, Order relatedOrder, String message) {
        Support ticket = new Support(customer, relatedOrder, message);
        tickets.add(ticket);
        customer.addSupportTicket(ticket);
        System.out.println("Thank you, support will answer shortly");
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
        System.out.println("Ticket was resolved successfully (hopefully): \nREASON:" + resolution.getMessage());
        return resolution;
    }

    public SupportResolution resolveTicket(Support ticket, String message) {
        SupportResolution resolution = new SupportResolution(ticket, currentActor, message);
        ticket.close(resolution);
        tickets.remove(ticket);
        System.out.println("Ticket was resolved successfully (hopefully): \nREASON:" + resolution.getMessage());
        return resolution;
    }

    private Support[] removeElement(Support[] array, int index) {
        Support[] result = new Support[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        return result;
    }

    private Support[] removeElement(Support[] array, Support element) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return array;
        }
        return removeElement(array, index);
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
