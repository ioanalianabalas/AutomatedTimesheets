import java.io.IOException;

import synergistUtils.LoginScreen;
import calendarUtils.CalendarSetup;
import calendarUtils.EventDurationGenerator;

public class Main {
	public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service = CalendarSetup.getCalendarService();
        EventDurationGenerator eventDurations = new EventDurationGenerator(service);
        eventDurations.printEventsForCurrentWeek(eventDurations.getEventsForCurrentWeek());
        LoginScreen.createLoginScreen();
        
    }
}
