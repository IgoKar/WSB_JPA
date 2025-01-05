package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AddressDaoImpl extends AbstractDao<AddressEntity, Long> implements AddressDao
{
    public Optional<AddressEntity> findByCityAndAddressLine1AndPostalCode(String city, String addressLine1, String postalCode) {
        TypedQuery<AddressEntity> query = entityManager.createQuery(
                "SELECT a FROM AddressEntity a WHERE a.city = :city AND a.addressLine1 = :addressLine1 AND a.postalCode = :postalCode",
                AddressEntity.class);
        query.setParameter("city", city);
        query.setParameter("addressLine1", addressLine1);
        query.setParameter("postalCode", postalCode);
        AddressEntity result = query.getResultStream().findFirst().orElse(null);
        return Optional.ofNullable(result);
    }
}
