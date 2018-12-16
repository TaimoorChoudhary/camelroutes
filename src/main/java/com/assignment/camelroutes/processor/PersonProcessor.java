package com.assignment.camelroutes.processor;

import com.assignment.camelroutes.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;

/**
 * Class is responsible for updating Person's object via Camel Apache process
 */
@Slf4j
public class PersonProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		log.debug("Value received inside Camel Person Processor");

		//Get incoming Person and Add/Update comment
		Person person = exchange.getIn().getBody(Person.class);
		person.setComments("Updated inside Camel Processor");

		exchange.getOut().setBody(person);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
	}

}
