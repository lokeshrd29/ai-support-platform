package com.lokesh.ai_support_platform.ticket.dto;

import com.lokesh.ai_support_platform.common.enums.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {

    private String title ;

    private String description ;

    private Priority priority;

}
