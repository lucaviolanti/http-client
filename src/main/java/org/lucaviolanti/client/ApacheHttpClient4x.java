package org.lucaviolanti.client;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Map;

public class ApacheHttpClient4x implements RestClient {

    @Override
    public String doPost(final String uri, final Map<String, String> headers, final String jsonBody) {
        try {
            final CloseableHttpClient client = HttpClientBuilder.create().disableAutomaticRetries().build();
            final HttpPost httpPost = new HttpPost(uri);
            headers.forEach(httpPost::addHeader);
            httpPost.setEntity(new StringEntity(jsonBody));

            final int statusCode = client.execute(httpPost).getStatusLine().getStatusCode();
            client.close();
            return "Response: " + statusCode;
        } catch (Exception e) {
            return "Exception during call " + e.getMessage();
        }
    }
}
