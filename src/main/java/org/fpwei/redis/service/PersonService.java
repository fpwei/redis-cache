package org.fpwei.redis.service;

import org.fpwei.redis.org.fpwei.redis.entity.Person;

import java.util.List;

public interface PersonService {

    List<Person> getPersonByFirstName(String firstName);

    void addPerson(Person person);

}
