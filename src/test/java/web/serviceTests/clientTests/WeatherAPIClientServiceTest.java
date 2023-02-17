package web.serviceTests.clientTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.config.RestServiceForRetrofit;
import web.config.RetrofitConfig;
import web.config.RetrofitProperties;
import web.model.UserLocation;
import web.model.Weather;
import web.service.clientService.WeatherAPIClientService;
import web.service.serviceImpl.ServiceUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {RetrofitConfig.class, RetrofitProperties.class, RestServiceForRetrofit.class, ServiceUtil.class})
public class WeatherAPIClientServiceTest {
    @Autowired
    private WeatherAPIClientService weatherAPIClientService;

    @Test
    public void weatherClientTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Weather> currentWeather = weatherAPIClientService.getCurrentWeatherByCity("/weather", "Oslo");
        assertNotNull(currentWeather);
        assertEquals("Oslo", currentWeather.get().getCity());
    }
    @Test
    public void weatherClientTest2() throws ExecutionException, InterruptedException {
        CompletableFuture<Weather> currentWeather = weatherAPIClientService.getCurrentWeather("/weather", UserLocation.builder()
                .lon(32.8469).lat(39.9547).country("Turkey").city("Ankara").build());
        assertNotNull(currentWeather);
        assertNotNull(currentWeather.get().getTemp());
    }
}
