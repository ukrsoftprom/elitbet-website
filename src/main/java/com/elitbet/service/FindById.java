package com.elitbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class FindById<E,Repository extends JpaRepository<E,Long>> {
    @Autowired
    Repository repository;

    public E findById(Long id){
        Optional<E> e = repository.findById(id);
        if(e.isPresent()){
            return e.get();
        }
        throw new IllegalArgumentException("Given event with id=" + id + " does not exists.");
    }
}
