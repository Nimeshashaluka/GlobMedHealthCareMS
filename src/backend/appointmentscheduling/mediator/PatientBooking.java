package backend.appointmentscheduling.mediator;

import java.time.LocalDateTime;

public class PatientBooking {

    private final AppointmentMediator mediator;

    public PatientBooking(AppointmentMediator mediator) {
        this.mediator = mediator;
    }

    public boolean isPatientAvailable(int patientId, LocalDateTime dateTime) {
        return mediator.checkPatientAvailability(patientId, dateTime);
    }
}
