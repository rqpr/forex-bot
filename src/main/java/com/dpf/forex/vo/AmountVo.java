package com.dpf.forex.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AmountVo {
    private List<Date> dateList;
    private List<Float> amountList;

    private List<Date> dateList1;
    private List<Float> amountList1;

    public AmountVo(List<Date> dateList, List<Float> amountList, List<Date> dateList1, List<Float> amountList1) {
        this.dateList = dateList;
        this.amountList = amountList;
        this.dateList1 = dateList1;
        this.amountList1 = amountList1;
    }
}
