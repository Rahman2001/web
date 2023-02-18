package web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String weatherForecasted = "daily_weather";
    @JsonProperty("cnt")
    private Integer forecastedTotalDays;
    @JsonProperty("list")
    private List<Weather> dailyWeatherList;

    public List<Weather> getDailyWeatherList() {
        this.dailyWeatherList = dailyWeatherList.stream().peek(weather -> weather.setWeatherForecasted("daily_weather")).toList();
        return this.dailyWeatherList;
    }
}
