package backend.appointmentscheduling.mediator;

import java.time.LocalDateTime;

public class DoctorSchedule {

    private final AppointmentMediator mediator;

    public DoctorSchedule(AppointmentMediator mediator) {
        this.mediator = mediator;
    }

    public boolean isStaffAvailable(int staffId, LocalDateTime dateTime) {
        return mediator.checkStaffAvailability(staffId, dateTime);
    }
}
