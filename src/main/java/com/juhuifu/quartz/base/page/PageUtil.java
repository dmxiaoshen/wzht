package com.juhuifu.quartz.base.page;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;


public class PageUtil {
    private static final int PAGE = 1;
    /**
     * default
     */
    public static final int PAGESIZE = 20;

    /**
     * 获取当前页
     * 
     * @param p
     *            {@link Page}
     * @return 当前页
     */
    public static int getPage(Page p) {
        int page = p.getPage();
        return page < PAGE ? PAGE : page;
    }

    /**
     * 每页包含的记录数
     * 
     * @param p
     *            {@link Page}
     * @return 每页包含的记录数
     */
    public static int getPageSize(Page p) {
        int pageSize = p.getRows();
        return pageSize == 0 ? PAGESIZE : pageSize;
    }

    /**
     * 处理输入的页码大于总页数
     * 
     * @param page
     *            当前页
     * @param pageSize
     *            每页包含的记录数
     * @param record
     *            记录总数
     * @return 处理后的页码
     */
    public static int validatePage(int record, int page, int pageSize) {
        if (record == 0) {
            return 1;
        }
        int total = (int) Math.ceil((double) record / (double) pageSize);
        return total > page ? page : total;
    }

    /**
     * 获取查询数据库的起始记录
     * 
     * @param page
     *            当前页
     * @param pageSize
     *            每页的记录数
     * @return 起始记录
     */
    public static int getStart(int page, int pageSize) {
        return (page - 1) * pageSize;
    }

    /**
     * 获取属性的排序
     * 
     * @param p
     *            {@link Page}
     * @return 排序对象
     */
    public static Order getOrder(Page p) {
        String property = p.getSidx();
        if (StringUtils.isBlank(property)) {
            return null;
        }
        if ("asc".equalsIgnoreCase(p.getSord())) {
            return Order.asc(property);
        } else {
            return Order.desc(property);
        }
    }
}
