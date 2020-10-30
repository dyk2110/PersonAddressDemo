package com.yash.example.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.example.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByPersonId(Long personId);	
}
