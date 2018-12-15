package com.assignment.camelroutes.api.mapper;

import com.assignment.camelroutes.domain.Person;
import com.assignment.camelroutes.api.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    /**
     * Map Person DTO object to Entity object
     * @param personDto
     * @return
     */
    Person toEntity(PersonDto personDto);

    /**
     * Map Person Entity to DTO object
     * @param person
     * @return
     */
    PersonDto toDto(Person person);
}
