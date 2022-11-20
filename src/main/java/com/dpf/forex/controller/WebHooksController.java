package com.dpf.forex.controller;

import com.dpf.forex.vo.Strategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/webhook")
public class WebHooksController {

//    BarUpDn 策略(BarUpDn Strategy) (1)订单{{strategy.order.action}}@{{strategy.order.contracts}}成交{{ticker}}。新策略仓位{{strategy.position_size}}
    //TradingView webhook
    @RequestMapping(value = "/strategy", method = RequestMethod.POST)
    public void webhook(@RequestBody Strategy strategy) {
        // 根据品种,策略划分不同的策略组

    }
}
