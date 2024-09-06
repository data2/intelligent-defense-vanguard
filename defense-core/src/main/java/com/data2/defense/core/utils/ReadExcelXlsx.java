package com.data2.defense.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class ReadExcelXlsx {
    private static String filePath = "classpath:portlist.xlsx";
    public static Map<Integer, String> config() {
        Map<Integer, String> map = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(ResourceUtils.getFile(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row != null) {
                    List<Integer> portList = new ArrayList<>();
                    String msg = null;
                    if (row.getCell(0) != null) {
                        String portStr = row.getCell(0).toString();
                        if (!portStr.contains("TCP")) {
                            continue;
                        }
                        if (portStr.contains("/")) {
                            portStr = portStr.split("/")[0];
                        }
                        if (portStr.contains("-")){
                            Integer start = Integer.parseInt(portStr.split("-")[0]);
                            Integer end = Integer.parseInt(portStr.split("-")[1]);
                            while (start <= end){
                                portList.add(start);
                                start += 1;
                            }
                        }else{
                            portList.add(Integer.parseInt(portStr));
                        }
                    }
                    if (row.getCell(1) != null) {
                        msg = row.getCell(1).toString();
                    }
                    log.info("端口：{}, 描述：{}", portList, msg);

                    for (Integer port : portList){
                        if (map.get(port)!= null){
                            map.put(port, msg + "||" + map.get(port));
                        }else{
                            map.put(port, msg);
                        }
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println(config());
    }
}