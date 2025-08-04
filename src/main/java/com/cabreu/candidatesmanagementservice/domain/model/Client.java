package com.cabreu.candidatesmanagementservice.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate birthDate;

    public Client(Long id, String firstName, String lastName, Integer age, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getEstimatedDeathDate() {
        return this.birthDate.plusYears(80);
    }

    public Long getDaysToEstimatedDeath() {
        return ChronoUnit.DAYS.between(LocalDate.now(), getEstimatedDeathDate());
    }


}
