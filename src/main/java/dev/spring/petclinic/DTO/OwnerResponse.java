package dev.spring.petclinic.DTO;

// 네이밍은 취향 (OwnerDTO, OwnerDto)
// Owner에 대한 응답용 객체(Response)로 사용될 클래스

import dev.spring.petclinic.model.Owner;
import dev.spring.petclinic.model.Pet;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class OwnerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    // todo : pet에 대한 데이터를 담을 필드도 필요
    private List<PetResponse> pets;

    // 생성자 생성
    private OwnerResponse(Long id, String firstName, String lastName, String address, String city, String telephone, List<PetResponse> pets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    // 이펙티브 자바 item - 정적(static) 팩토리 메서드
    public static OwnerResponse from (Owner owner) {
        final Long id = owner.getId();
        final String firstName = owner.getFirstName();
        final String lastName = owner.getLastName();
        final String address = owner.getAddress();
        final String city = owner.getCity();
        final String telephone = owner.getTelephone();
        // todo : pet 데이터 맵핑
        final List<PetResponse> petResponses = owner.getPets().stream().map(PetResponse::from)
                .collect(Collectors.toList());

        return new OwnerResponse(id, firstName, lastName, address, city, telephone, petResponses);
    }
}

