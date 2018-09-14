package org.lucaviolanti.client;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class SpringRestTemplate4x implements RestClient {

    @Override
    public String doPost(final String uri, final Map<String, String> headers, final String jsonBody) {
        try {
            final MultiValueMap<String, String> allHeaders = new LinkedMultiValueMap<>();
            allHeaders.setAll(headers);

            return "Response: " + new RestTemplate().postForEntity(uri, new HttpEntity<>(jsonBody, allHeaders), String.class).getStatusCode();
        } catch (Exception e) {
            return "Exception during call " + e.getMessage();
        }
    }
}
