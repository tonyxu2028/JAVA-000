package io.github.kimmking.gateway.router;

import java.util.List;

/**
 * 路由器接口
 */
public interface HttpEndpointRouter {
    
    String route(List<String> endpoints);
    
}
