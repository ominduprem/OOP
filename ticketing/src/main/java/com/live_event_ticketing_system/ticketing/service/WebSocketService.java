package com.live_event_ticketing_system.ticketing.service;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.live_event_ticketing_system.ticketing.model.TicketTransaction;
import com.live_event_ticketing_system.ticketing.model.TicketUpdate;
import com.live_event_ticketing_system.ticketing.model.WebSocketMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Queue<TicketTransaction> recentTransactions;
    private static final int MAX_RECENT_TRANSACTIONS = 10;
    
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.recentTransactions = new ConcurrentLinkedQueue<>();
    }

    public void broadcastTicketUpdate(int available, int sold) {
        TicketUpdate update = new TicketUpdate(
            available,
            sold,
            new ArrayList<>(recentTransactions)
        );

        messagingTemplate.convertAndSend("/topic/tickets", new WebSocketMessage(
            "TICKET_UPDATE",
            update
        ));
    }

    public void broadcastTransaction(TicketTransaction transaction) {
        recentTransactions.offer(transaction);
        if (recentTransactions.size() > MAX_RECENT_TRANSACTIONS) {
            recentTransactions.poll();
        }
        
        messagingTemplate.convertAndSend("/topic/transactions", new WebSocketMessage(
            "TRANSACTION_UPDATE",
            transaction
        ));
    }
}
