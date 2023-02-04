package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import web.model.UserLocation;
import web.model.Weather;
import web.service.serviceImpl.IP_APIClientServiceImpl;
import web.service.serviceImpl.WeatherAPIClientServiceImpl;

import java.util.List;

@RestController("/weather")
public class WeatherAPI {

    private final WeatherAPIClientServiceImpl weatherService;
    private final UserLocation userLocation;

    @Autowired
    public WeatherAPI (final IP_APIClientServiceImpl ipService,
                       final WeatherAPIClientServiceImpl weatherAPIService) {
        this.weatherService = weatherAPIService;
        this.userLocation = ipService.getUserLocation();
    }

    @GetMapping
    public ResponseEntity<List<? extends Weather>> getWeatherByCurrentLocation() {
        List<? extends Weather> weathers = this.weatherService.getAllWeatherData(this.userLocation);
        return weathers != null && !weathers.isEmpty() ? ResponseEntity.ok(weathers) : ResponseEntity.badRequest().build();
    }
    @GetMapping("/{city}")
    public ResponseEntity<List<? extends Weather>> getWeatherByCity(@PathVariable("city") String city) {
        List<? extends Weather> weathers = this.weatherService.getAllWeatherData(city);
        return weathers != null && !weathers.isEmpty() ? ResponseEntity.ok(weathers) : ResponseEntity.badRequest().build();
    }
}
