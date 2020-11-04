package io.github.kimmking.gateway.router;

import java.util.ArrayList;
import java.util.List;

import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_URL_8808;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_URL_8809;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_URL_8810;

/**
 * 路由器配置信息加载器
 * Created on 2020/11/3.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class LoadRouteConfig {

    public static List<String> getRouteUrls(){
        List<String> routeUrls = new ArrayList<>();
        routeUrls.add(PROXY_SERVER_URL_8808);
        routeUrls.add(PROXY_SERVER_URL_8809);
        routeUrls.add(PROXY_SERVER_URL_8810);
        return routeUrls;
    }
}
