package backend.appointmentscheduling.mediator;

import backend.appointmentscheduling.service.AppointmentService;
import java.time.LocalDateTime;

public class ConcreteAppointmentMediator implements AppointmentMediator {

    private final DoctorSchedule doctorSchedule;
    private final PatientBooking patientBooking;
    private final FacilitySchedule facilitySchedule;
    private final AppointmentService appointmentService;

    public ConcreteAppointmentMediator(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.doctorSchedule = new DoctorSchedule(this);
        this.patientBooking = new PatientBooking(this);
        this.facilitySchedule = new FacilitySchedule(this);
    }

    @Override
    public boolean scheduleAppointment(int patientId, int staffId, int facilityId, LocalDateTime dateTime) {
        // Check availability with all components
        return doctorSchedule.isStaffAvailable(staffId, dateTime)
                && facilitySchedule.isFacilityAvailable(facilityId, dateTime)
                && patientBooking.isPatientAvailable(patientId, dateTime);
    }

    @Override
    public void cancelAppointment(int appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }

    @Override
    public boolean checkPatientAvailability(int patientId, LocalDateTime dateTime) {
        return appointmentService.isPatientAvailable(patientId, dateTime);
    }

    @Override
    public boolean checkStaffAvailability(int staffId, LocalDateTime dateTime) {
        return appointmentService.isStaffAvailable(staffId, dateTime);
    }

    @Override
    public boolean checkFacilityAvailability(int facilityId, LocalDateTime dateTime) {
        return appointmentService.isFacilityAvailable(facilityId, dateTime);
    }
}
