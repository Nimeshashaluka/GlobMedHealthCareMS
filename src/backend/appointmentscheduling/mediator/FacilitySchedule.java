package backend.appointmentscheduling.mediator;

import java.time.LocalDateTime;

public class FacilitySchedule {

    private final AppointmentMediator mediator;

    public FacilitySchedule(AppointmentMediator mediator) {
        this.mediator = mediator;
    }

    public boolean isFacilityAvailable(int facilityId, LocalDateTime dateTime) {
        return mediator.checkFacilityAvailability(facilityId, dateTime);
    }
}
