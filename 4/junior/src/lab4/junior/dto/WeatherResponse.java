package lab4.junior.dto;

public class WeatherResponse {

    private String city;
    private String source;
    private Double temperatureCelsius;
    private Integer humidityPercent;
    private String error;

    public static WeatherResponse success(String city, String source, Double temp, Integer humidity) {
        WeatherResponse r = new WeatherResponse();
        r.setCity(city);
        r.setSource(source);
        r.setTemperatureCelsius(temp);
        r.setHumidityPercent(humidity);
        return r;
    }

    public static WeatherResponse error(String city, String source, String message) {
        WeatherResponse r = new WeatherResponse();
        r.setCity(city);
        r.setSource(source);
        r.setError(message);
        return r;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Double getTemperatureCelsius() { return temperatureCelsius; }
    public void setTemperatureCelsius(Double temperatureCelsius) { this.temperatureCelsius = temperatureCelsius; }
    public Integer getHumidityPercent() { return humidityPercent; }
    public void setHumidityPercent(Integer humidityPercent) { this.humidityPercent = humidityPercent; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public boolean isSuccess() { return error == null; }
}
