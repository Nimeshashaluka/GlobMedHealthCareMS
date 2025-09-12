package backend.appointmentscheduling.service;

import backend.appointmentscheduling.mediator.AppointmentMediator;
import backend.appointmentscheduling.mediator.ConcreteAppointmentMediator;
import model.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import model.Patient;
import model.Staff;

public class AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMediator mediator;

    public AppointmentService() {
        this.repository = new AppointmentRepository();
        this.mediator = new ConcreteAppointmentMediator(this);
    }

    public boolean createAppointment(int patientId, int staffId, int facilityId, LocalDateTime dateTime) {
        if (!mediator.scheduleAppointment(patientId, staffId, facilityId, dateTime)) {
            return false;
        }

        // Create and save appointment
        Appointment appointment = new Appointment();
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        Staff staff = new Staff();
        staff.setStaffId(staffId);
        appointment.setPatient(patient);
        appointment.setStaff(staff);
        appointment.setDateTime(dateTime);
        appointment.setStatus(Appointment.Status.Scheduled);
        repository.saveAppointment(appointment);
        return true;
    }

    public void cancelAppointment(int appointmentId) {
        repository.cancelAppointment(appointmentId);
    }

    public Appointment getAppointment(int appointmentId) {
        return repository.getAppointment(appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return repository.getAllAppointments();
    }

    public List<Appointment> searchAppointments(String query) {
        return repository.searchAppointments(query);
    }

    // Methods called by mediator
    public boolean isStaffAvailable(int staffId, LocalDateTime dateTime) {
        return repository.isStaffAvailable(staffId, dateTime);
    }

    public boolean isFacilityAvailable(int facilityId, LocalDateTime dateTime) {
        return repository.isFacilityAvailable(facilityId, dateTime);
    }

    public boolean isPatientAvailable(int patientId, LocalDateTime dateTime) {
        return repository.isPatientAvailable(patientId, dateTime);
    }

    public void saveAppointment(Appointment appointment) {
        repository.saveAppointment(appointment);
    }
}
