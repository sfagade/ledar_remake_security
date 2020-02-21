/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsp.projects.ledar.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tsp.projects.ledar.security.model.Person;


/**
 *
 * @author sfagade
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
