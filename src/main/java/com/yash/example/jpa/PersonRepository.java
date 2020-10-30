package com.yash.example.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.example.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}