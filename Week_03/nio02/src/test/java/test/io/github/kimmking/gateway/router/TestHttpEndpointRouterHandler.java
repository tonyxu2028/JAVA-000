package test.io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.router.HttpEndpointRouterHandler;

/**
 * Created on 2020/11/3.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class TestHttpEndpointRouterHandler {

    public static void main(String[] args) throws Exception {
        HttpEndpointRouterHandler handler = HttpEndpointRouterHandler.getInstance();
        int size = 3;
        for(int i = 0 ; i < 100000 ; i++) {
          int index = handler.getEndpointsIndex(size-1);
          if(index>(size-1)){
              throw new Exception("fail");
          }
          else {
              System.out.println(index);
          }
        }
    }
}
