package dev.spring.petclinic.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OwnerDTO {
    private Long id;
    private String address;
    private String city;
    private String telephone;
    private List<PetDTO> pets;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PetDTO {
        private Long id;
        private String name;
        private LocalDate birthDate;
        private PetTypeDTO petType;

        @Getter
        @AllArgsConstructor
        @Builder
        public static class PetTypeDTO {
            private Long id;
            private String name;
        }

    }
}
