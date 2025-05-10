package com.xie.video.command.presentation.dto;

public class YouTubeVideoDto {
    private String videoId;
    private String title;

    public YouTubeVideoDto(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }
}
