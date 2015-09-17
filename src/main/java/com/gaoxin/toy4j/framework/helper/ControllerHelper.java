package com.gaoxin.toy4j.framework.helper;

import com.gaoxin.toy4j.framework.bean.Handler;
import com.gaoxin.toy4j.framework.bean.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaoxin.wei
 */
public final class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        // todo 获取所有的controller类
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
