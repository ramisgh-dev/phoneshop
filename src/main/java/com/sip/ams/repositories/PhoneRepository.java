package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sip.ams.entities.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

}
