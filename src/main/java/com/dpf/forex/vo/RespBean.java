package com.dpf.forex.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
// 全参构造
@AllArgsConstructor
// 无参构造
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object object;

    //成功返回结果
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessge(),null);
    }

    public static RespBean success(Object o){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessge(),o);
    }

    //失败 成功只有一种200 失败有500 404
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessge(),null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum,Object o){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessge(),o);
    }

}
