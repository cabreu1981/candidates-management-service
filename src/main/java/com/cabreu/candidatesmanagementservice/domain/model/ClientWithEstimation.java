package com.cabreu.candidatesmanagementservice.domain.model;

import java.time.LocalDate;

public class ClientWithEstimation {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate birthDate;
    private LocalDate estimatedRetirementDate;

    public ClientWithEstimation(Long id, String firstName, String lastName, Integer age, LocalDate birthDate, LocalDate estimatedRetirementDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.birthDate = birthDate;
        this.estimatedRetirementDate = estimatedRetirementDate;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getEstimatedRetirementDate() {
        return estimatedRetirementDate;
    }

    public void setEstimatedRetirementDate(LocalDate estimatedRetirementDate) {
        this.estimatedRetirementDate = estimatedRetirementDate;
    }
}
