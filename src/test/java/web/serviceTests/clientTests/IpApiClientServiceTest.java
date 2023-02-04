package web.serviceTests.clientTests;

import web.config.RestServiceForRetrofit;
import web.config.RetrofitConfig;
import web.config.RetrofitProperties;
import web.service.clientService.IP_APIClientService;
import web.service.serviceImpl.ServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceUtil.class, RestServiceForRetrofit.class, RetrofitProperties.class, RetrofitConfig.class})
@SpringBootTest
public class IpApiClientServiceTest {

    @Autowired
    IP_APIClientService ip_apiClientService;

    @BeforeEach
    public void createClient(){
        assertNotNull(this.ip_apiClientService);
    }

    @Test
    public void ipClientTest(){
        String ipAddressExample = "24.48.0.1";
        assertNotNull(this.ip_apiClientService.getUserLocation(ipAddressExample));
        assertFalse(this.ip_apiClientService.getUserLocation(ipAddressExample).join().getCity().isEmpty());
    }
}
