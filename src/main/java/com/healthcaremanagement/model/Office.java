package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Offices")
public class Office {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officeId;

    private String location;
    private String phone;

    @OneToOne
    @JoinColumn(name = "DoctorID")
    private Doctor doctor;
}
