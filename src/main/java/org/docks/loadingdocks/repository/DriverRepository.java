package org.docks.loadingdocks.repository;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<DriverEntity> findByEmail(String email);

}
