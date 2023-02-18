package web.service.serviceImpl;

import web.config.EndpointProperty;
import web.model.HourlyWeather;
import web.model.UserLocation;
import web.model.Weather;
import web.service.clientService.WeatherAPIClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class WeatherAPIClientServiceImpl{

    private final WeatherAPIClientService weatherRestAPI;
    private final EndpointProperty currentWeatherEndpoint;
    private final EndpointProperty hourlyWeatherEndpoint;
    private final EndpointProperty dailyWeatherEndpoint;

    @Autowired
    public WeatherAPIClientServiceImpl(final WeatherAPIClientService weatherRestAPI,
                                       final @Qualifier("restEndpoints") List<EndpointProperty> restEndpoints,
                                       @Qualifier("serviceUtil") final ServiceUtil serviceUtil) {
        this.weatherRestAPI = weatherRestAPI;
        Map<String, EndpointProperty> map = serviceUtil.groupsEndpoints(restEndpoints);
        this.currentWeatherEndpoint = map.get("currentWeather");
        this.hourlyWeatherEndpoint = map.get("hourlyWeather");
        this.dailyWeatherEndpoint = map.get("dailyWeather");
    }

    public List<?> getAllWeatherData(UserLocation userLocation) {
        try {
            List<CompletableFuture<?>> weathers = getAllWeatherDataByCurrentLocation(userLocation);
            return combineAllWeatherData(weathers)
                    .thenApply(futureFunction -> weathers.stream()
                            .filter(Objects::nonNull)
                            .map(CompletableFuture::join).toList()).join();

        } catch (Exception e) {
            log.error("Could not return all weather data for current location! - ", e);
            return null;
        }
    }
    public List<?> getAllWeatherData(String city) {
        try {
            List<CompletableFuture<?>> weathers = getAllWeatherDataByCity(city); //we get list CompletableFutures first and pass it to allOf()
            return combineAllWeatherData(weathers) // if all CompletableFutures are complete, thenApply() method is performed.
                    .thenApply(futureFunction -> weathers.stream() //since allOf() guarantees the completion, we can use 'weathers'
                            .filter(Objects::nonNull)
                            .map(CompletableFuture::join).toList()).join();
        }
        catch (Exception e) {
            log.error("Could not return all weather data for indicated city! - ", e);
            return null;
        }
    }
    private List<CompletableFuture<?>> getAllWeatherDataByCurrentLocation(UserLocation userLocation) {
        return List.of(
                this.weatherRestAPI.getCurrentWeather(currentWeatherEndpoint.getPath(), userLocation).exceptionally(exception -> {
                    log.error("Something went wrong with CURRENT WEATHER data!", exception);
                    return null;
                }),
                this.weatherRestAPI.getHourlyWeather(hourlyWeatherEndpoint.getPath(),userLocation).exceptionally(exception ->{
                    log.error("Something went wrong with HOURLY WEATHER data!", exception);
                    return null;
                }),
                this.weatherRestAPI.getDailyWeather(dailyWeatherEndpoint.getPath(), userLocation).exceptionally(exception ->{
                    log.error("Something went wrong with DAILY WEATHER data!", exception);
                    return null;
                })
        );
    }
    private List<CompletableFuture<?>> getAllWeatherDataByCity(String city) {
        return List.of(
                this.weatherRestAPI.getCurrentWeatherByCity(currentWeatherEndpoint.getPath(), city).exceptionally(exception -> {
                    log.error("Something went wrong with CURRENT WEATHER data!", exception);
                    return null;
                }),
                this.weatherRestAPI.getHourlyWeatherByCity(hourlyWeatherEndpoint.getPath(), city).exceptionally(exception -> {
                    log.error("Something went wrong with HOURLY WEATHER data!", exception);
                    return null;
                }),
                this.weatherRestAPI.getDailyWeatherByCity(dailyWeatherEndpoint.getPath(), city).exceptionally(exception -> {
                    log.error("Something went wrong with DAILY WEATHER data!", exception);
                    return null;
                })
        );
    }
    private CompletableFuture<Void> combineAllWeatherData(List<CompletableFuture<?>> allWeatherData) { // allOf() is useful since it waits until all CompletableFutures complete.
        return CompletableFuture.allOf(allWeatherData.toArray(new CompletableFuture[allWeatherData.size()]));
    }
}
