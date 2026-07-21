package it.unibo.hookmaster.testutil;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.model.weather.WeatherEvent;
import it.unibo.hookmaster.model.weather.WeatherObserver;

/**
 * A WeatherObserver that records every event it recieves in order,
 * so tests can assert what was fired and how many times. 
 */
public final class RecordingWeatherObserver implements WeatherObserver {

    private final List<WeatherEvent> recievedEvents = new ArrayList<>();

    @Override
    public void onWeatherChanged(final WeatherEvent event) {
        recievedEvents.add(event);
    }

    /** 
     * 
     * @return an unmodifiable list of all events recieved
     */
    public List<WeatherEvent> getRecievedEvents() {
        return List.copyOf(recievedEvents);
    }
}
