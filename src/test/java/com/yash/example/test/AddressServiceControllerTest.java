package com.yash.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yash.example.model.Address;
import com.yash.example.model.Person;

public class AddressServiceControllerTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	// Test case to add address to a person
	@Test
	public void createAddress() throws Exception {
		String uri = "/api/person/1/address";
		Address address = new Address();
		Person person = new Person("yash", "hyd");
		address.setCity("hyd");
		address.setId(1L);
		address.setPostalCode("500010");
		address.setPerson(person);

		String inputJson = mapToJson(person);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Address is created successfully");
	}

	// Udpate address of a person
	@Test
	public void updateAddress() throws Exception {
		String uri = "/api/person/1/address/1";
		Address address = new Address();
		Person person = new Person("yash", "hyd");
		address.setCity("hyd");
		address.setId(1L);
		address.setPostalCode("500010");
		address.setPerson(person);

		String inputJson = mapToJson(address);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Address is updated successfully");
	}

	// Delete address of a person
	@Test
	public void deleteAddress() throws Exception {
		String uri = "/api/person/1/address/1";
		Address address = new Address();

		String inputJson = mapToJson(address);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Address is updated successfully");
	}

}
