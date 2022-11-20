package com.dpf.forex.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhoubin
 * @since 2022-04-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Amount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private String code;

    /**
     * 日期
     */
    private Date date;

    /**
     * 盈亏
     */
    private float amount;

    /**
     * 品种
     */
    private String variety; ;

    /**
     * 止盈
     */
    private float stopProfit;

    /**
     * 止损
     */
    private float stopLoss;

    /**
     * 交易手数
     */
    private float number;

    /**
     * 交易方向
     */
    private short direction;

    /**
     * 开仓时间
     */
    private Date openTime;

    /**
     * 平仓时间
     */
    private Date closeTime;
}
