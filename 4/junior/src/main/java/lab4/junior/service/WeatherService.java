package lab4.junior.service;

import lab4.junior.dto.WeatherResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Map<String, WeatherSource> sourcesById;

    public WeatherService(List<WeatherSource> sources) {
        this.sourcesById = sources.stream().collect(Collectors.toMap(WeatherSource::getId, Function.identity()));
    }

    public List<WeatherSource> getAvailableSources() {
        return List.copyOf(sourcesById.values());
    }

    public Optional<WeatherSource> getSource(String id) {
        return Optional.ofNullable(sourcesById.get(id));
    }

    public WeatherResponse getWeather(String city, String sourceId) {
        return getSource(sourceId)
                .map(source -> source.getWeather(city))
                .orElse(WeatherResponse.error(city, sourceId, "Неизвестный источник"));
    }
}
