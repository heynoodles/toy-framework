package com.gaoxin.toy4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * @author gaoxin.wei
 */
public abstract class AspectProxy implements Proxy {

   private static Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    private void end() {

    }

    private void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }

    private void after(Class<?> cls, Method method, Object[] params, Object result) {

    }

    private void before(Class<?> cls, Method method, Object[] params) {

    }

    private boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    private void begin() {
    }
}
