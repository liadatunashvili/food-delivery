package services;

import models.Support;
import models.SupportResolution;

public interface TicketResolver {
    SupportResolution resolveTicket(int index, String message);

    SupportResolution resolveTicket(Support ticket, String message);
}

