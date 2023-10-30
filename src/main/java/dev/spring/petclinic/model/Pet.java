package dev.spring.petclinic.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity{

    @Column(name="name")
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name="type_id") // 이 조인된 컬럼의 이름을 설정해주는 것이기 때문에 안써도 가능
    private PetType petType; //여러 펫은 하나의 타입
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner; // 여러 펫은 하나의 주인

    @Builder
    public Pet(Long id, String name, LocalDate birthDate, PetType petType, Owner owner) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return name;
    }
}
