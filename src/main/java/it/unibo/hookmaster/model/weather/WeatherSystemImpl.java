package it.unibo.hookmaster.model.weather;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.util.TimeUtils;

/**
 * Strandard implementation of WeatherSystem.
 */
public final class WeatherSystemImpl implements WeatherSystem {
    private static final double DEFAULT_MIN_INTERVAL_SECONDS = 15.0;
    private static final double DEFAULT_MAX_INTERVAL_SECONDS = 45.0;

    private static final Weather[] ALL_WEATHERS = Weather.values();

    private final double minChangeInterval;
    private final double maxChangeInterval;
    private final List<WeatherObserver> observers = new ArrayList<>();

    private Weather currentWeather;
    private double timeUntilNextChange;

    /**
     * Constructs a weather system with default change-interval bounds.
     */
    public WeatherSystemImpl() {
        this(DEFAULT_MIN_INTERVAL_SECONDS, DEFAULT_MAX_INTERVAL_SECONDS);
    }

    /**
     * Constructs a weather system with custom change-interval bounds.
     * 
     * @param minChangeInterval     minimum seconds between weather changes
     * @param maxChangeInterval     maximum seconds between weather changes
     * @throws IllegalArgumentException if the bounds are invalid
     */
    public WeatherSystemImpl(final double minChangeInterval, final double maxChangeInterval) {
        if (minChangeInterval <= 0 || maxChangeInterval < minChangeInterval) {
            throw new IllegalArgumentException("minChangeInterval must be positive and not greater than maxChangeInterval.");
        }
        this.minChangeInterval = minChangeInterval;
        this.maxChangeInterval = maxChangeInterval;
        this.currentWeather = Weather.CLEAR;
        this.timeUntilNextChange = randomInterval();
    }

    @Override
    public void update(final long deltaTime) {
        final double deltaSeconds = TimeUtils.toSeconds(deltaTime);
        timeUntilNextChange -= deltaSeconds;
        while (timeUntilNextChange <= 0) {
            changeWeather();
            timeUntilNextChange += randomInterval();
        }
    }

    @Override
    public Weather getCurrentWeather() {
        return currentWeather;
    }

    @Override
    public void addObserver(final WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(final WeatherObserver observer) {
        observers.remove(observer);
    }

    private void changeWeather() {
        if (ALL_WEATHERS.length > 1) {
            final int currentIndex = currentWeather.ordinal();
            int nextIndex = (int) (Math.random() * (ALL_WEATHERS.length - 1));
            if (nextIndex >= currentIndex) {
                nextIndex++;
            }
            currentWeather = ALL_WEATHERS[nextIndex];
        }

        final WeatherEvent event = new WeatherEvent(currentWeather);
        for (final WeatherObserver observer : observers) {
            observer.onWeatherChanged(event);
        }
    }

    private double randomInterval() {
        return minChangeInterval + Math.random() * (maxChangeInterval - minChangeInterval);
    }
}