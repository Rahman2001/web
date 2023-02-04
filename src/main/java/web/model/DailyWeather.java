package web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import web.service.deserializers.DailyWeatherDeserializer;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DailyWeather extends Weather{
    private String api_name;
    @JsonProperty("cnt")
    private Integer forecastedTotalDays;
    @JsonProperty("list")
    private List<Weather> dailyWeatherList;
}
