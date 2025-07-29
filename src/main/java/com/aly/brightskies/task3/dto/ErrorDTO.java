package com.aly.brightskies.task3.dto;

import java.time.Instant;

public record ErrorDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) { }
