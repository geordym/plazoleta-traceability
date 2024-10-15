package com.plazoleta.traceability.domain.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public record OrderCompletion(
        Long orderId,
        LocalDateTime orderStartedAt,
        LocalDateTime orderFinishedAt,
        Double finishedInSeconds,
        Long employeeId
) {
}