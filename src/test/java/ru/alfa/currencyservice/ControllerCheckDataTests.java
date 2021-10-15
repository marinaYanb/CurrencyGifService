package ru.alfa.currencyservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.alfa.currencyservice.service.CurrencyRateService;
import ru.alfa.currencyservice.service.GifService;
import ru.alfa.currencyservice.web.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ControllerCheckDataTests {
    private static final String RIGHT_BASE = "EUR";
    private static final String BAD_BASE = "EURO";
    private static final String TAG = "rich";

    @MockBean
    private CurrencyRateService currencyRateService;

    @MockBean
    private GifService gifService;

    @Autowired
    private Controller controller;

    @Test
    public void getGifIncorrectBaseTest() {
        when(currencyRateService.compareRates(BAD_BASE)).thenReturn(1);
        assertEquals(HttpStatus.BAD_REQUEST, controller.getGif(BAD_BASE).getStatusCode());
    }

    @Test
    public void getGifCorrectBaseTest() {
        when(currencyRateService.compareRates(RIGHT_BASE)).thenReturn(1);
        when(gifService.getGif(TAG)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        assertEquals(HttpStatus.OK, controller.getGif(RIGHT_BASE).getStatusCode());
    }
}
