package com.dpf.forex.controller;

import com.dpf.forex.pojo.Amount;
import com.dpf.forex.service.AmountServices;
import com.dpf.forex.utils.DateFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class FileUploadController {

    @Autowired
    private AmountServices amountServices;

    @PostMapping("/upload")
    public List<Amount> upload(@RequestParam(value = "file")MultipartFile uploadFile, HttpServletRequest req) throws IOException, ParseException {

        InputStream inputStream = uploadFile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Amount> amountArrayList = new ArrayList<>();
        List<CSVRecord> recordList= parser.getRecords();
        for (int i = 1; i < recordList.size(); i++) {
            CSVRecord record = recordList.get(i);
            if (!record.get(13).equals("")) {
                if (record.get(13).charAt(0) == '>' || record.get(13).equals("cancelled")) {
                    continue;
                }
            }
            Amount amount = new Amount();
            amount.setCode(record.get(0));
            amount.setDate(sdf.parse(DateFormat.format(record.get(1))));
            amount.setAmount(Float.parseFloat(record.get(12)));
// 订单号0	 开仓时间1	 类型2	 手数3	 交易品种4	 开仓价格5	止损6	 止盈7	 平仓时间8	 平仓价格9	 手续费10	 库存费11
            amount.setOpenTime(sdf1.parse(DateFormat.format(record.get(1))));
            amount.setNumber(Float.parseFloat(record.get(3)));
            amount.setVariety(record.get(4));
            amount.setStopLoss(Float.parseFloat(record.get(6)));
            amount.setStopProfit(Float.parseFloat(record.get(7)));
            amount.setCloseTime(sdf1.parse(DateFormat.format(record.get(8))));

            if (record.get(6).equals("buy")) {
                amount.setDirection((short) 1);
            } else {
                amount.setDirection((short) 2);
            }

            amountArrayList.add(amount);
        }
        // 反转
        Collections.reverse(amountArrayList);
        amountServices.insertAmount(amountArrayList);
        return amountArrayList;
    }

}