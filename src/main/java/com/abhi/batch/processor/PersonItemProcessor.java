package com.abhi.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.abhi.batch.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(final Person person) throws Exception {

        return person;
    }
}
