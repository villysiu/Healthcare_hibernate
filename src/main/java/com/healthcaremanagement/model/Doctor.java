package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Doctors")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;
    private String specialty;
    private String email;

}
