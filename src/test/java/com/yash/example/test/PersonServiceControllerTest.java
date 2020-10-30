package com.yash.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yash.example.model.Person;

public class PersonServiceControllerTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	// Test case for get list of persons
	@Test
	public void getPersonList() throws Exception {

		String uri = "/api/personList";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Person[] personlist = super.mapFromJson(content, Person[].class);
		assertTrue(personlist.length > 0);
	}

	// Test case for create a Person
	@Test
	public void createPerson() throws Exception {
		String uri = "/api/person";
		Person person = new Person();
		person.setFirstName("abc");
		person.setLastName("pqr");

		String inputJson = mapToJson(person);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Person is created successfully");
	}

	// Test case for updating the Person
	@Test
	public void updatePerson() throws Exception {
		String uri = "/api/person/1";
		Person person = new Person();
		person.setFirstName("Lemon");
		person.setLastName("Prasad");

		String inputJson = mapToJson(person);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Person is updated successsfully");
	}

	// Test case to delete a person with Id
	@Test
	public void deletePerson() throws Exception {
		String uri = "/person/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Person is deleted successsfully");
	}

}
