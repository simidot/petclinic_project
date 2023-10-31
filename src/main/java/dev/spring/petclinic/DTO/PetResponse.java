package dev.spring.petclinic.DTO;

import dev.spring.petclinic.model.Pet;
import dev.spring.petclinic.model.PetType;
import lombok.Getter;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

@Getter
@ToString
public class PetResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String petType;

    public PetResponse(Long id, String name, LocalDate birthDate, String petType) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
    }

    public static PetResponse from(Pet pet) {
        final Long id = pet.getId();
        final String name = pet.getName();
        final LocalDate birthDate = pet.getBirthDate();
        final String petType = pet.getPetType().getName();

        return new PetResponse(id, name, birthDate, petType);
    }

}
