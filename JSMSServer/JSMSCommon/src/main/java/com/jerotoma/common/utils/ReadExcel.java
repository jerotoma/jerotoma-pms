package com.jerotoma.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jerotoma.common.viewobjects.StudentVO;

public class ReadExcel {

	private Workbook workbook;

	public ReadExcel(String filePath) throws IOException {
		this(readFile(filePath));
	}
	
	public ReadExcel(InputStream inputStream) throws IOException {
		workbook = new XSSFWorkbook(inputStream);
	}

	private static InputStream readFile(String filePath) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
	}

	public List<StudentVO> loadStudentsFrom(int sheetIndex) throws IOException {
		List<StudentVO> studentList = new ArrayList<>();

		Sheet sheet = workbook.getSheetAt(sheetIndex);
		int count = 0;
		for (Row row : sheet) {	
			StudentVO student = new StudentVO();
			int minCellIndex = row.getFirstCellNum();
			int maxCellIndex = row.getLastCellNum();
			
			if (count != 0) {
				for (int cellIndex = minCellIndex; cellIndex < maxCellIndex; cellIndex++) {
					Cell cell = row.getCell(cellIndex);
					if (cell == null) {
						continue;
					}
					setStudent(student, cell);
				}
				
				if (StringUtility.isEmpty(student.getFirstName()) 
						|| StringUtility.isEmpty(student.getLastName()) 
						|| StringUtility.isEmpty(student.getUsername())) {
					continue;
				}
				studentList.add(student);
			}
			count++;									
		}
		workbook.close();

		return studentList;
	}

	private void setStudent(StudentVO student, Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			if (cell.getColumnIndex() == 0) {
				student.setFirstName(cell.getStringCellValue());
			}
			if (cell.getColumnIndex() == 1) {
				student.setMiddleNames(cell.getStringCellValue());
			}

			if (cell.getColumnIndex() == 2) {
				student.setLastName(cell.getStringCellValue());
			}

			if (cell.getColumnIndex() == 3) {
				student.setUsername(cell.getStringCellValue());
			}

			if (cell.getColumnIndex() == 4) {
				student.setEmailAddress(cell.getRichStringCellValue().toString());
			}

			if (cell.getColumnIndex() == 5) {
				student.setGender(cell.getStringCellValue());
			}

			if (cell.getColumnIndex() == 6) {
				student.setBirthDate(CalendarUtil.convertStringToDate(cell.getStringCellValue()));
			}

			if (cell.getColumnIndex() == 7) {
				student.setPhoneNumber(cell.getStringCellValue());
			}

			if (cell.getColumnIndex() == 8) {
				student.setOccupation(cell.getRichStringCellValue().toString());
			}

			if (cell.getColumnIndex() == 9) {
				student.setPicture(cell.getRichStringCellValue().toString());
			}

			break;
		case NUMERIC:
			if (cell.getColumnIndex() == 6) {
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

}
