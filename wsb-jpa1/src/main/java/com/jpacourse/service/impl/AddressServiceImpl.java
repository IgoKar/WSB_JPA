package com.jpacourse.service.impl;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService
{
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressTO findById(Long id) {
        final AddressEntity entity = addressDao.findOne(id);
        return addressMapper.toTO(entity);
    }
}
