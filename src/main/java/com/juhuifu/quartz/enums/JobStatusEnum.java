package com.juhuifu.quartz.enums;

import java.util.HashMap;
import java.util.Map;

import com.juhuifu.quartz.base.enums.SuperEnum;

public enum JobStatusEnum implements SuperEnum  {
    	
    Waiting("等待中"),
    Executing("执行中"),
    ExecuteFail("执行失败"),
    Executed("执行成功");
	 
   // Waiting,Executing,ExecuteFail,Executed;
    
    
    /** 只是做字面显示 */
    private String name = null;
    
    
    private JobStatusEnum(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }
	
    @Override
    public Map<String, Object> getMap() {
       Map<String,Object> map= new HashMap<String, Object>();
       map.put("key", ordinal());
       map.put("name", getName()); 
       return map;
    }
	
}
