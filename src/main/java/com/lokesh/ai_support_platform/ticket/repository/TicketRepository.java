package com.lokesh.ai_support_platform.ticket.repository;

import com.lokesh.ai_support_platform.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
