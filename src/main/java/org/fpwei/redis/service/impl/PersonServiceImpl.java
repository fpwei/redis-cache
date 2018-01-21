package org.fpwei.redis.service.impl;

import org.fpwei.redis.org.fpwei.redis.entity.Person;
import org.fpwei.redis.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private static final List<Person> memoryDataBase;

    static {
        memoryDataBase = new ArrayList<>();

        memoryDataBase.add(new Person("001", "first1", "last1"));
        memoryDataBase.add(new Person("002", "first2", "last2"));
        memoryDataBase.add(new Person("003", "first3", "last3"));
        memoryDataBase.add(new Person("004", "first4", "last4"));
        memoryDataBase.add(new Person("005", "first5", "last5"));

    }


    @Override
    @Cacheable("person")
    public List<Person> getPersonByFirstName(String firstName) {

        log.debug("Search person from memory database.");

        List<Person> result = new ArrayList<>();
        memoryDataBase.forEach((person) -> {
            if (person.getFirstName().equals(firstName)) {
                result.add(person);
            }
        });

        return result;
    }

    @Override
    public void addPerson(Person person) {
        throw new UnsupportedOperationException();
    }


}
