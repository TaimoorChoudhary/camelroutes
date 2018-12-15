package com.assignment.camelroutes.service;

import com.assignment.camelroutes.api.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    /**
     * Save a Person
     * @param personDto
     */
    PersonDto save(PersonDto personDto);

    /**
     * Find Person for the given ID
     * @param id
     * @return
     */
    Optional<PersonDto> findOne(Long id);

    /**
     * Find all existing Persons
     * @return
     */
    List<PersonDto> findAll();

    /**
     * Delete Person for the given ID
     * @param id
     */
    void delete(Long id);
}
