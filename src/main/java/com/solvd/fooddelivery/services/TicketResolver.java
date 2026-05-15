package com.solvd.fooddelivery.services;

import com.solvd.fooddelivery.models.Support;
import com.solvd.fooddelivery.models.SupportResolution;

public interface TicketResolver {
    SupportResolution resolveTicket(int index, String message);

    SupportResolution resolveTicket(Support ticket, String message);
}

