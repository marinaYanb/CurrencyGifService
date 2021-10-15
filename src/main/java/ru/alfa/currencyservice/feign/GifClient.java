package ru.alfa.currencyservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "GiphyClient", url = "${giphy.url}")
public interface GifClient {
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(@RequestParam("api_key") String apiKey, @RequestParam("tag") String tag);
}
