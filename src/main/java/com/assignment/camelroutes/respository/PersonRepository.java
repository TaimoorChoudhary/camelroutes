package com.assignment.camelroutes.respository;

import com.assignment.camelroutes.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
