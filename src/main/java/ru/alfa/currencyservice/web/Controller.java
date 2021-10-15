package ru.alfa.currencyservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.alfa.currencyservice.service.CurrencyRateService;
import ru.alfa.currencyservice.service.GifService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;
    @Value("${giphy.no_changes}")
    private String noChange;

    private final GifService gifService;
    private final CurrencyRateService currencyRateService;

    @GetMapping("/getgif/{base}")
    public ResponseEntity<Map> getGif(@PathVariable String base) {
        if (base.matches("[A-Z]{3}")) {
            int coeff = currencyRateService.compareRates(base);
            String tag = coeff == 1 ? rich : coeff == -1 ? broke : noChange;
            log.info("Tag for gif: {}", tag);
            return gifService.getGif(tag);
        } else {
            log.info("Incorrect parameter: {}", base);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
