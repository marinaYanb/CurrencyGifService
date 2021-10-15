package ru.alfa.currencyservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alfa.currencyservice.feign.CurrencyRateClient;
import ru.alfa.currencyservice.model.CurrencyRate;
import ru.alfa.currencyservice.service.CurrencyRateService;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CurrencyRateServiceTest {
    @Value("${openexchangerates.app_id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    private CurrencyRate TODAY_CURRENCY_RATE_OBJ;
    private CurrencyRate YESTERDAY_CURRENCY_RATE_OBJ;

    @BeforeEach
    public void init() {
        TODAY_CURRENCY_RATE_OBJ = currencyRateObject("RUB", Map.of("RUB", 71.4));
        YESTERDAY_CURRENCY_RATE_OBJ = currencyRateObject("RUB", Map.of("RUB", 70.9));
    }

    @MockBean
    private CurrencyRateClient currencyRateClient;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Test
    public void compareRatesSuccessTest() {
        when(currencyRateClient.getLatestRates(appId)).thenReturn(TODAY_CURRENCY_RATE_OBJ);
        when(currencyRateClient.getHistoricalRates(LocalDate.now().minusDays(1).toString(), appId)).thenReturn(YESTERDAY_CURRENCY_RATE_OBJ);
        int compareResult = currencyRateService.compareRates(base);
        assertEquals(compareResult, 1);
    }

    @Test
    public void compareRatesFailedTest() {
        when(currencyRateClient.getLatestRates(appId)).thenReturn(TODAY_CURRENCY_RATE_OBJ);
        when(currencyRateClient.getHistoricalRates(LocalDate.now().minusDays(1).toString(), appId)).thenReturn(YESTERDAY_CURRENCY_RATE_OBJ);
        int compareResult = currencyRateService.compareRates(base);
        String baseValue = TODAY_CURRENCY_RATE_OBJ.getBase();
        assertNotEquals(baseValue, "AMD");
        assertNotEquals(compareResult, 2);
    }

    private static CurrencyRate currencyRateObject(String base, Map<String, Double> rates) {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setDisclaimer("test.ru");
        currencyRate.setBase(base);
        currencyRate.setRates(rates);
        return currencyRate;
    }
}

