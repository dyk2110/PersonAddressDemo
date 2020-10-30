package com.yash.example.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.example.jpa.AddressRepository;
import com.yash.example.jpa.PersonRepository;
import com.yash.example.model.Address;
import com.yash.example.exception.NotFoundException;

@RestController
@RequestMapping("/api")
public class AddressController {
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PersonRepository personRepository;
    
	//Get List of address of person by Id
	@GetMapping("/person/{personId}/address")
	public List<Address> getContactByPersonId(@PathVariable Long personId) {

		if (!personRepository.existsById(personId)) {
			throw new NotFoundException("Person not found with given Id!");
		}
		return addressRepository.findByPersonId(personId);
	}

	//Add addresses to a person with their Id
	
	@PostMapping("/person/{personId}/address")
	public Address addAddress(@PathVariable Long personId, @Valid @RequestBody Address address) {
		return personRepository.findById(personId).map(person -> {
			address.setPerson(person);
			return addressRepository.save(address);
		}).orElseThrow(() -> new NotFoundException("Person not found with given Id!"));
	}

	//Update address of a Person with their Id
	
	@PutMapping("/person/{personId}/address/{addressId}")
	public Address updateAddress(@PathVariable Long personId, @PathVariable Long addressId,
			@Valid @RequestBody Address addressUpdated) {

		if (!personRepository.existsById(personId)) {
			throw new NotFoundException("Person not found with given Id!");
		}

		return addressRepository.findById(addressId).map(address -> {
			address.setStreet(addressUpdated.getStreet());
			address.setCity(addressUpdated.getCity());
			address.setState(addressUpdated.getState());
			address.setPostalCode(addressUpdated.getPostalCode());
			return addressRepository.save(address);
		}).orElseThrow(() -> new NotFoundException("Address not found!"));
	}

	//Delete address for a Person
	@DeleteMapping("/person/{personId}/address/{addressId}")
	public String deleteAddress(@PathVariable Long personId, @PathVariable Long addressId) {

		if (!personRepository.existsById(personId)) {
			throw new NotFoundException("Person not found while deleting the address!");
		}

		return addressRepository.findById(addressId).map(address -> {
			addressRepository.delete(address);
			return "Deleted Address Successfully!";
		}).orElseThrow(() -> new NotFoundException("Person not found!"));
	}
}
