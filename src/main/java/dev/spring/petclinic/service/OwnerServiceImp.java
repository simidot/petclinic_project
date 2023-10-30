package dev.spring.petclinic.service;

import dev.spring.petclinic.model.Owner;
import dev.spring.petclinic.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerServiceImp implements OwnerService{
    private final OwnerRepository ownerRepository;


    @Override
    public Owner save(Owner owner, Long ownerId) {
        if (ownerId != null) {
            // ownerId가 존재하면 해당 ID에 해당하는 Owner를 가져와서 수정합니다.
            Owner existingOwner = this.findById(ownerId);

            if (existingOwner != null) {
                // 기존 Owner 엔티티의 필드를 수정합니다.
//                existingOwner.setFirstName(owner.getFirstName());
//                existingOwner.setLastName(owner.getLastName());
//                existingOwner.setAddress(owner.getAddress());
//                existingOwner.setCity(owner.getCity());
//                existingOwner.setTelephone(owner.getTelephone());
                owner.setId(existingOwner.getId());

                // 수정된 Owner를 저장합니다.
                return ownerRepository.save(owner);
            } else {
                // ID에 해당하는 Owner를 찾을 수 없는 경우 처리할 내용을 정의하세요.
                return null; // 또는 예외 처리 등을 수행할 수 있습니다.
            }
        } else {
            // ownerId가 없는 경우 새로운 Owner 엔티티를 추가합니다.
            return ownerRepository.save(owner);
        }
    }

    @Override
    public Owner findById(Long id) {
        // JPA - manager.find(Owner.class, 1);
        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()) { //optional 내부의 값이 유효하면
            return optionalOwner.get(); // optional 내부에서 해당 객체를 꺼냄
        } else {
            // 예외 던지기
            return null;
        }
        // 한줄로 표현
        // ownerRepository.findById(id).orElse(null); //orElseThrow(람다 형태) 활용 권장
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        return ownerRepository.findAllByLastNameLike(lastName);
    }

}
