系统作业实现说明简介：

1）两个系统，nio02是gateway网关的角色，netty-server是后端服务的角色。

2）调用关系nio02--->netty-server

3)  网关采取了两种客户端制式并存，httpClient和nettyClient,切换方式为对

```
GateWayServerApplication
```

main的时候传递参数args[0]，参数为GatewayConstants中的PROXY_CURRENT_HTTP_CLIENT_TYPE 或者 PROXY_CURRENT_NETTY_CLIENT_TYPE的内容

前者为HttpClient制式，后者为NettyClient制式

4) 网关配置参数详见GatewayConstants

5）路由器详见router包，LoadRouteConfig为加载器，HttpEndpointRouterHandler为实现类

路由方式采取随机获取数组下标的方式，数组为加载器加载的后端URL集合，分别为配置端口号为（8808，8809，8810）的实际URL

6）过滤器HttpRequestFilterHandler为实现类

7）后端服务netty-server，启动NettyServerApplication，需要传入arg[0]代表端口号，配置参数为前文提到的（8808，8809，8810）





作业结果调试反馈简介如下：

HttpClient制式下为：（页面显示，其中 Nio=LeoXu为过滤器注入的信息，Nio和LeoXu均在配置GatewayConstants中）

8809响应显示：

 hello ::: [Connection=Keep-Alive, Nio=LeoXu, Host=127.0.0.1:8809, User-Agent=Apache-HttpAsyncClient/4.1.4 (Java/1.8.0_241), content-length=0]

8810响应显示：

 hello ::: [Connection=Keep-Alive, Nio=LeoXu, Host=127.0.0.1:8810, User-Agent=Apache-HttpAsyncClient/4.1.4 (Java/1.8.0_241), content-length=0]

8808响应显示：

hello ::: [Connection=Keep-Alive, Nio=LeoXu, Host=127.0.0.1:8808, User-Agent=Apache-HttpAsyncClient/4.1.4 (Java/1.8.0_241), content-length=0]



NettyClient制式下为：（命令行显示，其中 Nio=LeoXu为过滤器注入的信息，Nio和LeoXu均在配置GatewayConstants中）

8810响应显示：

Client is active !
CONTENT_TYPE:DefaultHttpResponse(decodeResult: success, version: HTTP/1.1)
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 72
connection: keep-alive
 hello ::: [Connection=keep-alive, Content-Length=5, Nio=LeoXu ::: 8810]

8809响应显示：

Client is active !
CONTENT_TYPE:DefaultHttpResponse(decodeResult: success, version: HTTP/1.1)
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 72
connection: keep-alive
 hello ::: [Connection=keep-alive, Content-Length=5, Nio=LeoXu ::: 8809]

8808响应显示：

 Client is active !
CONTENT_TYPE:DefaultHttpResponse(decodeResult: success, version: HTTP/1.1)
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 72
connection: keep-alive
 hello ::: [Connection=keep-alive, Content-Length=5, Nio=LeoXu ::: 8808]



