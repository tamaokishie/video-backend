package com.xie.video.command.application;

import com.xie.video.command.infrastructure.YouTubeApiClient;
import com.xie.video.command.presentation.dto.YouTubeVideoDto;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTubeApiClient apiClient;

    public YouTubeService(YouTubeApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<YouTubeVideoDto> getPrivateVideos(String playlistId, String accessToken) {
        return apiClient.fetchPrivateVideos(playlistId, accessToken);
    }
}
