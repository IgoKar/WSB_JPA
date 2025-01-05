package com.jpacourse.repository;

import com.jpacourse.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByCityAndAddressLine1AndPostalCode(String city, String addressLine1, String postalCode);
}
