package com.live_event_ticketing_system.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdate {
    private int availableTickets;
    private int soldTickets;
    private java.util.List<TicketTransaction> recentTransactions;

}
