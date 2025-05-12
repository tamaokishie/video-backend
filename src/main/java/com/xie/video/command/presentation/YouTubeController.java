package com.xie.video.command.presentation;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.xie.video.command.presentation.dto.YouTubeVideoDto;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {

    @GetMapping("/videos")
    public List<YouTubeVideoDto> getVideos(
        @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String playlistId = "PLINGkNFSbpzdcuKVrHr2t7fgYm4FjGvbk";
        String url = "https://www.googleapis.com/youtube/v3/playlistItems"
                   + "?part=snippet&playlistId=" + playlistId + "&maxResults=50";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        return items.stream().map(item -> {
            Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
            String videoId = (String) ((Map<String, Object>) snippet.get("resourceId")).get("videoId");
            String title = (String) snippet.get("title");
            return new YouTubeVideoDto(videoId, title);
        }).collect(Collectors.toList());
    }
}
