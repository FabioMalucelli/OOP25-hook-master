package it.unibo.hookmaster.model.weather;

/**
 * Observer interface for weather changes.
 */
@FunctionalInterface
public interface WeatherObserver {
    /**
     * Called by the WeatherSystem whenever the weather changes.
     * 
     * @param event the event carrying the new weather condition
     */
    void onWeatherChanged(WeatherEvent event);
}
