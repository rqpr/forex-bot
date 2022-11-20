package com.dpf.forex.service;

import com.dpf.forex.pojo.Amount;

import java.util.List;
public interface AmountServices {
    List<Amount> findAmount();

    // 批量插入
    void insertAmount(List<Amount> amountList);
}
