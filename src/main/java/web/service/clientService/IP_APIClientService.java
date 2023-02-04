package web.service.clientService;

import web.model.UserLocation;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface IP_APIClientService {
    @GET("{ipAddress}")
    CompletableFuture<UserLocation> getUserLocation(@Path("ipAddress") String ipAddress);
}
