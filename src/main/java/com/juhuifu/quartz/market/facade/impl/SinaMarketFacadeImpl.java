package com.juhuifu.quartz.market.facade.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.market.facade.SinaMarketFacade;

@Service("sinaMarketFacade")
public class SinaMarketFacadeImpl implements SinaMarketFacade {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

   /* @Autowired
    private SinaMarketService sinaMarketService;*/

    @Override
    public void transferMarket(Date date) throws BusinessException {
        logger.debug("获取sina行情数据定时任务开始==========");
        try {
            //sinaMarketService.transferMarket(date);
        	logger.debug("--------------------执行定时任务功能-----------------");
        } catch (Exception e) {
            throw new BusinessException(e.getLocalizedMessage());
        }

    }
}
