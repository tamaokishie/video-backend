package com.xie.video.command.presentation;

import com.xie.video.command.application.YouTubeService;
import com.xie.video.command.presentation.dto.YouTubeVideoDto;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {

    private final OAuth2AuthorizedClientService clientService;
    private final YouTubeService youTubeService;

    public YouTubeController(OAuth2AuthorizedClientService clientService, YouTubeService youTubeService) {
        this.clientService = clientService;
        this.youTubeService = youTubeService;
    }

    @GetMapping("/private-videos")
    public List<YouTubeVideoDto> getPrivateVideos(
            @RequestParam String playlistId,
            OAuth2AuthenticationToken authentication
    ) {
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        if (client == null || client.getAccessToken() == null) {
            throw new RuntimeException("OAuth2 client or token is null");
        }

        String accessToken = client.getAccessToken().getTokenValue();
        return youTubeService.getPrivateVideos(playlistId, accessToken);
    }
}
