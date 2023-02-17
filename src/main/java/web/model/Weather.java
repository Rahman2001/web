package web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Weather {
    @JsonProperty(value = "api_name")
    private String api_name;
    @JsonProperty("dt")
    private String dateTime;
    @JsonProperty("description")
    private String description;
    @JsonProperty("temp")
    private Integer temp;
    @JsonProperty("temp_min")
    private Integer tempMin;
    @JsonProperty("temp_max")
    private Integer tempMax;
    @JsonProperty("feels_like")
    private Integer feelsLike;
    @JsonProperty("pressure")
    private Integer pressure;
    @JsonProperty("humidity")
    private Integer humidity;
    @JsonProperty("wind")
    private Integer wind;
    @JsonProperty("clouds")
    private Integer clouds;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
}
