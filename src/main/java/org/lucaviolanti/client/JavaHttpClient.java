package org.lucaviolanti.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class JavaHttpClient implements RestClient {

    @Override
    public String doPost(String uri, Map<String, String> headers, String jsonBody) {
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            headers.forEach(conn::setRequestProperty);
            OutputStream os = conn.getOutputStream();
            os.write(jsonBody.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            return "Response: " + response;
        } catch (Exception e) {
            return "Exception during call " + e.getMessage();
        }
    }
}
