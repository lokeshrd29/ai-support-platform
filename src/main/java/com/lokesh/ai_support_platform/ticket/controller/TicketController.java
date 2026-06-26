package com.lokesh.ai_support_platform.ticket.controller;

import com.lokesh.ai_support_platform.ticket.dto.CreateTicketRequest;
import com.lokesh.ai_support_platform.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService ;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateTicketRequest createTicketRequest ,HttpSession session)
    {
        Long userId = (Long) session.getAttribute("id");

        ticketService.createTicket(createTicketRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ticket Created Successfully");
    }

}
