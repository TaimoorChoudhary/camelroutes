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

@Slf4j
public class QueueProcessor implements Processor {

    private final String queue;

    private final JmsTemplate jmsTemplate;

    public QueueProcessor(String queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        log.debug("Value received inside Camel Queue Processor");

        //Get incoming Person Object
        Person person = exchange.getIn().getBody(Person.class);
        sendMessage(person);

        exchange.getOut().setBody(person);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
    }

    public void sendMessage(Person person) throws JMSException, JsonProcessingException {

        // Convert the updated value to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String personAsJson = objectMapper.writeValueAsString(person);

        jmsTemplate.convertAndSend(queue, personAsJson);
    }
}
