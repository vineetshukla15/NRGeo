package nr.poc.geo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import nr.poc.geo.io.CSVUtil;

public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Full path of CSV input file:");
		String inputCSVFile = sc.next();

		System.out.println("Enter Full path of new CSV output file with Address field:");
		String outPutCSVFile = sc.next();

		System.out.println("Enter Google GeoCoding APIKey:");
		String apiKey = sc.next();

		List<String> urlList = CSVUtil.readOpenCSV(inputCSVFile);
		int totalRecords = urlList.size();

		List<String> addressList = new ArrayList<String>();

		addressList.add("Address");// Build new CSV file's header
		
		for (String url : urlList) {
			addressList.add(sendGeoApiRequest(url + "&key=" + apiKey));
		}
		if (outPutCSVFile != null) {
			int processedCount = CSVUtil.writeNewCSVFile(inputCSVFile, outPutCSVFile, addressList);
			if (totalRecords == processedCount - 1) {
				System.out.println("*********New CSV File Generated Successfully*********");
			} else {
				System.out
						.println("Oops!!! Something went wrong, may be with record no# " + processedCount + " onwards");
			}
		}
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
		return address;
	}
}
