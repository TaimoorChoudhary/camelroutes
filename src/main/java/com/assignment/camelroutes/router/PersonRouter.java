package com.assignment.camelroutes.router;

import com.assignment.camelroutes.processor.PersonProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.assignment.camelroutes.config.constants.RouteConstants.PERSON_PROCESSOR_ENDPOINT;
import static com.assignment.camelroutes.config.constants.RouteConstants.PERSON_PROCESSOR_ID;
import static com.assignment.camelroutes.config.constants.RouteConstants.QUEUE_PROCESSOR_ENDPOINT;

/**
 * Class is responsible for routing incoming person object to intended processors
 */
@Component
public class PersonRouter extends RouteBuilder {

	/**
	 * Sends incoming Person for update and then passes it onto Queue Router
	 * @throws Exception
	 */
	@Override
	public void configure() throws Exception {
		from(PERSON_PROCESSOR_ENDPOINT)
			.id(PERSON_PROCESSOR_ID)
			.log("Routing personDto to Person Processor")
			.process(new PersonProcessor())
			.to(QUEUE_PROCESSOR_ENDPOINT);
	}

}
