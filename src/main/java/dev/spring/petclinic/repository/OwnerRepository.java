package dev.spring.petclinic.repository;

import dev.spring.petclinic.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    /*
    JpaRepository - crud+ 페이징 처리 기능을 제공하는 인터페이스
    CrudRepository - 기본적인 crud 기능을 제공하는 인터페이스
     */

    List<Owner> findAllByLastNameLike (String lastName);
}
