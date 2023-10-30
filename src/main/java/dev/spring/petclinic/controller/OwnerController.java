package dev.spring.petclinic.controller;

import java.util.List;
import java.util.SimpleTimeZone;

import dev.spring.petclinic.model.Pet;
import dev.spring.petclinic.model.PetType;
import dev.spring.petclinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.spring.petclinic.model.Owner;
import dev.spring.petclinic.service.OwnerService;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequestMapping("/owners") // baseUrl(기본 URL)
// bean으로 등록시키기 위한 annotation 추가
@Controller
@RequiredArgsConstructor
public class OwnerController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String VIEWS_PET_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    @GetMapping // localhost:8080/owners로 동작, 별도의 URL작성하지 않음
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        /*
         * 요구사항 - 특정 키워드로 검색 시 해당 키워드가 포함된 모든 Owner가 검색되서 목록에 출력되도록 구현
         * 필요한 API - Spring Data JPA에서 제공하는 Query Method 기능이 필요할 수 있음
         * findAll() 대신 다른 메서드를 사용해야함
         */

        // 별도의 파라미터 값(lastName에 값이 없는 경우) 없이 /owners로 요청이 올 경우, 전체 owners 데이터 반환
        if(owner.getLastName() == null) owner.setLastName("");

        // 조건문을 통해 구현
        List<Owner> results = ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");
        if (results.isEmpty()) { // 해당하는 Owner 데이터가 없을 경우
            result.rejectValue("lastName", "notFound", "not Found");

            return "owners/findOwners";
        } else if (results.size() == 1) { // 일치하는 Owners 데이터가 1명일 경우
            owner = results.get(0);
            return "redirect:/owners/"+owner.getId(); //  한 명의 Owner 디테일 페이지로 리다이렉트";
        } else { // 2명 이상의 Owner가 조회될 경우
            model.addAttribute("listOwners", results); // ownersList.html에 작성된 key값과 일치하도록(listOwners)
            return "owners/ownersList"; // 전체 Owner 목록 조회 요청으로 이동
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");

        mav.addObject(ownerService.findById(ownerId));

        return mav;
    }

    // @RequestMapping(value = "/find", method = RequestMethod.GET)
    @GetMapping("/find") // 위 코드의 축약 표현
    public String findOwners(Model model) {

        // In servlet, request.setAttribute("key", value); 코드와 동일
        model.addAttribute("owner", Owner.builder().build());

        return "owners/findOwners";
    }

    // Owner 등록 화면으로 렌더링해주는 메서드
    @GetMapping("/new") // GET: localhost:8080/owners/new 요청 시 렌더링할 페이지 지정
    public String initCreationForm(Model model) {

        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM; // templates/owners에서 찾아보기
    }

    // Owner 등록 처리 메서드
    @PostMapping(value = {"/new", "{ownerId}/edit"})
    public String processCreationForm(@Validated Owner owner, BindingResult result, @PathVariable Long ownerId) {
        System.out.println(owner);

        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            // OwnerService 생성 후 등록 처리 로직 구현, 완성
            Owner savedOwner = ownerService.save(owner, ownerId);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateForm(@PathVariable Long ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        System.out.println(owner.toString());
        model.addAttribute("owner", owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping("/{ownerId}/pets/new")
    public String initAddPetForm(Model model, @PathVariable Long ownerId){
        model.addAttribute("owner", Owner.builder().build());
        model.addAttribute("pet", Pet.builder().build());
        petTypeService.selectAll();
//        model.addAttribute("types", pet);

        return VIEWS_PET_CREATE_OR_UPDATE_FORM;
    }



}
