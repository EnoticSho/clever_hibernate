package ru.clevertec.ecl.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private final UUID resourceId;
    private final Class<?> resourceType;

    public ResourceNotFoundException(UUID resourceId, Class<?> resourceType) {
        super(String.format("%s with uuid: %s not found", resourceType.getSimpleName(), resourceId));
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }
}
