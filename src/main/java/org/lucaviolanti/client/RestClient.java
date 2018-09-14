package org.lucaviolanti.client;

import java.util.Map;

public interface RestClient {
    String doPost(final String uri, final Map<String, String> headers, final String jsonBody);
}
