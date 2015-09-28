package com.juhuifu.quartz.market.facade;

import java.util.Date;

import com.juhuifu.quartz.base.exception.BusinessException;

public interface SinaMarketFacade {

    void transferMarket(Date date) throws BusinessException;
}
