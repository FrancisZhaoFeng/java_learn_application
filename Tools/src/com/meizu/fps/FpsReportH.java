package com.meizu.fps;

import java.io.FileOutputStream;
import java.io.OutputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.meizu.filemanage.ExcelHandle;

public class FpsReportH {
	public void writeExcel(String writeExcel, Sheet sheet) {
		OutputStream os;
		String strFlag = "";
		int flag = 0;
		try {
			os = new FileOutputStream(writeExcel);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			WritableCellFormat titleFormate = new WritableCellFormat();// 生成一个单元格样式控制对象
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);// 单元格中的内容水平方向居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直方向居中
			titleFormate.setWrap(true);
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell[] cell = sheet.getColumn(i);
				flag = 0;
				strFlag = cell[0].getContents();
				for (int j = 0; j < sheet.getRows() - 1; j++) {
					if (i > 3) {
						ws.addCell(new Number(i, j, Double.parseDouble(strFlag), titleFormate));
					} else if (!strFlag.equals(cell[j + 1].getContents()) || j + 1 == sheet.getRows() - 1) {
						ws.mergeCells(i, flag, i, j);
						ws.addCell(new Label(i, flag, strFlag, titleFormate));
						flag = j + 1;
					}
					strFlag = cell[j + 1].getContents();
				}
			}
			wwb.write();
			wwb.close();
			System.out.println("成功生成excel文件：" + writeExcel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filepath = "E:\\fps-M76";
		String strReadExcel = filepath + ".xls";
		String strWriteExcel = filepath + "_done.xls";
		Sheet sheet = ExcelHandle.readExcel(strReadExcel, 0);
		new FpsReportH().writeExcel(strWriteExcel, sheet);
	}
}
