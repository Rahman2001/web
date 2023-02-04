package web.service.clientService;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import web.model.DailyWeather;
import web.model.HourlyWeather;
import web.model.UserLocation;
import web.model.Weather;

import java.util.concurrent.CompletableFuture;

public interface WeatherAPIClientService {
    @POST("{path}")
    CompletableFuture<Weather> getCurrentWeather(@Path(value = "path", encoded = true) String path, @Body UserLocation userLocation );
    @GET("{path}/{city}")
    CompletableFuture<Weather> getCurrentWeatherByCity(@Path(value = "path", encoded = true) String path,
                                                       @Path(value = "city", encoded = true) String city);
    @POST("{path}")
    CompletableFuture<HourlyWeather> getHourlyWeather(@Path(value = "path", encoded = true) String path, @Body UserLocation userLocation );
    @GET("{path}/{city}")
    CompletableFuture<HourlyWeather> getHourlyWeatherByCity(@Path(value = "path", encoded = true) String path,
                                                            @Path(value = "city", encoded = true) String city);
    @POST("{path}")
    CompletableFuture<DailyWeather> getDailyWeather(@Path(value = "path", encoded = true) String path, @Body UserLocation userLocation );
    @GET("{path}/{city}")
    CompletableFuture<DailyWeather> getDailyWeatherByCity(@Path(value = "path", encoded = true) String path,
                                                          @Path(value = "city", encoded = true) String city);

}
