package lab4.junior.controller;

import lab4.junior.dto.WeatherResponse;
import lab4.junior.service.WeatherService;
import lab4.junior.service.WeatherSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<WeatherSource> sources = weatherService.getAvailableSources();
        model.addAttribute("sources", sources);
        return "index";
    }

    @GetMapping("/weather")
    public String weather(
            @RequestParam String city,
            @RequestParam String source,
            Model model
    ) {
        WeatherResponse result = weatherService.getWeather(city, source);
        model.addAttribute("weather", result);
        model.addAttribute("sources", weatherService.getAvailableSources());
        return "index";
    }
}
