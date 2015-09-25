package com.gaoxin.toy4j.framework.helper;

import com.gaoxin.toy4j.framework.annotation.Aspect;
import com.gaoxin.toy4j.framework.proxy.AspectProxy;
import com.gaoxin.toy4j.framework.proxy.Proxy;
import com.gaoxin.toy4j.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author gaoxin.wei
 */
public class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            // 1. 获取所有带有Aspect的类
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            // 2. 获取Aspect注解中的注解，并获取所有带此注解（目标类）的类 Map<Class<?>, Class>
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            // 3. 目标类，代理类
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop helper load error", e);
            throw new RuntimeException(e);
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> result = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> classSetEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = classSetEntry.getKey();
            Set<Class<?>> targetClassSet = classSetEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                List<Proxy> proxyList = result.get(targetClass);
                if (proxyList == null) {
                    proxyList = new ArrayList<Proxy>();
                    result.put(targetClass, proxyList);
                }
                proxyList.add((Proxy)proxyClass.newInstance());
            }
        }
        return result;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> result = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxySet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> aClass : proxySet) {
            if (aClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = aClass.getAnnotation(Aspect.class);
                Class<? extends Annotation> annotation = aspect.value();
                Set<Class<?>> targetSet = new HashSet<Class<?>>();
                if (annotation != null && !annotation.equals(Aspect.class)) {
                    targetSet = ClassHelper.getClassSetByAnnotation(annotation);
                }
                result.put(aClass, targetSet);
            }
        }
        return result;
    }

}
