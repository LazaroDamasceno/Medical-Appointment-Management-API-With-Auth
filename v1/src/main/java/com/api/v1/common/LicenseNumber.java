package com.api.v1.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@NotNull
@Size(min = 10, max = 10)
public @interface LicenseNumber {
    String message() default "Invalid license number format. Please enter a 10-digit license number.";
}