package com.assignment.camelroutes.integration;

import com.assignment.camelroutes.domain.Person;
import com.assignment.camelroutes.respository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class QueueMessageHandler {

    private final PersonRepository personRepository;

    public QueueMessageHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void process(String message){

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            log.debug("Attempting to save Person in DB: {}", message);

            // Parse incoming JSON value to Person object
            Person person = objectMapper.readValue(message, Person.class);

            // Save Person object in DB
            personRepository.save(person);

        } catch (IOException exception) {
            log.error("Unable to parse incoming JSON to Person Object: {}", exception);
        }
    }
}
