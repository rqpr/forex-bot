package com.dpf.forex.mapper;


import com.dpf.forex.pojo.Amount;

import java.util.List;

public interface AmountMapper {
    List<Amount> findAmount();

    // 批量插入
    void insertAmount(List<Amount> amountList);
}
