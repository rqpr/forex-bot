package com.dpf.forex.controller;


import com.alibaba.fastjson.JSONObject;
import com.dpf.forex.pojo.Amount;
import com.dpf.forex.service.AmountServices;
import com.dpf.forex.utils.RedisUtil;
import com.dpf.forex.vo.AmountVo;
import com.dpf.forex.vo.RedisKey;
import com.dpf.forex.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/amount")
public class AmountController {

    @Autowired
    private AmountServices amountServices;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Author 1
     * @Version  1.0
     * @Description
     * @param amountVo
     * @Date 2022-11-20 10:45
     */
    @RequestMapping(value = "/amountline", method = RequestMethod.GET)
    @ResponseBody
    public RespBean test(){
        List<Amount> amountList = new ArrayList<>();

        if (redisUtil.hasKey(RedisKey.AMOUNT)){
            Map<Object, Object> objectObjectMap = redisUtil.hgetAll(RedisKey.AMOUNT);
            for (Map.Entry<Object, Object> entry : objectObjectMap.entrySet()) {
                amountList.add(JSONObject.parseObject(entry.getValue().toString(), Amount.class));
            }
        } else {
            amountList = amountServices.findAmount();
            for (Amount amount : amountList) {
                redisUtil.hput(RedisKey.AMOUNT,amount.getCode(),amount);
            }
        }

        float total = 0;
        int time = 0;
        // 胜
        List<Float> win = new ArrayList<>();
        float winmoney = 0;
        // 负
        List<Float> lose = new ArrayList<>();
        float losemoney = 0;

        List<Date> dateList = new ArrayList<>();
        List<Float> amount = new ArrayList<>();

        for (Amount amount1 : amountList) {
            // 总盈亏
            total += amount1.getAmount();

            dateList.add(amount1.getDate());
            amount.add(amount1.getAmount());

            if(amount1.getAmount() > 0) {
                time += 1;
                winmoney += amount1.getAmount();
                win.add(amount1.getAmount());
            } else if (amount1.getAmount() < 0) {
                time += 1;
                losemoney += amount1.getAmount();
                lose.add(amount1.getAmount());
            } else if (amount1.getAmount() == 0) {
                continue;
            }
        }

        {
            // 胜率 = 胜/总 * 100%
            float winrate = (float) win.size() / time;
            // 盈亏比 平均盈利/平均亏损
            float profit = (winmoney / win.size()) / (losemoney / lose.size());
            // 总盈亏 = 盈利所有次数 * 平均盈利 - 亏损所有次数 * 平均亏损
            float total_profit = (float) win.size() * (winmoney / win.size()) - lose.size() * (losemoney / lose.size());
            // 凯利公式  胜率 - （1-p胜率）/r盈亏 = q投注占总投资比例
            float q = winrate - (1 - winrate) / profit;
        }

        // 资金曲线

        // 账户净值曲线
        List<Date> dateList1 = new ArrayList<>();
        List<Float> amountList2 = new ArrayList<>();
        if (amountList.size() > 0) {
            amountList2.add(amount.get(0));
            dateList1.add(dateList.get(0));
        }
        float amount1 = amount.get(0);
        for (int i = 1; i < dateList.size() - 2; i++) {
            amount1 += amount.get(i);
            if (dateList1.get(dateList1.size()-1).equals(dateList.get(i))) {
                amountList2.set(amountList2.size()-1, amount1);
            } else {
                dateList1.add(dateList.get(i));
                amountList2.add(amount1);
            }
        }

        // 每笔止损在1%
        for (int i = 0; i < amountList.size(); i++) {
            if (amount.get(i) < -1) {
                amount.set(i, (float)-1);
            }
        }
        // 账户净值曲线
        List<Date> dateList2 = new ArrayList<>();
        List<Float> amountList3 = new ArrayList<>();
        if (amountList.size() > 0) {
            amountList3.add(amount.get(0));
            dateList2.add(dateList.get(0));
        }
        float amount2 = amount.get(0);
        for (int i = 1; i < dateList.size() - 2; i++) {
            amount2 += amount.get(i);
            if (dateList2.get(dateList2.size()-1).equals(dateList.get(i))) {
                amountList3.set(amountList3.size()-1, amount2);
            } else {
                dateList2.add(dateList.get(i));
                amountList3.add(amount2);
            }
        }
        return RespBean.success(new AmountVo(dateList1, amountList2, dateList2, amountList3));
    }

    // 统计止损单
    public void stoploss(List<Amount> amount) {
        int time = 0;
        for (Amount amount1 : amount) {
            if (amount1.getAmount() < -1) {
                time += 1;
            }
        }
        System.out.println("止损单数：" + time);
    }


//    // 统计品种偏好
//    public void variety(List<Amount> amount) {
//        String[] variety;
//        for (Amount amount1 : amount) {
//            if (amount1.getAmount() < -1) {
//                time += 1;
//            }
//        }
//        System.out.println("止损单数：" + time);
//    }
}
