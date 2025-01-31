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
    int officeId;

    String location;
    String phone;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "DoctorID")
    Doctor doctor;
}
