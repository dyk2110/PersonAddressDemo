package com.yash.example.rest;

import java.util.List;
import java.util.Optional;

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

import com.yash.example.exception.NotFoundException;
import com.yash.example.jpa.PersonRepository;
import com.yash.example.model.Person;

@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	// Get List of all Persons
	@GetMapping("/personList")
	public List<Person> getAllPerson() {
		System.out.println("No of Persons:"+personRepository.findAll().size());
		return personRepository.findAll();
	}

	//Get Person by their Id
	@GetMapping("/person/{id}")
	public Person getPersonByID(@PathVariable Long id) {
		Optional<Person> optPerson = personRepository.findById(id);
		if (optPerson.isPresent()) {
			return optPerson.get();
		} else {
			throw new NotFoundException("Person not found with id " + id);
		}
	}

	//To Add a person with details
	@PostMapping("/person")
	public Person createPerson(@Valid @RequestBody Person person) {
		return personRepository.save(person);
	}
    
    //To Update a person with Id as input	
	//If not found with with given Id Exception should be thrown
	@PutMapping("/person/{id}")
	public Person updatePerson(@PathVariable Long id, @Valid @RequestBody Person personUpdated) {
		return personRepository.findById(id).map(person -> {
			person.setFirstName(personUpdated.getFirstName());
			person.setLastName(personUpdated.getLastName());
			return personRepository.save(person);
		}).orElseThrow(() -> new NotFoundException("Person not found with id " + id));
	}
    
	//Delete Person with given Id
	@DeleteMapping("/person/{id}")
	public String deletePerson(@PathVariable Long id) {
		return personRepository.findById(id).map(person -> {
			personRepository.delete(person);
			return "Person Delete Successfully!";
		}).orElseThrow(() -> new NotFoundException("Person not found with id " + id));
	}
}