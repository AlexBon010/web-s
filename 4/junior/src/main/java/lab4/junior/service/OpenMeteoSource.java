package lab4.junior.service;

import com.fasterxml.jackson.databind.JsonNode;
import lab4.junior.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class OpenMeteoSource implements WeatherSource {

    private static final String GEO_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1";
    private static final String WEATHER_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m";

    private final RestTemplate restTemplate;

    public OpenMeteoSource(RestTemplateBuilder builder,
                           @Value("${weather.http-timeout:5000}") int timeoutMs) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(timeoutMs))
                .setReadTimeout(Duration.ofMillis(timeoutMs))
                .build();
    }

    @Override
    public String getId() {
        return "OPEN_METEO";
    }

    @Override
    public String getDisplayName() {
        return "Open-Meteo";
    }

    @Override
    public WeatherResponse getWeather(String city) {
        if (city == null || city.isBlank()) {
            return WeatherResponse.error(city, getDisplayName(), "Город не указан");
        }
        String encodedCity = city.trim().replace(" ", "+");
        try {
            String geoUrl = String.format(GEO_URL, encodedCity);
            JsonNode geo = restTemplate.getForObject(geoUrl, JsonNode.class);
            if (geo == null || !geo.has("results") || geo.get("results").isEmpty()) {
                return WeatherResponse.error(city, getDisplayName(), "Город не найден");
            }
            JsonNode first = geo.get("results").get(0);
            String lat = first.get("latitude").asText();
            String lon = first.get("longitude").asText();

            String weatherUrl = String.format(WEATHER_URL, lat, lon);
            JsonNode weather = restTemplate.getForObject(weatherUrl, JsonNode.class);
            if (weather == null || !weather.has("current")) {
                return WeatherResponse.error(city, getDisplayName(), "Нет данных о погоде");
            }
            JsonNode current = weather.get("current");
            double temp = current.has("temperature_2m") ? current.get("temperature_2m").asDouble() : 0;
            int humidity = current.has("relative_humidity_2m") ? current.get("relative_humidity_2m").asInt() : 0;
            return WeatherResponse.success(city, getDisplayName(), temp, humidity);
        } catch (Exception e) {
            String msg = e.getMessage() != null && e.getMessage().contains("timed out")
                    ? "Источник отвечает слишком долго. Попробуйте позже."
                    : "Ошибка: " + e.getMessage();
            return WeatherResponse.error(city, getDisplayName(), msg);
        }
    }
}
