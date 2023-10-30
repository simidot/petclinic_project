package dev.spring.petclinic.service;

import dev.spring.petclinic.model.PetType;

import java.util.List;

public interface PetTypeService {
    List<PetType> selectAll();
}
