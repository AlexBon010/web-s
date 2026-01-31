package lab4.junior.service;

import com.fasterxml.jackson.databind.JsonNode;
import lab4.junior.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class WttrInSource implements WeatherSource {

    private static final String URL = "https://wttr.in/%s?format=j1";

    private final RestTemplate restTemplate;

    public WttrInSource(RestTemplateBuilder builder,
                       @Value("${weather.http-timeout:5000}") int timeoutMs) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(timeoutMs))
                .setReadTimeout(Duration.ofMillis(timeoutMs))
                .defaultHeader(HttpHeaders.USER_AGENT, "WeatherLab/1.0 (Educational)")
                .build();
    }

    @Override
    public String getId() {
        return "WTTR_IN";
    }

    @Override
    public String getDisplayName() {
        return "Wttr.in";
    }

    @Override
    public WeatherResponse getWeather(String city) {
        if (city == null || city.isBlank()) {
            return WeatherResponse.error(city, getDisplayName(), "Город не указан");
        }
        String encodedCity = city.trim().replace(" ", "+");
        try {
            String url = String.format(URL, encodedCity);
            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            if (root == null || !root.has("current_condition")) {
                return WeatherResponse.error(city, getDisplayName(), "Нет данных");
            }
            JsonNode current = root.get("current_condition").get(0);
            double temp = current.has("temp_C") ? current.get("temp_C").asDouble() : 0;
            int humidity = current.has("humidity") ? current.get("humidity").asInt() : 0;
            return WeatherResponse.success(city, getDisplayName(), temp, humidity);
        } catch (Exception e) {
            String msg = e.getMessage() != null && e.getMessage().contains("timed out")
                    ? "Источник отвечает слишком долго. Попробуйте другой источник или город (латиницей, например Minsk)."
                    : "Ошибка: " + e.getMessage();
            return WeatherResponse.error(city, getDisplayName(), msg);
        }
    }
}
