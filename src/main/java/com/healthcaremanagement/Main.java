package com.healthcaremanagement;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.repository.AppointmentRepository;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.PatientRepository;
import com.healthcaremanagement.service.AppointmentService;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.PatientService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        PatientRepository patientRepository = new PatientRepository(sessionFactory);
        PatientService patientService = new PatientService(patientRepository);

        DoctorRepository doctorRepository = new DoctorRepository(sessionFactory);
        DoctorService doctorService = new DoctorService(doctorRepository);

        AppointmentRepository appointmentRepository = new AppointmentRepository(sessionFactory);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);


        try (Scanner scanner = new Scanner(System.in)) {
            String choice = "";
            while (!choice.equalsIgnoreCase("d")) {
                System.out.println("a. Manage Patients");
                System.out.println("b. Manage Doctors");
                System.out.println("c. Manage Appointments");
                System.out.println("d. End program");


                choice = scanner.nextLine();

                System.out.println(choice);

                switch (choice) {
                    case "a":
                        managePatients(scanner, patientService);
                        break;
                    case "b":
                        manageDoctors(scanner, doctorService);
                        break;
                    case "c":
                        manageAppointments(scanner, appointmentService);
                    default:
                        System.out.println("Invalid choice.");
                }

            }
        } finally {
            sessionFactory.close();
        }
    }



    private static void managePatients(Scanner scanner, PatientService patientService) {


        System.out.println("1. Create Patient");
        System.out.println("2. Read Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");

        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println(choice);
        switch (choice) {
            case 1:
                // Application calls the service layer, not the repository directly
                Patient newPatient = new Patient();
                System.out.print("Enter first name: ");
                newPatient.setFirstName(scanner.nextLine());
                System.out.print("Enter last name: ");
                newPatient.setLastName(scanner.nextLine());
                System.out.print("Enter date of birth (YYYY-MM-DD): ");
                newPatient.setDateOfBirth(scanner.nextLine());
                System.out.print("Enter email: ");
                newPatient.setEmail(scanner.nextLine());
                System.out.print("Enter phone number: ");
                newPatient.setPhoneNumber(scanner.nextLine());
                patientService.createPatient(newPatient);  // Use service here
                System.out.println("Patient created successfully.");
                break;
            case 2:
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Patient ID: ");
                int patientId = scanner.nextInt();
                scanner.nextLine(); //consume new line
                Patient patient = patientService.getPatientById(patientId);  // Use service here
                if (patient != null) {
                    System.out.println("Patient ID: " + patient.getPatientId());
                    System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                    System.out.println("Date of Birth: " + patient.getDateOfBirth());
                    System.out.println("Email: " + patient.getEmail());
                    System.out.println("Phone: " + patient.getPhoneNumber());
                } else {
                    System.out.println("Patient not found.");
                }
                break;
            case 3:
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Patient ID: ");
                patientId = scanner.nextInt();
                scanner.nextLine();  // consume newline
                patient = patientService.getPatientById(patientId);  // Use service here
                if (patient != null) {
                    System.out.print("Enter new first name: ");
                    patient.setFirstName(scanner.nextLine());
                    System.out.print("Enter new last name: ");
                    patient.setLastName(scanner.nextLine());
                    System.out.print("Enter new date of birth (YYYY-MM-DD): ");
                    patient.setDateOfBirth(scanner.nextLine());
                    System.out.print("Enter new email: ");
                    patient.setEmail(scanner.nextLine());
                    System.out.print("Enter new phone number: ");
                    patient.setPhoneNumber(scanner.nextLine());
                    patientService.updatePatient(patient);  // Use service here
                    System.out.println("Patient updated successfully.");
                } else {
                    System.out.println("Patient not found.");
                }
                break;
            case 4:
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Patient ID: ");
                patientId = scanner.nextInt();
                scanner.nextLine();
                patientService.deletePatient(patientId);  // Use service here
                System.out.println("Patient deleted successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }


    private static void manageDoctors(Scanner scanner, DoctorService doctorService) {
        System.out.println("1. Create Doctor");
        System.out.println("2. Read Doctor");
        System.out.println("3. Update Doctor");
        System.out.println("4. Delete Doctor");

        int choice = scanner.nextInt();
        scanner.nextLine();


            switch (choice) {
                case 1:
                    // Application calls the service layer, not the repository directly
                    Doctor newDoctor = new Doctor();
                    System.out.print("Enter first name: ");
                    newDoctor.setFirstName(scanner.nextLine());
                    System.out.print("Enter last name: ");
                    newDoctor.setLastName(scanner.nextLine());

                    System.out.print("Enter email: ");
                    newDoctor.setEmail(scanner.nextLine());
                    System.out.print("Enter specialty: ");
                    newDoctor.setSpecialty(scanner.nextLine());
                    doctorService.createDoctor(newDoctor);  // Use service here
                    System.out.println("Doctor created successfully.");
                    break;
                case 2:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine(); //consume new line
                    Doctor doctor = doctorService.getDoctorById(doctorId);

                    if (doctor != null) {
                        System.out.println("Doctor ID: " + doctor.getId());
                        System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
                        System.out.println("Email: " + doctor.getEmail());
                        System.out.println("Specialty: " + doctor.getSpecialty());
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
                case 3:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // consume newline
                    doctor = doctorService.getDoctorById(id);  // Use service here
                    if (doctor != null) {
                        System.out.print("Enter new first name: ");
                        doctor.setFirstName(scanner.nextLine());
                        System.out.print("Enter new last name: ");
                        doctor.setLastName(scanner.nextLine());

                        System.out.print("Enter new email: ");
                        doctor.setEmail(scanner.nextLine());
                        System.out.print("Enter new specialty: ");
                        doctor.setSpecialty(scanner.nextLine());

                        doctorService.updateDoctor(doctor);  // Use service here
                        System.out.println("Doctor updated successfully.");
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
                case 4:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    id = scanner.nextInt();
                    doctorService.deleteDoctor(id);  // Use service here
                    System.out.println("Doctor deleted successfully.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

    }


    private static void manageAppointments(Scanner scanner, AppointmentService appointmentService) {
        System.out.println("1. Create Appointment");
        System.out.println("2. Read Appointment");
        System.out.println("3. Update Appointment");
        System.out.println("4. Delete Appointment");

        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {
            case 1:
                // Application calls the service layer, not the repository directly
                Appointment newAppointment = new Appointment();
                System.out.print("Enter patient ID: ");
                newAppointment.setPatientId(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter doctor ID: ");
                newAppointment.setDoctorId(scanner.nextInt());
                scanner.nextLine();

                System.out.print("Enter date in YYYY-MM-DD: ");
                newAppointment.setAppointmentDate(scanner.nextLine());
                System.out.print("Enter notes: ");
                newAppointment.setNotes(scanner.nextLine());
                appointmentService.createAppointment(newAppointment);  // Use service here
                System.out.println("Appointment created successfully.");
                break;
            case 2:

                System.out.print("Enter Appointment ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); //consume new line
                Appointment appointment = appointmentService.getAppointmentById(id);

                if (appointment != null) {
                    System.out.println("Appointment ID: " + appointment.getAppointmentId());
                    System.out.println("Doctor ID: " + appointment.getDoctorId());
                    System.out.println("Patient ID: " + appointment.getPatientId());

                    System.out.println("Appointment Date: " + appointment.getAppointmentDate());
                    System.out.println("Notes: " + appointment.getNotes());
                } else {
                    System.out.println("Appointment not found.");
                }
                break;
            case 3:
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Appointment ID: ");
                 id = scanner.nextInt();
                scanner.nextLine();  // consume newline
                appointment = appointmentService.getAppointmentById(id);  // Use service here
                if (appointment != null) {
                    System.out.print("Enter patient ID: ");
                    appointment.setPatientId(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Enter doctor ID: ");
                    appointment.setDoctorId(scanner.nextInt());
                    scanner.nextLine();

                    System.out.print("Enter date in YYYY-MM-DD: ");
                    appointment.setAppointmentDate(scanner.nextLine());
                    System.out.print("Enter notes: ");
                    appointment.setNotes(scanner.nextLine());

                    appointmentService.updateAppointment(appointment);  // Use service here
                    System.out.println("Appointment updated successfully.");
                } else {
                    System.out.println("Appointment not found.");
                }
                break;
            case 4:
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Appointment ID: ");
                id = scanner.nextInt();
                scanner.nextLine(); //consume new line
                appointmentService.deleteAppointment(id);  // Use service here
                System.out.println("Appointment deleted successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
 }


