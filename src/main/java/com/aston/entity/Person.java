package com.aston.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сущность Person
 */
@Data
@AllArgsConstructor
public class Person {

    private String name;
    private String lastName;
}
