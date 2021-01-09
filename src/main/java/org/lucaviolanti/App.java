package org.lucaviolanti;

import org.lucaviolanti.client.ApacheHttpClient4x;
import org.lucaviolanti.client.JavaHttpClient;
import org.lucaviolanti.client.RestClient;
import org.lucaviolanti.client.SpringRestTemplate4x;

import java.util.*;

public class App {

    private static final String LOCALHOST = "http://localhost:8080/userLists";
    private static final int REPETITIONS = 1000;

    public static void main(String[] args) {

        final List<RestClient> restClients = Arrays.asList(new JavaHttpClient(), new SpringRestTemplate4x(), new ApacheHttpClient4x());
        final String uri = LOCALHOST;
        final String body = createJsonBody();
        final Map<String, String> headers = getInvalidHeaders();

        for (RestClient restClient : restClients) {
            runForClient(restClient, uri, headers, body);
        }
    }

    private static void runForClient(RestClient restClient, String uri, Map<String, String> headers, String body) {
        final Map<String, Integer> responses = new HashMap<>();
        System.out.printf("Start execution for %s%n", restClient.getClass().getCanonicalName());
        for (int i = 0; i < REPETITIONS; ++i) {
            final String response = restClient.doPost(uri, headers, body);
            final Integer count = responses.getOrDefault(response, 0);
            responses.put(response, count + 1);
            if (i % (REPETITIONS / 10) == 0) {
                System.out.printf("%d calls performed%n", i);
            }
        }
        responses.forEach((response, count) -> {
            System.out.printf("Response '%s' returned %d times. Frequency: %.5f%n", response, count, ((double) count) / REPETITIONS);
        });
    }

    private static Map<String, String> getInvalidHeaders() {
        final Map<String, String> invalidHeaders = new HashMap<>();
        invalidHeaders.put("appId", "Приветé");
        invalidHeaders.put("Content-Type", "application/json");
        invalidHeaders.put("tid", "03e47cae-da17-433f-a453-3e2f07bc4fd6");
        invalidHeaders.put("originatingIp", "Приветé");
        return invalidHeaders;
    }

    private static Map<String, String> getValidHeaders() {
        final Map<String, String> invalidHeaders = new HashMap<>();
        invalidHeaders.put("appId", "test");
        invalidHeaders.put("Content-Type", "application/json");
        invalidHeaders.put("tid", "03e47cae-da17-433f-a453-3e2f07bc4fd6");
        invalidHeaders.put("originatingIp", "127.0.0.1");
        return invalidHeaders;
    }

    private static String createJsonBody() {
        final StringJoiner memberIds = new StringJoiner(",");
        for (int i = 0; i < 50000; i++) {
            String memberId = String.format("%08d", i);
            memberIds.add("\"" + memberId + "\"");
        }
        final String PREAMBLE = "{\n\t\"name\":\"Test user list\",\n\t\"users\":[\n\t\t";
        final String CLOSURE = "]\n}\n";
        return PREAMBLE + memberIds.toString() + CLOSURE;
    }
}
