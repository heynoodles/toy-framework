package com.gaoxin.toy4j.framework.core;

import com.gaoxin.toy4j.framework.annotation.Action;
import com.gaoxin.toy4j.framework.bean.Handler;
import com.gaoxin.toy4j.framework.bean.Request;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gaoxin.wei
 */
public class ControllerHolder implements Initialize {

    private final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    private ControllerHolder() {}

    public static ControllerHolder INSTANCE = new ControllerHolder();

    @Override
    public void init() {
        Set<Class<?>> controllerClassSet = ClassHolder.INSTANCE.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Action.class)) {
                        Action action = method.getAnnotation(Action.class);
                        String mapping = action.value();
//                        if (mapping.matches("\\w+:/\\w*")) {
                        String[] array = mapping.split(":");
                        Request request = new Request(array[0], array[1]);
                        Handler handler = new Handler(controllerClass, method);
                        ACTION_MAP.put(request, handler);
//                        }
                    }
                }

            }
        }

    }

    public Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
