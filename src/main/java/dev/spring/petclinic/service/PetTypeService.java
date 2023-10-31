package dev.spring.petclinic.service;

import dev.spring.petclinic.model.PetType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PetTypeService {
    List<PetType> selectAll();
}
