package com.aston;

import com.aston.entity.Person;
import com.aston.entity.comparator.PersonNameComparator;
import com.aston.list.CustomArrayList;
import com.aston.list.CustomList;

import java.util.Iterator;

/**
 * Main-класс - точка входа в приложение, содержит пример работы с {@link CustomArrayList}
 */
public class Main {

    public static void main(String[] args) {
        CustomList<Person> list = new CustomArrayList<>();
        list.add(new Person("Ivan", "Ivanov"));
        list.add(new Person("Ivan", "Alexov"));
        list.add(new Person("Alex", "Kirpichev"));
        list.add(new Person("Maria", "Sidorova"));
        list.add(new Person("Stan", "Ivanov"));
        list.add(new Person("Sara", "Kirpicheva"));

        list.sort(new PersonNameComparator());

        Iterator<Person> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
