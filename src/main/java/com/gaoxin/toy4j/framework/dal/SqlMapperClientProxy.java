package com.gaoxin.toy4j.framework.dal;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gaoxin.wei
 */
public class SqlMapperClientProxy {

    public static <T> T createProxy(final Class<?> targetClass) {
        return (T)Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return SqlMapperClient.INSTANCE.execute();
            }
        });
    }
}
