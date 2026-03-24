package services;

import models.Customer;
import models.Order;
import models.RoleDescribable;
import models.Support;
import models.SupportResolution;

public class SupportService implements TicketResolver {

    private RoleDescribable currentActor;
    private Support[] tickets = new Support[0];

    public Support[] getTickets() {
        return this.tickets;
    }

    public void makeComplaint(Customer customer, String message) {
        makeComplaint(customer, null, message);
    }

    public void makeComplaint(Customer customer, Order relatedOrder, String message) {
        Support ticket = new Support(customer, relatedOrder, message);
        Support[] next = new Support[tickets.length + 1];
        System.arraycopy(tickets, 0, next, 0, tickets.length);
        next[next.length - 1] = ticket;
        tickets = next;
        customer.addSupportTicket(ticket);
        System.out.println("Thank you, support will answer shortly");
    }

    public SupportResolution resolveTicket(int index, String message) {
        return resolveTicket(index, message, currentActor);
    }

    public SupportResolution resolveTicket(int index, String message, RoleDescribable resolvedBy) {
        if (index < 0 || index >= tickets.length) {
            throw new InvalidIndexException("Invalid index");
        }
        Support ticket = tickets[index];
        SupportResolution resolution = new SupportResolution(
                ticket,
                resolvedBy != null ? resolvedBy : currentActor,
                message
        );
        ticket.close(resolution);
        tickets = removeElement(tickets, index);
        System.out.println("Ticket was resolved successfully (hopefully): \nREASON:" + resolution.getMessage());
        return resolution;
    }

    public SupportResolution resolveTicket(Support ticket, String message) {
        SupportResolution resolution = new SupportResolution(ticket, currentActor, message);
        ticket.close(resolution);
        tickets = removeElement(tickets, ticket);
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
}
