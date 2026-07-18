package it.unibo.hookmaster.model.weather;

/**
 * Defines the contract for the weather system.
 */
public interface WeatherSystem {

    /**
     * Advances the weather system by one frame.
     * May trigger a weather change.
     * 
     * @param deltaTime seconds elapsed since the last frame
     */
    void update(double deltaTime);

    /**
     * Gets the currently active weather condition.
     *
     * @return the current Weather
     */
    Weather getCurrentWeather();

    /**
     * Registers an observer to be notified of weather changes.
     * 
     * @param observer the observer to add
     */
    void addObserver(WeatherObserver observer);

    /**
     * Removes a previously registered observer.
     * 
     * @param observer the observer to remove
     */
    void removeObserver(WeatherObserver observer);
}
