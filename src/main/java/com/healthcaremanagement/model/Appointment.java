package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    private int  patientId;
    private int doctorId;

    //YYYY-MM-DD
    @Column(name = "AppointmentDate")
    private String appointmentDate;
    private String notes;
}
