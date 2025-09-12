package backend.appointmentscheduling.mediator;

import java.time.LocalDateTime;

public interface AppointmentMediator {

    boolean scheduleAppointment(int patientId, int staffId, int facilityId, LocalDateTime dateTime);

    void cancelAppointment(int appointmentId);

    boolean checkPatientAvailability(int patientId, LocalDateTime dateTime);

    boolean checkStaffAvailability(int staffId, LocalDateTime dateTime);

    boolean checkFacilityAvailability(int facilityId, LocalDateTime dateTime);
}
