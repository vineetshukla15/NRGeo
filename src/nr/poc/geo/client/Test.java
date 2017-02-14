package nr.poc.geo.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import nr.poc.geo.io.CSVReader;
import nr.poc.geo.io.Util;

public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {

		List<String> urlList = CSVReader.readCSV(""); // Full file path

		for (String url : urlList) {
			String jsonResponse = Util.sendGeoApiRequest(url);
			JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonResponse);

			JSONArray jsonObject1 = (JSONArray) jsonObject.get("results");
			JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
			// JSONObject jsonObject3 =
			// (JSONObject)jsonObject2.get("formatted_address");
			String address = jsonObject2.get("formatted_address").toString();

			System.out.println("The formatted Address id-->" + address);
		}
	}
}
