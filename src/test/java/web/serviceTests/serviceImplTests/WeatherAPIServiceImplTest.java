package web.serviceTests.serviceImplTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import web.config.RetrofitConfig;
import web.config.RetrofitProperties;
import web.model.DailyWeather;
import web.model.HourlyWeather;
import web.model.UserLocation;
import web.model.Weather;
import web.service.clientService.WeatherAPIClientService;
import web.service.serviceImpl.ServiceUtil;
import web.service.serviceImpl.WeatherAPIClientServiceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {RetrofitProperties.class, RetrofitConfig.class, WeatherAPIClientServiceImpl.class, ServiceUtil.class})
public class WeatherAPIServiceImplTest {
    @MockBean
    private WeatherAPIClientService weatherClient;
    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private ServiceUtil serviceUtil;
    @Autowired
    private WeatherAPIClientServiceImpl weatherService;

    @Test
    public void getAllWeatherData() {
        when(weatherClient.getCurrentWeather(anyString(), any(UserLocation.class))).thenReturn(CompletableFuture.supplyAsync(Weather::new));
        when(weatherClient.getHourlyWeather(anyString(), any(UserLocation.class)))
                .thenReturn(CompletableFuture.supplyAsync(HourlyWeather::new));
        when(weatherClient.getDailyWeather(anyString(), any(UserLocation.class)))
                .thenReturn(CompletableFuture.supplyAsync(DailyWeather::new));
        List<?> weathers = this.weatherService.getAllWeatherData(UserLocation.builder().build());
        assertNotNull(weathers);
    }
}
