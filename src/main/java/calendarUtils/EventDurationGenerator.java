package calendarUtils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EventDurationGenerator {
	
	private final Calendar calendar = Calendar.getInstance();
	private final double conversionRate = 3600000;
	private com.google.api.services.calendar.Calendar service;
	
	public EventDurationGenerator(com.google.api.services.calendar.Calendar service) {
		this.service = service;
	}
	
	private DateTime getStartOfWeek() {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return new DateTime(calendar.getTimeInMillis());
	}

	private DateTime getEndOfWeek() {
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return new DateTime(calendar.getTimeInMillis());
	}
	
	public double getEventDuration(Event event) {
		long duration = event.getEnd().getDateTime().getValue() - event.getStart().getDateTime().getValue();
		return duration / conversionRate;
	}

	public List<Event> getEventsForCurrentWeek() throws IOException {
		Events events = service.events().list("primary")
	            .setTimeMin(getStartOfWeek())
	            .setTimeMax(getEndOfWeek())
	            .setOrderBy("startTime")
	            .setSingleEvents(true)
	            .execute();
		return events.getItems();
	}
	
	public void printEventsForCurrentWeek(List<Event> events) {
		if (events.size() == 0) {
			System.out.println("No events found.");
		}
		else {
			System.out.println("Events this week: ");
			for (Event event : events) {
				System.out.printf("%s \n", event.getSummary());
				System.out.println(getEventDuration(event));
            }
        }
	}
	
	public HashMap<String, Double> getEventsAndDurationForCurrentWeek() throws IOException {
		HashMap<String, Double> eventDurations = new HashMap<>();
		List<Event> events = getEventsForCurrentWeek();
		for (Event event : events) {
			eventDurations.put(event.getSummary(), getEventDuration(event));
		}
		return eventDurations;
	}
	
}
