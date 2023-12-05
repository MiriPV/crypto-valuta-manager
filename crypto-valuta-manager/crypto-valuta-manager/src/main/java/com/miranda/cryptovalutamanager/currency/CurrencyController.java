package com.miranda.cryptovalutamanager.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String GET = "API CALL GET: ";
    private final String POST = "API CALL POST: ";
    private final String PUT = "API CALL PUT: ";
    private final String DELETE = "API CALL DELETE: ";

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
        logger.trace(GET + "getCurrencies - page: " + page + ", size: " + size + ", sort: " + sort);
        return currencyService.getCurrencies(page, size, sort);
    }

    /**
     * Returns a currency with the requested id, returns null if the object doesn't exist
     * @param id The currency id
     * @return Optional<Currency>
     */
    @GetMapping(path = "{currencyId}")
    public Optional<Currency> getCurrencyById (@PathVariable("currencyId") String id){
        logger.trace(GET + "getCurrencyById - id: " + id);
        return currencyService.getCurrencyById(id);
    }

    /**
     * Inserts a new currency into the database
     * @param currencyDto transfers the data to a currency entity
     * @return ResponseEntity<Currency>
     */
    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody CurrencyDto currencyDto) {
        logger.trace(POST + "addCurrency - RequestBody: " + currencyDto.toString());
        CurrencyBuilder builder = new CurrencyBuilder(currencyDto.getTicker());
        Currency currency = builder
                .setName(currencyDto.getName())
                .setNumberOfCoins(currencyDto.getNumberOfCoins())
                .setMarketCap(currencyDto.getMarketCap())
                .build();
        return currencyService.addNewCurrency(currency);
    }

    /**
     * Updates the values of a currency entity in the database
     * @param id the id of the currency entity that needs to be updated
     * @param currencyDto transfers the data to a currency entity
     * @return Currency
     */
    @PutMapping(path = "{currencyId}")
    public Currency updateCurrency(
            @PathVariable("currencyId") String id,
            @RequestBody CurrencyDto currencyDto) {
        logger.trace(PUT + "updateCurrency - id: " + id + " - RequestBody: " + currencyDto.toString());
        CurrencyBuilder builder = new CurrencyBuilder(currencyDto.getTicker());
        Currency currency = builder
                .setName(currencyDto.getName())
                .setNumberOfCoins(currencyDto.getNumberOfCoins())
                .setMarketCap(currencyDto.getMarketCap())
                .build();
        return currencyService.updateCurrency(id, currency);
    }

    /**
     * Deletes a currency entity from the database
     * @param id The id of the entity that needs to be deleted
     * @return ResponseEntity<Optional<Currency>>
     */
    @DeleteMapping(path = "{currencyId}")
    public ResponseEntity<Optional<Currency>> deleteCurrency(@PathVariable("currencyId") String id) {
        logger.trace(DELETE + "deleteCurrency - id: " + id);
        return currencyService.deleteCurrency(id);
    }
}
