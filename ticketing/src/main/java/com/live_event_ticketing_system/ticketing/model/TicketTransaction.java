package com.live_event_ticketing_system.ticketing.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TicketTransaction {
    private String type; //Purchase or Release
    private String userId;
    private LocalDateTime timestamp;
    private String ticketId;

}
