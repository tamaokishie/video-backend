package com.xie.video.command.presentation;

// import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
// import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.xie.video.command.application.YouTubeService;

// @RestController
// public class YouTubeController {

//     private final YouTubeService youTubeService;

//     public YouTubeController(YouTubeService youTubeService) {
//         this.youTubeService = youTubeService;
//     }

//     @GetMapping("/youtube")
//     public String fetchVideos(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
//         String accessToken = authorizedClient.getAccessToken().getTokenValue();

//         // 再生リストID（固定で良い）
//         String playlistId = "PLINGkNFSbpzdcuKVrHr2t7fgYm4FjGvbk";

//         return youTubeService.fetchVideos(accessToken, playlistId);
//     }
// }


// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.Map;

// @RestController
// public class AuthController {

//     @GetMapping("/me")
//     public Map<String, Object> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User principal) {
//         return principal.getAttributes(); // 名前やメールアドレスなど
//     }

//     @GetMapping("/")
//     public String index() {
//         return "ログインはこちら → http://localhost:8080/oauth2/authorization/google";
//     }
// }


import com.xie.video.command.application.YouTubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

@RestController
public class AuthController {

    private final YouTubeService youTubeService;

    public AuthController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/videos")
    public List<String> getVideoIds(
        @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String playlistId = "PLINGkNFSbpzdcuKVrHr2t7fgYm4FjGvbk";

        return youTubeService.fetchVideoIds(accessToken, playlistId);

    }
    @GetMapping("/")
    public String home() {
        return "ログイン成功！/videos にアクセスして動画IDを取得できます。";
}
}