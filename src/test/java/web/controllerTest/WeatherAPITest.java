package web.controllerTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import web.controllers.WeatherAPI;
import web.model.HourlyWeather;
import web.model.UserLocation;
import web.model.Weather;
import web.service.serviceImpl.IP_APIClientServiceImpl;
import web.service.serviceImpl.WeatherAPIClientServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.id.IdentifierGeneratorHelper.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {WeatherAPI.class})
@ContextConfiguration(classes = {WeatherAPI.class})
public class WeatherAPITest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherAPI weatherAPI;

    @Test
    public void firstApiTest() throws Exception {
        when(this.weatherAPI.getWeatherByCurrentLocation()).thenReturn(ResponseEntity.ok(new ArrayList<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/weather"))
                .andExpect(status().isOk());
    }

}
