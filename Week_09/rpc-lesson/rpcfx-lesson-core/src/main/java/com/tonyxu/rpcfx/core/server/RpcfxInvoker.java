package com.tonyxu.rpcfx.core.server;


import com.tonyxu.rpcfx.core.domain.RpcfxRequest;
import com.tonyxu.rpcfx.core.domain.RpcfxResponse;

/**
 * 服务端服务执行器
 */
public interface RpcfxInvoker {

    /**
     * 执行服务端服务
     *
     * @param request
     * @return rpcfxResponse
     */
    RpcfxResponse invoke(RpcfxRequest request);

}
