package com.miranda.cryptovalutamanager.currency;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Returns Currency objects paginated and sorted
     * @param page the page which is returned
     * @param size the number of objects that are displayed per page
     * @param sort the name of the object field the results are sorted on
     * @return List<Currency>
     */
    @GetMapping
    public List<Currency> getCurrencies (
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1000") Integer size,
            @RequestParam(defaultValue = "ticker") String sort) {
        return currencyService.getCurrencies(page, size, sort);
    }

    /**
     * Returns a currency with the requested id, returns null if the object doesn't exist
     * @param id The currency id
     * @return Optional<Currency>
     */
    @GetMapping(path = "{currencyId}")
    public Optional<Currency> getCurrencyById (@PathVariable("currencyId") String id){
        return currencyService.getCurrencyById(id);
    }

}
