package io.github.kimmking.gateway.router;

import java.util.List;

/**
 * 路由器实现类
 *
 * Created on 2020/11/2.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class HttpEndpointRouterHandler implements HttpEndpointRouter{

    private static HttpEndpointRouterHandler handler = null;

    private HttpEndpointRouterHandler(){

    }

    /**
     * 获取路由结果URL
     * @param endpoints
     * @return
     */
    @Override
    public String route(List<String> endpoints) {
        if(endpoints!=null&&!endpoints.isEmpty()) {
            return endpoints.get(getEndpointsIndex(endpoints.size()));
        }
        return null;
    }

    /**
     * 路由算法
     * (数据类型)(最小值+Math.random()*(最大值-最小值+1))
     * @param size
     * @return
     */
    public int getEndpointsIndex(int size){
        int length = size-1;
        return (int)(0+Math.random()*(length-0+1));
    }

    /**
     * 获取路由器单例
     * @return
     */
    public static HttpEndpointRouterHandler getInstance(){
        if(handler==null){
            handler = new HttpEndpointRouterHandler();
        }
        return handler;
    }

}
