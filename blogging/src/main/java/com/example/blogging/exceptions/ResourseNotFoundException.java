package com.example.blogging.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourseNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    String email;

    public ResourseNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s %s ", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourseNotFoundException(String message) {
        super(message);
    }
}
