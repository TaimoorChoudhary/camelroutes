package com.assignment.camelroutes.router;

import com.assignment.camelroutes.config.CamelRoutesProperties;
import com.assignment.camelroutes.processor.QueueProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.assignment.camelroutes.config.constants.RouteConstants.QUEUE_PROCESSOR_ENDPOINT;
import static com.assignment.camelroutes.config.constants.RouteConstants.QUEUE_PROCESSOR_ID;

@Component
public class QueueRouter extends RouteBuilder {

    private final CamelRoutesProperties camelRoutesProperties;
    private final JmsTemplate jmsTemplate;

    public QueueRouter(CamelRoutesProperties camelRoutesProperties, JmsTemplate jmsTemplate) {
        this.camelRoutesProperties = camelRoutesProperties;
        this.jmsTemplate = jmsTemplate;
    }
    @Override
    public void configure() throws Exception {
        from(QUEUE_PROCESSOR_ENDPOINT)
                .id(QUEUE_PROCESSOR_ID)
                .log("Routing personDto to Queue Processor")
                .process(new QueueProcessor(camelRoutesProperties.getActivemq().getPersonQueue(), jmsTemplate));
    }
}
