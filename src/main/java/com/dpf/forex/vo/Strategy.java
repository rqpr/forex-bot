package com.dpf.forex.vo;

import lombok.Data;

@Data
public class Strategy {
    private String action;
    private String symbol;
    private String price;
    private String close;
}
