// package com.xie.video.command.application;

// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.util.UriComponentsBuilder;

// @Service
// public class YouTubeService {

//     private final WebClient webClient = WebClient.create();

//     public String fetchVideos(String accessToken, String playlistId) {
//         String uri = UriComponentsBuilder
//                 .fromUriString("https://www.googleapis.com/youtube/v3/playlistItems")
//                 .queryParam("part", "snippet")
//                 .queryParam("playlistId", playlistId)
//                 .queryParam("maxResults", 50)
//                 .build()
//                 .toUriString();

//         return webClient.get()
//                 .uri(uri)
//                 .headers(headers -> headers.setBearerAuth(accessToken))
//                 .retrieve()
//                 .bodyToMono(String.class)
//                 .block(); // ※本番では非同期推奨、ここでは同期で簡単に
//     }
// }

package com.xie.video.command.application;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;


@Service
public class YouTubeService {

    public List<String> fetchVideoIds(String accessToken, String playlistId) {
        WebClient client = WebClient.builder()
            .baseUrl("https://www.googleapis.com/youtube/v3")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .build();

        Map<String, Object> response = client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/playlistItems")
                .queryParam("part", "snippet")
                .queryParam("maxResults", 50)
                .queryParam("playlistId", playlistId)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();

        if (response == null || !response.containsKey("items")) return List.of();

        return ((List<Map<String, Object>>) response.get("items")).stream()
            .map(item -> (Map<String, Object>) item.get("snippet"))
            .map(snippet -> (Map<String, Object>) snippet.get("resourceId"))
            .map(resourceId -> (String) resourceId.get("videoId"))
            .toList();
    }
}
