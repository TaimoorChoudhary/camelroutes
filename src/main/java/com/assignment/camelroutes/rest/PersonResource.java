package com.assignment.camelroutes.rest;

import com.assignment.camelroutes.api.dto.PersonDto;
import com.assignment.camelroutes.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.assignment.camelroutes.config.constants.RestEndpoints.API_PERSON;
import static com.assignment.camelroutes.config.constants.RestEndpoints.API_PERSON_ID;

@Slf4j
@RestController
@RequestMapping
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(API_PERSON)
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) throws URISyntaxException {

        PersonDto result = personService.save(personDto);

        return ResponseEntity.created(new URI(API_PERSON + "/id")).body(result);
    }

    @PutMapping(API_PERSON)
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) throws URISyntaxException {

        PersonDto result = personService.save(personDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(API_PERSON)
    public ResponseEntity<List<PersonDto>> getAllPersons(){

        List<PersonDto> personDtoList = personService.findAll();

        return new ResponseEntity<>(personDtoList, HttpStatus.OK);

    }

    @GetMapping(API_PERSON_ID)
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id){

        Optional<PersonDto> personDto = personService.findOne(id);

        if(personDto.isPresent()) {
            return new ResponseEntity<>(personDto.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(API_PERSON_ID)
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){

        personService.delete(id);

        return ResponseEntity.ok().build();
    }
}
