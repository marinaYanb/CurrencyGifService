package ru.alfa.currencyservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alfa.currencyservice.feign.GifClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {
    @Value("${giphy.app_id}")
    private String apiKey;

    private final GifClient gifClient;

    public ResponseEntity<Map> getGif(String tag) {
        return gifClient.getRandomGif(apiKey, tag);
    }
}
