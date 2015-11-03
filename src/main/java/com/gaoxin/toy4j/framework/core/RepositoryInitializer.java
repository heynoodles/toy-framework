package com.gaoxin.toy4j.framework.core;

import com.gaoxin.toy4j.framework.dal.SqlMapperClientProxy;

import java.util.Set;

/**
 * @author gaoxin.wei
 */
public class RepositoryInitializer implements Initialize {

    public static RepositoryInitializer INSTANCE = new RepositoryInitializer();

    private RepositoryInitializer() {}

    @Override
    public void init() {
        // 获取所有的Repository, 并代理
        Set<Class<?>> repositoryClassSet = ClassHolder.INSTANCE.getRepositoryClassSet();
        for (Class<?> cls : repositoryClassSet) {
            Object proxy = SqlMapperClientProxy.createProxy(cls);
            BeanHolder.INSTANCE.setBean(cls, proxy);
        }
    }
}
