package com.gaoxin.toy4j.framework.proxy;

/**
 * @author gaoxin.wei
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
