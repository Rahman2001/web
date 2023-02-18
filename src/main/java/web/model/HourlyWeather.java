package web.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeather {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String weatherForecasted = "hourly_weather";
    @JsonProperty("cnt")
    private Integer forecastedTotalHours;
    @JsonProperty("list")
    private List<Weather> hourlyWeatherList;

    public List<Weather> getHourlyWeatherList() {
        this.hourlyWeatherList = hourlyWeatherList.stream().peek(weather -> weather.setWeatherForecasted("hourly_weather")).toList();
        return this.hourlyWeatherList;
    }
}
