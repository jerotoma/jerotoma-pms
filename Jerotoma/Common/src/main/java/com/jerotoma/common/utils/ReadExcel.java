package com.jerotoma.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jerotoma.common.models.students.Student;

public class ReadExcel {
	private FileInputStream fileInputStream = null;
	private Workbook workbook;
	
	public ReadExcel(String filePath) throws IOException {
		fileInputStream = readFile(filePath);
		workbook = new XSSFWorkbook(fileInputStream);
	}
	
	protected FileInputStream readFile(String filePath) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();		
		try {
			File file = new File(classLoader.getResource(filePath).getFile());			
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 	
		return fileInputStream;		
	}
	
	public List<Student> loadStudentsFrom(int sheetIndex) throws IOException {	
		List<Student> studentList = new ArrayList<>();
		if (fileInputStream!= null && workbook != null) {			
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			for (Row row: sheet) {
				Student student = new Student();
				for (Cell cell: row) {
					 switch (cell.getCellType()) {
			         case STRING:
			        	 
			        	 if (cell.getColumnIndex() == 1) {
			        		 student.setFirstName(cell.getStringCellValue()); 
			        	 }
			        	 if (cell.getColumnIndex() == 2) {
			        		 student.setMiddleNames(cell.getStringCellValue()); 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 3) {
			        		 student.setLastName(cell.getStringCellValue()); 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 4) {
			        		 student.setGender(cell.getStringCellValue()); 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 5) {
			        		 if (DateUtil.isCellDateFormatted(cell)) {
			        			 student.setBirthDate(cell.getDateCellValue()); 
			        		 } else {
			        			 student.setBirthDate(CalendarUtil.convertStringToDate(cell.getStringCellValue()));  
			        		 } 
			        		 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 6) {
			        		 student.setPhoneNumber(cell.getStringCellValue()); 
			        	 }
			        	 if (cell.getColumnIndex() == 7) {
			        		 student.setEmailAddress(cell.getRichStringCellValue().toString()); 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 8) {
			        		 student.setOccupation(cell.getRichStringCellValue().toString()); 
			        	 }
			        	 
			        	 if (cell.getColumnIndex() == 9) {
			        		 student.setPicture(cell.getRichStringCellValue().toString()); 
			        	 }
			        	 
			        	 break;
			         case NUMERIC:
			        	 if (cell.getColumnIndex() == 5) {
			        		 if (DateUtil.isCellDateFormatted(cell)) {
			        			 student.setBirthDate(cell.getDateCellValue()); 
			        		 } else {
			        			 student.setBirthDate(CalendarUtil.convertStringToDate(cell.getStringCellValue()));  
			        		 }
			        		 
			        	 }
			        	 break;
			         case BOOLEAN:
			        	 cell.getBooleanCellValue();
			        	break;
			         case FORMULA:
			        	break;
			         case BLANK:
				        break;
			         case _NONE:
					    break;
			         default: 
			            break;
			        }
				}
				studentList.add(student);
			}
			
			workbook.close();
			fileInputStream.close();
		}		
		return studentList;
	}

}
