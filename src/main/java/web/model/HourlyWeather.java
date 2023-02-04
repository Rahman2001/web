package web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class HourlyWeather extends Weather {
    private String api_name;
    @JsonProperty("cnt")
    private Integer forecastedTotalHours;
    @JsonProperty("list")
    private List<Weather> hourlyWeatherList;
}
