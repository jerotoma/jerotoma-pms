package com.jerotoma.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
	
	public static String getFileContents(String pathToFileName) throws IOException {
		String line = null;
		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedReader = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {				
			InputStream inputStream =  classLoader.getResourceAsStream(pathToFileName);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
	        
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bufferedReader != null ) {
				bufferedReader.close();
			}
		}
		return builder.toString();		
	}
}
