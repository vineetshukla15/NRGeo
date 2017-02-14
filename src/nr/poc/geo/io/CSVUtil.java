package nr.poc.geo.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class CSVUtil {

	public static List<String> readCSV(String csvFile) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String latlong = null;
		String longitude = null;
		String URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		// 40.714224,-73.961452
		List<String> geoURLList = new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int count = 0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				if (count > 0) { //Skip header
					String[] csvLine = line.split(cvsSplitBy);
					latlong = csvLine[2] + "," + csvLine[3];				
					geoURLList.add(URL + latlong);
				}else{
					
				}
				count++;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return geoURLList;
	}
	
	
	public static void writeNewCSVFile(String inputFilePath,String outPutFilePath,List columnList) throws IOException  //assuming data is of type ArrayList here, you need to be more explicit when posting code
	{
		CSVReader reader = new CSVReader(new FileReader(inputFilePath));
		CSVWriter writer = new CSVWriter(new FileWriter(outPutFilePath), ',');
		String[] entries = null;
		int count =0;
		while ((entries = reader.readNext()) != null) {
		    ArrayList list = new ArrayList(Arrays.asList(entries));	
		    if(count>4)
		    list.add("Unknown"); 
		    else{
		    	list.add(columnList.get(count));
		    }
		    writer.writeNext((String[]) list.toArray(new String[list.size()]));
		    count++;
		}
		writer.close();
	}
	
	
	public static List readOpenCSV(String inputFile) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(inputFile));
	    String [] nextLine;
	    String URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		List<String> geoURLList = new ArrayList<String>();
	    while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	    	geoURLList.add(URL+nextLine[2]+","+nextLine[3]);
	    }
	    
	    return geoURLList;
	}
}
