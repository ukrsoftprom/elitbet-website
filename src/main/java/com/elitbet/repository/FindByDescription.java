package com.elitbet.repository;

public interface FindByDescription<I> {
    I findByDescriptionEquals(String description);
}
