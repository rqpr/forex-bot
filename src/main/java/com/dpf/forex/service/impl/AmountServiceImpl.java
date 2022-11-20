package com.dpf.forex.service.impl;

import com.dpf.forex.mapper.AmountMapper;
import com.dpf.forex.pojo.Amount;
import com.dpf.forex.service.AmountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmountServiceImpl implements AmountServices {

    @Autowired
    private AmountMapper amountMapper;

    @Override
    public List<Amount> findAmount() {
        return amountMapper.findAmount();
    }

    @Override
    public void insertAmount(List<Amount> amountList) {
        amountMapper.insertAmount(amountList);
    }
}
