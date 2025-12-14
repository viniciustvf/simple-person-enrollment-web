package com.avaliacaopratica.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiErrorResponse(int status, String error, String message, LocalDateTime timestamp) {}