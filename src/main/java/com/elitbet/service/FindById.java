package com.elitbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class FindById<E,Repository extends JpaRepository<E,Long>> {
    @Autowired
    Repository repository;

    public E findById(Long clientBankId){
        Optional<E> e = repository.findById(clientBankId);
        if(e.isPresent()){
            return e.get();
        }
        throw new IllegalArgumentException("Given event with id=" + clientBankId + " does not exists.");
    }
}
