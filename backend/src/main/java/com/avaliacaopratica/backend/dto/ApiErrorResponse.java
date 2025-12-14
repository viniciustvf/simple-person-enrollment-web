package com.avaliacaopratica.backend.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {}