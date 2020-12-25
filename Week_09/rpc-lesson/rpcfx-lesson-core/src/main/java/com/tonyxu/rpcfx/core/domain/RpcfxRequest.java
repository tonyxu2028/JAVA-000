package com.tonyxu.rpcfx.core.domain;

import lombok.Data;

@Data
public class RpcfxRequest {

    private String serviceClass;

    private String method;

    private Object[] params;

}
