package ru.alfa.currencyservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.currencyservice.feign.CurrencyRateClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateServiceImpl implements CurrencyRateService {
    @Value("${openexchangerates.app_id}")
    private String appId;

    private final CurrencyRateClient currencyRateClient;

    @Override
    public int compareRates(String base) {
        Double today = currencyRateClient.getLatestRates(appId).getRates().get(base);
        Double previous = currencyRateClient.getHistoricalRates(LocalDate.now().minusDays(1).toString(), appId).getRates().get(base);
        log.info("Today rate for {}: {}; previous rate for {}: {}", base, today, base, previous);
        return today.compareTo(previous);
    }
}
