package ru.alfa.currencyservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfa.currencyservice.model.CurrencyRate;

@FeignClient(name = "CurrencyClient", url = "${openexchangerates.url}")
public interface CurrencyRateClient {
    @GetMapping("/latest.json")
    CurrencyRate getLatestRates(@RequestParam("app_id") String appId);

    @GetMapping("/historical/{date}.json")
    CurrencyRate getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String appId);
}
