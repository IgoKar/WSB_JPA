package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;

import java.util.Optional;

public interface AddressDao extends Dao<AddressEntity, Long>
{
    Optional<AddressEntity> findByCityAndAddressLine1AndPostalCode(String city, String addressLine1, String postalCode);
}
