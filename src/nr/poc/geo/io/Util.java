package nr.poc.geo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Util {

	public static String sendGeoApiRequest(String URL) throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452");
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer jb = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			jb.append(line);
		}
		System.out.println(jb.toString());
		return jb.toString();
	}

}
