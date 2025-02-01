package com.healthcaremanagement;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.repository.AppointmentRepository;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.OfficeRepositoryImpl;
import com.healthcaremanagement.repository.PatientRepository;
import com.healthcaremanagement.service.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        PatientRepository patientRepository = new PatientRepository(sessionFactory);
        PatientService patientService = new PatientService(patientRepository);

        DoctorRepository doctorRepository = new DoctorRepository(sessionFactory);
        DoctorService doctorService = new DoctorService(doctorRepository);

        AppointmentRepository appointmentRepository = new AppointmentRepository(sessionFactory);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);

        OfficeRepositoryImpl officeRepository = new OfficeRepositoryImpl(sessionFactory);
        OfficeService officeService = new OfficeService(officeRepository);


        try( Scanner scanner = new Scanner(System.in)){
            String input = "1";
            while(!input.equals("5")){
                input = printMainMenu(scanner);
                switch (input) {
                    case "1":
                        managePatients(scanner, patientService);
                        break;
                    case "2":
                        manageDoctors(scanner, doctorService);
                        break;
                    case "3":
                        manageAppointments(scanner, appointmentService, patientService, doctorService);
                        break;
                    case "4":
                        manageOffices(scanner, officeService, doctorService);
                        break;
                    case "5":
                        System.out.println("Bye");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } finally {
            sessionFactory.close();
        }
    }

    private static void managePatients(Scanner scanner, PatientService patientService) {


        switch (getPatientChoice(scanner)) {
            case "1":
                // Application calls the service layer, not the repository directly
                Patient newPatient = new Patient();
                System.out.print("Enter first name: ");
                newPatient.setFirstName(scanner.nextLine());
                System.out.print("Enter last name: ");
                newPatient.setLastName(scanner.nextLine());

//                System.out.print("Enter date of birth (YYYY-MM-DD): ");
                newPatient.setDateOfBirth(inputDate(scanner, "Enter date of birth (YYYY-MM-DD): "));

                System.out.print("Enter email: ");
                newPatient.setEmail(scanner.nextLine());

                newPatient.setPhoneNumber(inputPhoneNumber(scanner));

                patientService.createPatient(newPatient);  // Use service here
                System.out.println("Patient created successfully.");
                break;
            case "2":
                // Application calls the service layer, not the repository directly

                Patient patient = inputPatient(scanner, patientService);

                System.out.println("Patient ID: " + patient.getPatientId());
                System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("Date of Birth: " + patient.getDateOfBirth());
                System.out.println("Email: " + patient.getEmail());
                System.out.println("Phone: " + patient.getPhoneNumber());

                break;
            case "3":
                Patient updatePatient = inputPatient(scanner, patientService);

                System.out.print("Enter new first name: ");
                updatePatient.setFirstName(scanner.nextLine());
                System.out.print("Enter new last name: ");
                updatePatient.setLastName(scanner.nextLine());

                updatePatient.setDateOfBirth(inputDate(scanner, "Enter new date of birth (YYYY-MM-DD): "));

                System.out.print("Enter new email: ");
                updatePatient.setEmail(scanner.nextLine());
                System.out.print("Enter new phone number: ");
                updatePatient.setPhoneNumber(scanner.nextLine());

                patientService.updatePatient(updatePatient);  // Use service here
                System.out.println("Patient updated successfully.");

                break;
            case "4":
                // Application calls the service layer, not the repository directly
               Patient deletePatient = inputPatient(scanner, patientService);
                patientService.deletePatient(deletePatient.getPatientId());  // Use service here
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

                    Doctor doctor = inputDoctor(scanner, doctorService);

                    System.out.println("Doctor ID: " + doctor.getDoctorId());
                    System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
                    System.out.println("Email: " + doctor.getEmail());
                    System.out.println("Specialty: " + doctor.getSpecialty());

                    break;
                case 3:
                    Doctor updateDoctor = inputDoctor(scanner, doctorService);

                    System.out.print("Enter new first name: ");
                    updateDoctor.setFirstName(scanner.nextLine());
                    System.out.print("Enter new last name: ");
                    updateDoctor.setLastName(scanner.nextLine());

                    System.out.print("Enter new email: ");
                    updateDoctor.setEmail(scanner.nextLine());
                    System.out.print("Enter new specialty: ");
                    updateDoctor.setSpecialty(scanner.nextLine());

                    doctorService.updateDoctor(updateDoctor);  // Use service here
                    System.out.println("Doctor updated successfully.");

                    break;
                case 4:
                    Doctor deleteDoctor = inputDoctor(scanner, doctorService);
                    doctorService.deleteDoctor(deleteDoctor.getDoctorId());  // Use service here
                    System.out.println("Doctor deleted successfully.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

    }



    private static void manageAppointments(Scanner scanner, AppointmentService appointmentService, PatientService patientService, DoctorService doctorService) {

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

                Patient p1= inputPatient(scanner, patientService);
                Doctor d1= inputDoctor(scanner, doctorService);
                newAppointment.setPatient(p1);
                newAppointment.setDoctor(d1);
                newAppointment.setAppointmentDate(inputDate(scanner, "Enter date in YYYY-MM-DD: "));

                System.out.print("Enter notes: ");
                newAppointment.setNotes(scanner.nextLine());

                appointmentService.createAppointment(newAppointment);

                doctorService.addPatientToDoctor(d1.getDoctorId(), p1);
                patientService.addDoctorToPatient(p1.getPatientId(), d1);

                System.out.println("Appointment created successfully.");
                break;

            case 2:
                Appointment ap = inputAppointment(scanner, appointmentService);

                System.out.println("Appointment ID: " + ap.getAppointmentId());
                System.out.println("Doctor ID: " + ap.getDoctor().getDoctorId());
                System.out.println("Patient ID: " + ap.getPatient().getPatientId());

                System.out.println("Appointment Date: " + ap.getAppointmentDate());
                System.out.println("Notes: " + ap.getNotes());

                break;

            case 3:

                Appointment a1 = inputAppointment(scanner, appointmentService);

                Doctor originalDoctor = a1.getDoctor();
                Patient originalPatient = a1.getPatient();

                a1.setPatient(inputPatient(scanner, patientService));
                a1.setDoctor(inputDoctor(scanner, doctorService));
                a1.setAppointmentDate(inputDate(scanner, "Enter date in YYYY-MM-DD: "));

                System.out.print("Enter notes: ");
                a1.setNotes(scanner.nextLine());

                appointmentService.updateAppointment(a1);

                if (!appointmentService.hasOtherAppointmentsBetween(originalDoctor.getDoctorId(), originalPatient.getPatientId())){
                    doctorService.removePatientFromDoctor(originalDoctor.getDoctorId(), originalPatient);
                    patientService.removeDoctorFromPatient(originalPatient.getPatientId(), originalDoctor);
                }

                System.out.println("Appointment updated successfully.");
                break;
            case 4:

                Appointment a2 = inputAppointment(scanner, appointmentService);
                appointmentService.deleteAppointment(a2.getAppointmentId());
                System.out.println("Appointment deleted successfully.");

                if (!appointmentService.hasOtherAppointmentsBetween(
                        a2.getDoctor().getDoctorId(), a2.getPatient().getPatientId())) {
                    doctorService.removePatientFromDoctor(a2.getDoctor().getDoctorId(), a2.getPatient());
                    patientService.removeDoctorFromPatient(a2.getPatient().getPatientId(), a2.getDoctor());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manageOffices(Scanner scanner, OfficeService officeService, DoctorService doctorService) {
        switch(printOfficeMenu(scanner)){
            case "1":
                Office newOffice = new Office();
                System.out.print("Enter location: ");
                newOffice.setLocation(scanner.nextLine());

                System.out.print("Enter phone: ");
                newOffice.setPhone(scanner.nextLine());

                newOffice.setDoctor(inputDoctor(scanner, doctorService));

                officeService.createOffice(newOffice);
                System.out.println("Office created successfully.");
                break;
            case "2":
                printOffice(inputOffice(scanner, officeService));
                break;
            case "3":
                Office o1 = inputOffice(scanner, officeService);

                System.out.print("Enter location: ");
                o1.setLocation(scanner.nextLine());
                System.out.print("Enter phone number (###-###-####):  ");
                o1.setPhone(scanner.nextLine());

                o1.setDoctor(inputDoctor(scanner, doctorService));

                officeService.createOffice(o1);
                System.out.println("Office updated successfully.");

                break;
            case "4":
                Office o2 = inputOffice(scanner, officeService);
                officeService.deleteOffice(o2.getOfficeId());
                System.out.println("Office deleted successfully.");
                break;
            case "5":
                List<Office> offices = officeService.getAllOffices();
                System.out.println("List of offices: ");

                for(Office office1: offices){
                    System.out.println("-------------");
                    printOffice(office1);

                }

        }
    }
    private static Doctor inputDoctor(Scanner scanner, DoctorService doctorService) {
        while(true){
            System.out.print("Enter doctor ID: ");
            Doctor doctor = doctorService.getDoctorById(scanner.nextInt());
            scanner.nextLine();
            if(doctor != null) {
                return doctor;
            }

            System.out.println("Doctor not found. Try again.");
        }
    }

    private static Patient inputPatient(Scanner scanner, PatientService patientService){
        while(true){
            System.out.print("Enter patient ID: ");

            Patient p1 = patientService.getPatientById(scanner.nextInt());
            scanner.nextLine();
            if(p1 != null) {
                return p1;
            }
            System.out.println("Patient not found. Try again.");
        }
    }
    private static String inputDate(Scanner scanner, String prompt){
        while(true) {

            System.out.println(prompt);

            if(scanner.hasNext("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                return scanner.nextLine();
            }
            System.out.println("Invalid date format.");
            scanner.nextLine();

        }
    }
    private static String inputPhoneNumber(Scanner scanner){
        while(true){
            System.out.print("Enter phone number (###-###-####): ");
            String phone = scanner.nextLine();
            if(isPhone(phone))
                return phone;

            System.out.println("Invalid phone number. Try again.");
        }
    }
    private static Boolean isPhone(String phone){
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();


    }
    private static Appointment inputAppointment(Scanner scanner, AppointmentService appointmentService){
        while(true){
            System.out.print("Enter Appointment ID: ");

            Appointment appointment = appointmentService.getAppointmentById(scanner.nextInt());
            scanner.nextLine();
            if(appointment != null) {
                return appointment;
            }
            System.out.println("appointment not found. Try again.");
        }
    }

    private static Office inputOffice(Scanner scanner, OfficeService officeService) {
        while(true){
            System.out.print("Enter Office ID: ");
            Office office = officeService.getOfficeById(scanner.nextInt());
            scanner.nextLine();
            if(office != null) {
                return office;
            }

            System.out.println("Office not found. Try again.");
        }
    }
    private static String printMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("Enter your choice [1-5]");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Appointments");
            System.out.println("4. Manage Offices");
            System.out.println("5. End program");

            String choice = scanner.nextLine();
            if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5")) {
                return choice;
            }
            System.out.println("Invalid choice. Try again.");
        }
    }
    private static String getPatientChoice(Scanner scanner) {
        while(true){
            System.out.println("Select an option [1-4]: ");
            System.out.println("1. Create Patient");
            System.out.println("2. Read Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");

            String choice = scanner.nextLine();
            if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                return choice;
            }
            System.out.println("Invalid choice. Try again.");
        }

    }

    private static String printOfficeMenu(Scanner scanner){
        while(true){
            System.out.println("Select an option: [1-5]");
            System.out.println("1. Create Office");
            System.out.println("2. Read Office");
            System.out.println("3. Update Office");
            System.out.println("4. Delete Office");
            System.out.println("5. List All Office");


            String choice  = scanner.nextLine();
            if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5")){
                return choice;
            }
            System.out.println("Invalid choice. Please try again");
        }
    }
    private static void printOffice(Office office){
        System.out.println("Office ID: " + office.getOfficeId());
        System.out.println("Office location: " + office.getLocation());
        System.out.println("Phone number: " + office.getPhone());
        System.out.println("Doctor ID: " + office.getDoctor().getDoctorId());
    }
}


