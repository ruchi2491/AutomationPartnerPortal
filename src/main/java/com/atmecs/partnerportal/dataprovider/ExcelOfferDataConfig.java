package com.atmecs.partnerportal.dataprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOfferDataConfig {

	XSSFWorkbook wb;
	XSSFSheet sheet1;

	public ExcelOfferDataConfig(String excwlPath) throws IOException {
		File src = new File(excwlPath);
		FileInputStream fis = new FileInputStream(src);

		wb = new XSSFWorkbook(fis);

	}

	public String getdata(int sheetnumber, int row, int column) {
		sheet1 = wb.getSheetAt(sheetnumber);
		DataFormatter formatter = new DataFormatter(); // creating formatter
														// using the default
														// locale
		Cell cell = sheet1.getRow(row).getCell(column);
		String data = formatter.formatCellValue(cell); // Returns the formatted
														// value of a cell as a
														// String regardless of
														// the cell type.
		return data;
	}

	public int getRowCount(int sheetIndex) {
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row = row + 1;
		return row;
	}

}
