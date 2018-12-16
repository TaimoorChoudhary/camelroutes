package com.assignment.camelroutes.integration;

import com.assignment.camelroutes.domain.Person;
import com.assignment.camelroutes.respository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class is responsible for processing the de-queued message and save into database
 */
@Slf4j
@Component
public class QueueMessageHandler {

    private final PersonRepository personRepository;

    public QueueMessageHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Save incoming Person object in database
     * @param message
     */
    public void process(String message){

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            log.debug("Attempting to save Person in DB: {}", message);

            // Parse incoming JSON value to Person object
            Person person = objectMapper.readValue(message, Person.class);

            // Save Person object in DB
            Person savedPerson = personRepository.save(person);

            log.debug("Person save in DB: {}", savedPerson);

        } catch (IOException exception) {
            log.error("Unable to parse incoming JSON to Person Object: {}", exception);
        }
    }
}
