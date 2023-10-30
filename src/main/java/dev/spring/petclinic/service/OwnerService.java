package dev.spring.petclinic.service;

import dev.spring.petclinic.model.Owner;

import java.util.List;

public interface OwnerService {
    Owner save (Owner owner, Long ownerId);

    Owner findById(Long id);

    List<Owner> findAll();

    List<Owner> findAllByLastNameLike(String lastName);

//    Owner update(Owner owner, Long id);
}

