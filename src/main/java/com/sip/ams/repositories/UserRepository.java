package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sip.ams.entities.Users;
@Repository("userRepository")
public interface UserRepository extends JpaRepository<Users, Integer> {
	 Users findByEmail(String email);
}
