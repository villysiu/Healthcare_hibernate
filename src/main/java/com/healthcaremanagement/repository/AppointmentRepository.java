package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AppointmentRepository {
    private final SessionFactory sessionFactory;

    public AppointmentRepository(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void createAppointment(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(appointment);
            transaction.commit();
        }
    }

    public Appointment getAppointmentById(int appointmentId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Appointment.class, appointmentId);
        }
    }

    public void updateAppointment(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(appointment);
            transaction.commit();
        }
    }

    public void deleteAppointment(int appointmentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, appointmentId);
            if (appointment != null) {
                session.remove(appointmentId);
            }
            transaction.commit();
        }
    }

    public List<Appointment> getAllAppointments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Appointment", Appointment.class).list();
        }
    }
}
