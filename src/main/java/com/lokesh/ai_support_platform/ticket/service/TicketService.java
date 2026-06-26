package com.lokesh.ai_support_platform.ticket.service;

import com.lokesh.ai_support_platform.auth.entity.User;
import com.lokesh.ai_support_platform.auth.exception.UserNotFoundException;
import com.lokesh.ai_support_platform.auth.repository.UserRepository;
import com.lokesh.ai_support_platform.ticket.dto.CreateTicketRequest;
import com.lokesh.ai_support_platform.ticket.entity.Ticket;
import com.lokesh.ai_support_platform.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository ;

    private final UserRepository userRepository ;

    public Ticket createTicket(CreateTicketRequest createTicketRequest,Long userId)
    {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Ticket ticket = Ticket.builder()
                .title(createTicketRequest.getTitle())
                .description(createTicketRequest.getDescription())
                .priority(createTicketRequest.getPriority())
                .user(user)
                .build();

        return ticketRepository.save(ticket);
    }
}
