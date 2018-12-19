package com.assignment.camelroutes.processor;

import com.assignment.camelroutes.domain.Person;
import com.assignment.camelroutes.respository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class contains Processor functions to be used inside Apache Camel routes
 */
@Slf4j
public class CamelRouteProcessor {

    private final PersonRepository personRepository;
    private final DateTimeFormatter dateTimeFormatter;

    public CamelRouteProcessor(PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
    }

    /**
     * Function is responsible for updating Person's object
     * @param exchange
     * @throws Exception
     */
    public void personProcessor(Exchange exchange) throws Exception {

        log.debug("Value received inside Camel Person Processor");

        String currentTime = LocalDateTime.now().format(dateTimeFormatter);

        //Get incoming Person and Add/Update comment
        Person person = exchange.getIn().getBody(Person.class);
        person.setComments("Updated inside Camel Processor - " + currentTime);

        exchange.getOut().setBody(person);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
    }

    /**
     * Manages handling of the Person's object received via messaging queue
     * @param exchange
     * @throws Exception
     */
    public void queueProcessor(Exchange exchange) throws Exception {
        log.debug("Value received inside Camel Queue Processor");

        //Get incoming Person Object
        Person person = exchange.getIn().getBody(Person.class);

        log.debug("Attempting to save Person in DB: {}", person);

        // Save Person object in DB
        Person savedPerson = personRepository.save(person);

        log.debug("Person save in DB: {}", savedPerson);
    }
}
