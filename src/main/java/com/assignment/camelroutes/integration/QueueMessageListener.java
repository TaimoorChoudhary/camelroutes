package com.assignment.camelroutes.integration;

import com.assignment.camelroutes.config.CamelRoutesProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;

@Service
public class QueueMessageListener {

    private final CamelRoutesProperties camelRoutesProperties;
    private final ConnectionFactory jmsConnectionFactory;

    public QueueMessageListener(CamelRoutesProperties camelRoutesProperties, ConnectionFactory jmsConnectionFactory) {
        this.camelRoutesProperties = camelRoutesProperties;
        this.jmsConnectionFactory = jmsConnectionFactory;
    }


    @Bean
    public IntegrationFlow inboundFlow() {
        return IntegrationFlows.from(Jms.inboundGateway(jmsConnectionFactory)
                .destination(camelRoutesProperties.getActivemq().getPersonQueue()))
                .handle("queueMessageHandler", "process")
                .get();
    }
}
