package com.juhuifu.quartz.base.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juhuifu.quartz.base.dao.BaseHibernateDAO;

public abstract class ServiceSupport {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected BaseHibernateDAO baseHibernateDAO;

    @Resource
    public void setBaseHibernateDAO(BaseHibernateDAO baseHibernateDAO) {
        this.baseHibernateDAO = baseHibernateDAO;
    }
}
