package it.unibo.hookmaster.model.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.testutil.RecordingWeatherObserver;

/**
 * Unit tests for WeatherSystemImpl.
 */
class WeatherSystemImplTest {
    private static final double FIXED_INTERVAL_SECONDS = 1.0;
    private static final long JUST_BEFORE_INTERVAL = 999L;
    private static final long EXACT_INTERVAL = 1000L;
    private static final long MANY_INTERVALS = 10_000L;
    private static final int MIN_EXPECTED_CHANGES = 10;

    @Test
    void constructorRejectsNonPositiveMinInterval() {
        assertThrows(IllegalArgumentException.class, () -> new WeatherSystemImpl(0.0, 1.0));
    }

    @Test
    void constructorRejectsMaxLowerThanMin() {
        assertThrows(IllegalArgumentException.class, () -> new WeatherSystemImpl(2.0, 1.0));
    }

    @Test
    void startsWithClearWeather() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl();
        assertEquals(Weather.CLEAR, weatherSystem.getCurrentWeather());
    }

    @Test
    void doesNotChangeWeatherBeforeIntervalElapses() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        final RecordingWeatherObserver observer = new RecordingWeatherObserver();
        weatherSystem.addObserver(observer);

        weatherSystem.update(JUST_BEFORE_INTERVAL);

        assertEquals(Weather.CLEAR, weatherSystem.getCurrentWeather());
        assertTrue(observer.getRecievedEvents().isEmpty());
    }

    @Test
    void changesWeatherAndNotifiesObserverOnceIntervalElapses() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        final RecordingWeatherObserver observer = new RecordingWeatherObserver();
        weatherSystem.addObserver(observer);

        weatherSystem.update(EXACT_INTERVAL);

        assertEquals(Weather.STORMY, weatherSystem.getCurrentWeather());
        assertEquals(1, observer.getRecievedEvents().size());
        assertEquals(Weather.STORMY, observer.getRecievedEvents().get(0).getWeather());
    }

    @Test
    void neverPicksTheSameWeatherTwiceInARow() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        Weather previous = weatherSystem.getCurrentWeather();

        for (int i = 0; i < MIN_EXPECTED_CHANGES; i++) {
            weatherSystem.update(EXACT_INTERVAL);
            final Weather current = weatherSystem.getCurrentWeather();
            assertNotSame(previous, current);
            previous = current;
        }
    }

    @Test
    void cathcesUpOnMultipleChangesWhenDeltaTimeIsLarge() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        final RecordingWeatherObserver observer = new RecordingWeatherObserver();
        weatherSystem.addObserver(observer);

        weatherSystem.update(MANY_INTERVALS);

        assertTrue(observer.getRecievedEvents().size() >= MIN_EXPECTED_CHANGES);
    }

    @Test
    void notifiesMultipleObservers() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        final RecordingWeatherObserver firstObserver = new RecordingWeatherObserver();
        final RecordingWeatherObserver secondObserver = new RecordingWeatherObserver();
        weatherSystem.addObserver(firstObserver);
        weatherSystem.addObserver(secondObserver);

        weatherSystem.update(EXACT_INTERVAL);

        assertEquals(1, firstObserver.getRecievedEvents().size());
        assertEquals(1, secondObserver.getRecievedEvents().size());
    }

    @Test
    void removedObserverIsNoLongerNotified() {
        final WeatherSystem weatherSystem = new WeatherSystemImpl(FIXED_INTERVAL_SECONDS, FIXED_INTERVAL_SECONDS);
        final RecordingWeatherObserver observer = new RecordingWeatherObserver();
        weatherSystem.addObserver(observer);
        weatherSystem.removeObserver(observer);

        weatherSystem.update(EXACT_INTERVAL);

        assertTrue(observer.getRecievedEvents().isEmpty());
    }
}
