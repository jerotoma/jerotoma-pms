package com.jerotoma.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentVO;

public class ExcelUtility {
	
	private static ReadExcel readExcel = null;
	
	public static List<StudentVO> readStudent(String excelFilePath, int index) {
		ReadExcel readExcel = getReadExcelInstance(excelFilePath);
		try {
			return readExcel.loadStudentsFrom(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static List<StudentVO> readStudent(InputStream inputStream, int index) {
		ReadExcel readExcel = getReadExcelInstance(inputStream);
		try {
			return readExcel.loadStudentsFrom(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	private static ReadExcel getReadExcelInstance(String excelFilePath) {
		if (readExcel == null) {
			try {
				readExcel = new ReadExcel(excelFilePath);
			} catch (IOException e) {
				e.printStackTrace();
				readExcel = null;
			}
		}
		return readExcel;
	}
	
	private static ReadExcel getReadExcelInstance(InputStream inputStream) {
		if (readExcel == null) {
			try {
				readExcel = new ReadExcel(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
				readExcel = null;
			}
		}
		return readExcel;
	}
}
