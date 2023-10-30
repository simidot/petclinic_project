package dev.spring.petclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetTypeServiceImpl implements PetService{
    private final PetTypeService petTypeService;
}
