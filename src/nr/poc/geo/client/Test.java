package nr.poc.geo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import nr.poc.geo.io.CSVUtil;

public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {

		String inputCSVFile = "E://NextTag//DeviceHomeLocations.csv";
		String outPutCSVFile = "E://NextTag//DeviceHomeLocationsNew.csv";

		List<String> urlList = CSVUtil.readOpenCSV(inputCSVFile); // Full file
																	// path
		System.out.println(urlList.get(0));
		System.out.println(urlList.get(1));
		System.out.println(urlList.get(2));
		System.out.println(urlList.get(3));
		System.out.println(urlList.get(4));
		System.out.println(urlList.get(5));

		List<String> addressList = new ArrayList<String>();

		addressList.add("Address");
		int count = 0;
		for (String url : urlList) {
			addressList.add(sendGeoApiRequest(url));
		}
		CSVUtil.writeNewCSVFile(inputCSVFile, outPutCSVFile, addressList);
	}

	private static String sendGeoApiRequest(String URL) throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer jb = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			jb.append(line);
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jb.toString());
		JSONArray jsonObject1 = (JSONArray) jsonObject.get("results");
		JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
		// JSONObject jsonObject3 =
		// (JSONObject)jsonObject2.get("formatted_address");
		String address = jsonObject2.get("formatted_address").toString();
		System.out.println("The formatted Address id-->" + address);
		return address;
	}
}
