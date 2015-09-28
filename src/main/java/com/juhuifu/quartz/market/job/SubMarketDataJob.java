package com.juhuifu.quartz.market.job;

import java.util.Date;

import org.quartz.SchedulerContext;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.common.job.AbstractJob;
import com.juhuifu.quartz.market.facade.SinaMarketFacade;

public class SubMarketDataJob extends AbstractJob {
    private SinaMarketFacade sinaMarketFacade;

    @Override
    protected void execute(SchedulerContext ctx, Date date) throws BusinessException {
        if (sinaMarketFacade == null) {
        	sinaMarketFacade = (SinaMarketFacade) ctx.get("sinaMarketFacade");
        }
        if (date == null) {
            date = new Date();
        }

        sinaMarketFacade.transferMarket(date);

    }

}
