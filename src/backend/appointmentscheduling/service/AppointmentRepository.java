package backend.appointmentscheduling.service;

import model.Appointment;
import model.Patient;
import model.Staff;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public Appointment getAppointment(int appointmentId) {
        try {
            String query = "SELECT * FROM appointment WHERE appointment_id = ?";
            ResultSet rs = MySQL.executeQuery(query, appointmentId);
            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatient(new Patient()); // Placeholder; fetch if needed
                appointment.setStaff(new Staff()); // Placeholder; fetch if needed
                appointment.setDateTime(rs.getObject("date_time", LocalDateTime.class));
                appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
                rs.close();
                return appointment;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve appointment: " + e.getMessage());
        }
    }

    public List<Appointment> getAllAppointments() {
        try {
            String query = "SELECT a.appointment_id, a.patient_id, a.staff_id, a.date_time, a.status, "
                    + "p.name AS patient_name, s.name AS staff_name "
                    + "FROM appointment a "
                    + "JOIN patient p ON a.patient_id = p.patient_id "
                    + "JOIN staff s ON a.staff_id = s.staff_id";
            ResultSet rs = MySQL.executeQuery(query);
            List<Appointment> appointments = new ArrayList<>();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("patient_name"));
                appointment.setPatient(patient);
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("staff_name"));
                appointment.setStaff(staff);
                appointment.setDateTime(rs.getObject("date_time", LocalDateTime.class));
                appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
                appointments.add(appointment);
            }
            rs.close();
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve appointments: " + e.getMessage());
        }
    }

    public List<Appointment> searchAppointments(String query) {
        try {
            String sql = "SELECT a.appointment_id, a.patient_id, a.staff_id, a.date_time, a.status, "
                    + "p.name AS patient_name, s.name AS staff_name "
                    + "FROM appointment a "
                    + "JOIN patient p ON a.patient_id = p.patient_id "
                    + "JOIN staff s ON a.staff_id = s.staff_id "
                    + "WHERE p.name LIKE ? OR a.appointment_id = ?";
            ResultSet rs = MySQL.executeQuery(sql, "%" + query + "%", query);
            List<Appointment> appointments = new ArrayList<>();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("patient_name"));
                appointment.setPatient(patient);
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("staff_name"));
                appointment.setStaff(staff);
                appointment.setDateTime(rs.getObject("date_time", LocalDateTime.class));
                appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
                appointments.add(appointment);
            }
            rs.close();
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search appointments: " + e.getMessage());
        }
    }

    public void saveAppointment(Appointment appointment) {
        try {
            String query = "INSERT INTO appointment (patient_id, staff_id, date_time, status) VALUES (?, ?, ?, ?)";
            MySQL.executeUpdate(query,
                    appointment.getPatient().getPatientId(),
                    appointment.getStaff().getStaffId(),
                    appointment.getDateTime(),
                    appointment.getStatus().name()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save appointment: " + e.getMessage());
        }
    }

    public boolean isStaffAvailable(int staffId, LocalDateTime dateTime) {
        try {
            String query = "SELECT 1 FROM appointment WHERE staff_id = ? AND date_time = ? AND status = 'Scheduled'";
            ResultSet rs = MySQL.executeQuery(query, staffId, dateTime);
            boolean isBusy = rs.next();
            rs.close();
            return !isBusy;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check staff availability: " + e.getMessage());
        }
    }

    public boolean isFacilityAvailable(int facilityId, LocalDateTime dateTime) {
        // hardcoded
        return true;
    }

    public boolean isPatientAvailable(int patientId, LocalDateTime dateTime) {
        try {
            String query = "SELECT 1 FROM appointment WHERE patient_id = ? AND date_time = ? AND status = 'Scheduled'";
            ResultSet rs = MySQL.executeQuery(query, patientId, dateTime);
            boolean isBusy = rs.next();
            rs.close();
            return !isBusy;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check patient availability: " + e.getMessage());
        }
    }

    public void cancelAppointment(int appointmentId) {
        try {
            String query = "UPDATE appointment SET status = 'Cancelled' WHERE appointment_id = ?";
            MySQL.executeUpdate(query, appointmentId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to cancel appointment: " + e.getMessage());
        }
    }
}
