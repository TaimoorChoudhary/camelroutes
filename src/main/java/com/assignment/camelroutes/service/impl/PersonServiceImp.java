package com.assignment.camelroutes.service.impl;

import com.assignment.camelroutes.api.dto.PersonDto;
import com.assignment.camelroutes.api.mapper.PersonMapper;
import com.assignment.camelroutes.domain.Person;
import com.assignment.camelroutes.respository.PersonRepository;
import com.assignment.camelroutes.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.assignment.camelroutes.config.constants.RouteConstants.PERSON_PROCESSOR_ENDPOINT;

/**
 * Class is responsible for handling Person CRUD operations
 */
@Slf4j
@Service
public class PersonServiceImp implements PersonService {

    private final ProducerTemplate producer;
    private final CamelContext camelContext;

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    public PersonServiceImp(ProducerTemplate producer, CamelContext camelContext, PersonMapper personMapper, PersonRepository personRepository) {
        this.producer = producer;
        this.camelContext = camelContext;
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    /**
     * Saves the incoming Person object by sending it over Apache Camel routing
     * @param personDto
     * @return
     */
    @Override
    public PersonDto save(PersonDto personDto) {

        log.debug("Request to save Person : {}", personDto);

        Person person = personMapper.toEntity(personDto);

        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(person).build();
        final Exchange responseExchange = producer.send(PERSON_PROCESSOR_ENDPOINT, requestExchange);
        final Person responseBody = responseExchange.getOut().getBody(Person.class);

        PersonDto result = personMapper.toDto(responseBody);

        return result;
    }

    /**
     * Find Person for the given ID
     * @param id
     * @return
     */
    @Override
    public Optional<PersonDto> findOne(Long id) {

        log.debug("Request to find Person : {}", id);

        return personRepository.findById(id).map(personMapper::toDto);
    }

    /**
     * Find all available Persons
     * @return
     */
    @Override
    public List<PersonDto> findAll() {

        log.debug("Request to find all available Persons");

        return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Delete Person for the given ID
     * @param id
     */
    @Override
    public void delete(Long id) {

        log.debug("Request to delete Person : {}", id);

        personRepository.deleteById(id);
    }
}
