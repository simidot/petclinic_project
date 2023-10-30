package dev.spring.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@Getter
@Setter
public class Owner extends Person {

    private String address;
    private String city;
    private String telephone;
    @OneToMany(mappedBy = "owner")
    private List<Pet> pets = new ArrayList<>();

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, List<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                ", pets=" + pets +
                '}';
    }
}
