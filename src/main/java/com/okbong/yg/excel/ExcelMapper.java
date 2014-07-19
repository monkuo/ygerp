/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.excel;

public interface ExcelMapper {
   
   int sheetIdx();
   
   String sheetName();
   
   int startRowIdx();
   
   int headerRowIdx();
}
