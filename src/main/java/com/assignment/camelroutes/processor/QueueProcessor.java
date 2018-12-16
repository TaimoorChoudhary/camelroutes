package com.assignment.camelroutes.processor;

import com.assignment.camelroutes.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

/**
 * Class is responsible for processing Person's object to be sent over on messaging queue
 * which is received via Apache Camel process
 */
@Slf4j
public class QueueProcessor implements Processor {

    private final String queue;

    private final JmsTemplate jmsTemplate;

    public QueueProcessor(String queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Manages further actions for Person object received via Camel Route
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        log.debug("Value received inside Camel Queue Processor");

        //Get incoming Person Object
        Person person = exchange.getIn().getBody(Person.class);

        exchange.getOut().setBody(person);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);

        try {
            sendMessage(person);
        }catch (Exception exception){

            log.error("Unable to send Person on intended queue: {}", exception);

            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Send incoming Person object over the messaging queue
     * @param person
     * @throws JMSException
     * @throws JsonProcessingException
     */
    public void sendMessage(Person person) throws JMSException, JsonProcessingException {

        // Convert the updated value to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String personAsJson = objectMapper.writeValueAsString(person);

        jmsTemplate.convertAndSend(queue, personAsJson);
    }
}
