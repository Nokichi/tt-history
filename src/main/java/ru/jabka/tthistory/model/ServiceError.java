package ru.jabka.tthistory.model;

import lombok.Builder;

@Builder
public record ServiceError(Boolean success, String message) {
}