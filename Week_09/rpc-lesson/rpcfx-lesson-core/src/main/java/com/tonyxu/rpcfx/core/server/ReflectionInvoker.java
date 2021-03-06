package com.tonyxu.rpcfx.core.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tonyxu.rpcfx.core.domain.RpcfxRequest;
import com.tonyxu.rpcfx.core.domain.RpcfxResponse;
import com.tonyxu.rpcfx.core.exception.RpcfxException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionInvoker implements RpcfxInvoker {

    private final RpcfxResolver resolver;

    public ReflectionInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        Object service = resolver.resolve(serviceClass);
        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {
            response.setException(new RpcfxException(e));
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
