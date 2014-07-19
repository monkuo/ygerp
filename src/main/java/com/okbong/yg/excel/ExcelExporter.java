/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelExporter<T> {
    
    Workbook exportExcel(List<T> list);

    Workbook getWorkbook();

    void setWorkbook(Workbook workbook);
}
