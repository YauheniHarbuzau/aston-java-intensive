package com.aston.entity.comparator;

import com.aston.entity.Person;

import java.util.Comparator;

/**
 * Компаратор, задающий условие сравнения для {@link Person}
 */
public class PersonNameComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        return person1.getName().compareTo(person2.getName());
    }
}
