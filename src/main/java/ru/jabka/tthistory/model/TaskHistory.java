package ru.jabka.tthistory.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskHistory(
        Long id,
        Long taskId,
        TaskData data,
        Long createdBy,
        LocalDateTime moment
) {
}