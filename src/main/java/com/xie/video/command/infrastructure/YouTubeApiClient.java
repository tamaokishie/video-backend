package com.xie.video.command.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.xie.video.command.presentation.dto.YouTubeVideoDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;


@Component
public class YouTubeApiClient {

    public List<YouTubeVideoDto> fetchPrivateVideos(String playlistId, String accessToken) {
        String url = UriComponentsBuilder
                .fromUriString("https://www.googleapis.com/youtube/v3/playlistItems")
                .queryParam("part", "snippet")
                .queryParam("maxResults", "50")
                .queryParam("playlistId", playlistId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        return items.stream()
                .map(item -> {
                    Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                    String videoId = (String) ((Map<String, Object>) snippet.get("resourceId")).get("videoId");
                    String title = (String) snippet.get("title");
                    return new YouTubeVideoDto(videoId, title);
                })
                .collect(Collectors.toList());
    }
}
