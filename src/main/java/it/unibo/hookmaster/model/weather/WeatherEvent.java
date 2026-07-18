package it.unibo.hookmaster.model.weather;

public final class WeatherEvent {
    private final Weather weather;

    /**
     * Builds the event.
     * 
     * @param weather the new weather condition
     */
    public WeatherEvent(final Weather weather) {
        this.weather = weather;
    }

    /**
     * Gets the new weather condition.
     * 
     * @return the new weather
     */
    public Weather getWeather() {
        return weather;
    }
}
