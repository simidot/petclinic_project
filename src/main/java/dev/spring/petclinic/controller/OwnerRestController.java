package dev.spring.petclinic.controller;

import dev.spring.petclinic.DTO.*;
import dev.spring.petclinic.model.Owner;
import dev.spring.petclinic.model.Pet;
import dev.spring.petclinic.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//owner에 대한 처리 컨트롤러이기 때문에 owners 경로의 하위 요청이 올 경우 모두 수행하도록
@RestController //@Controller + @ResponseBody (응답 객체)
@RequestMapping("/api/owners") // localhost:8080/owners로 오는 모든 요청을 받음
@AllArgsConstructor
public class OwnerRestController {
    private OwnerService ownerService;

    //GET:localhost:8080/owners - 전체 Owner 목록 조회 요청
    //GET: localhost:8080/owners - 전체 Owner 목록 조회 요청
//    @GetMapping
//    public List<OwnerDTO> listsOwners(){
//        //service.findAll() 호출 및 반환
//        List<Owner> owners = ownerService.findAll();
//
//        List<OwnerDTO> dtos = new ArrayList<>();
//
//        for (Owner owner: owners){
//
//            List<OwnerDTO.PetDTO> petDtos = new ArrayList<>();
//            for (Pet pet : owner.getPets()) {
//                petDtos.add(OwnerDTO.PetDTO.builder()
//                        .name(pet.getName())
//                        .birthDate(pet.getBirthDate())
//                        .petType(OwnerDTO.PetDTO.PetTypeDTO.builder()
//                                .name(pet.getPetType().getName())
//                                .build())
//                        .build());
//            }
//
//            dtos.add(OwnerDTO.builder()
//                    .id(owner.getId())
//                    .address(owner.getAddress())
//                    .city(owner.getCity())
//                    .telephone(owner.getTelephone())
//                    .pets(petDtos)
//                    .build());
//        }
//        return dtos;
//    }

    @GetMapping
    public List<OwnerResponse> listsOwners(){
        //service.findAll() 호출 및 반환
        List<Owner> owners = ownerService.findAll();

        List<OwnerResponse> ownerList = owners.stream().map(OwnerResponse::from)
                .collect(Collectors.toList());

        // 풀어쓰면
//        List<OwnerResponse> ownerList = new ArrayList<>();
//        for (Owner owner1 : owners) {
//            OwnerResponse from = OwnerResponse.from(owner1);
//            ownerList.add(from);
//        }
        return ownerList;
    }

    // Owner 등록 처리, 별도의 요청 객체 처리용 DTO를 활용해서 유효성 처리
    // 해당 메서드의 반환 타입은 ResponseEntity사용해보기
    // -> postman or TalendAPI Testing(크롬 익스텐션)
    @PostMapping
    public ResponseEntity<?> addOwner(@RequestBody @Validated OwnerRequest owner, BindingResult bindingResult) {
        System.out.println(owner);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Owner owner1 = ownerService.save(Owner.builder()
                        .firstName(owner.getFirstName())
                        .lastName(owner.getLastName())
                        .address(owner.getAddress())
                        .telephone(owner.getTelephone())
                        .city(owner.getCity())
                .build(), null);

        return ResponseEntity.ok(AddOwnerResponse.builder().id(owner1.getId())
                .firstName(owner1.getFirstName())
                .lastName(owner1.getLastName())
                .address(owner1.getAddress())
                .city(owner1.getCity())
                .telephone(owner1.getTelephone()).build());
    }



}
