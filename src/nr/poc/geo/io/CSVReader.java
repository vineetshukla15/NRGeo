package nr.poc.geo.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

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
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] csvLine = line.split(cvsSplitBy);
				latlong = csvLine[2] + "," + csvLine[3];
				URL = URL + latlong;
				geoURLList.add(URL);
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
}
