package com.tonyxu.rpcfx.core.server;

/**
 * 服务端服务解析器
 */
public interface RpcfxResolver {

    Object resolve(String serviceClass);

}
