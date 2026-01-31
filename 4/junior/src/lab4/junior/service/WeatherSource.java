package lab4.junior.service;

import lab4.junior.dto.WeatherResponse;

public interface WeatherSource {

    String getId();

    String getDisplayName();

    WeatherResponse getWeather(String city);
}
