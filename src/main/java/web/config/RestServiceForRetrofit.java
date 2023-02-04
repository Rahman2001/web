package web.config;

import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import web.service.clientService.WeatherAPIClientService;
import web.service.clientService.IP_APIClientService;
import web.service.serviceImpl.ServiceUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
public class RestServiceForRetrofit {
    private OkHttpClient okHttpClient;
    private JacksonConverterFactory jacksonConverterFactory;
    private final Long RETROFIT_CACHE_SIZE;
    private final Long RETROFIT_LONG_RUNNING_READ_TIMEOUT;
    private final File RETROFIT_CACHE_DIRECTORY;
    private final Long RETROFIT_DEFAULT_READ_TIMEOUT;
    private final Map<String, EndpointProperty> endpointPropertyMap;

    @Autowired
    public RestServiceForRetrofit(@Qualifier("restEndpoints") final List<EndpointProperty> restEndpoints,
                                  @NonNull @Value("${retrofit.integration.cacheSizeInMb}") final Long RETROFIT_CACHE_SIZE,
                                  @NonNull @Value("${retrofit.integration.cacheDirectory}") String RETROFIT_CACHE_DIRECTORY,
                                  @NonNull @Value("${retrofit.integration.longRunningReadTimeout}") Long RETROFIT_LONG_READING_TIMEOUT,
                                  ServiceUtil serviceUtil)
    {
        this.RETROFIT_CACHE_SIZE = RETROFIT_CACHE_SIZE;
        this.RETROFIT_CACHE_DIRECTORY = new File(RETROFIT_CACHE_DIRECTORY);
        this.RETROFIT_LONG_RUNNING_READ_TIMEOUT = RETROFIT_LONG_READING_TIMEOUT;
        this.RETROFIT_DEFAULT_READ_TIMEOUT = 200L;

        this.endpointPropertyMap = serviceUtil.groupsEndpoints(restEndpoints);
        this.okHttpClient = defaultSetup();
        this.jacksonConverterFactory = JacksonConverterFactory.create();
    }

    private OkHttpClient defaultSetup() {
        return new OkHttpClient.Builder()
                .readTimeout(RETROFIT_DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(20L, TimeUnit.SECONDS)
                .addInterceptor(new RequestLoggerInterceptor())
                .addInterceptor(new CorrelationIdHeaderInterceptor())
                .build();
    }
   @Bean
    public WeatherAPIClientService weatherAPIClientService() {
       return new Retrofit.Builder().client(okHttpClient)
               .baseUrl(endpointPropertyMap.get("currentWeather").getBaseUrl())
               .addConverterFactory(jacksonConverterFactory)
               .build().create(WeatherAPIClientService.class);
   }
   @Bean
    public IP_APIClientService ip_apiClientService() {
       return new Retrofit.Builder().client(okHttpClient)
               .baseUrl(endpointPropertyMap.get("ipApi").getBaseUrl())
               .addConverterFactory(jacksonConverterFactory)
               .build().create(IP_APIClientService.class);
   }
}
