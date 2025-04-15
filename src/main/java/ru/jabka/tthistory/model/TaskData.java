package ru.jabka.tthistory.model;

import java.time.LocalDate;

public record TaskData(
        String title,
        String description,
        TaskStatus status,
        LocalDate deadLine,
        Long assignee
) {
}