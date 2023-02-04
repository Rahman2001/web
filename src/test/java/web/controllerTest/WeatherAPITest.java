package web.controllerTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import web.controllers.WeatherAPI;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {WeatherAPI.class})
@ContextConfiguration(classes = {WeatherAPI.class})
public class WeatherAPITest {

}
