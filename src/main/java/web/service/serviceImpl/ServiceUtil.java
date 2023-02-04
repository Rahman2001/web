package web.service.serviceImpl;

import web.config.EndpointProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

@Service
public class ServiceUtil {
    public Map<String, EndpointProperty> groupsEndpoints(List<EndpointProperty> endpointProperties) {
        return endpointProperties.stream().collect(toImmutableMap(EndpointProperty::getServiceName, Function.identity()));
    }
}
