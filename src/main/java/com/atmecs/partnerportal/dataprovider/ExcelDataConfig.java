package com.atmecs.partnerportal.dataprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {

	XSSFWorkbook wb;
	XSSFSheet sheet1;

	public ExcelDataConfig(String excwlPath) throws IOException {
		File src = new File(excwlPath);
		FileInputStream fis = new FileInputStream(src);

		wb = new XSSFWorkbook(fis);

	}

	public String getdata(int sheetnumber, int row, int column) {
		// System.out.println(sheetnumber);
		sheet1 = wb.getSheetAt(sheetnumber);
		String data = sheet1.getRow(row).getCell(column).getStringCellValue();
		return data;
	}

	public int getRowCount(int sheetIndex) {
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row = row + 1;
		return row;
	}
}
