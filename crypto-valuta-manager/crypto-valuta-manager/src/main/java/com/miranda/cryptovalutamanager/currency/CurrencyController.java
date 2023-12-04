package com.miranda.cryptovalutamanager.currency;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/currencies")
public class CurrencyController {

    private ModelMapper modelMapper;
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        super();
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

    /**
     * Inserts a new currency into the database
     * @param currencyDto transfers the data to a currency entity
     * @return ResponseEntity<Currency>
     */
    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody CurrencyDto currencyDto) {
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
     * @return Optional<Currency>
     */
    @PutMapping(path = "{currencyId}")
    public Optional<Currency> updateCurrency(
            @PathVariable("currencyId") String id,
            @RequestBody CurrencyDto currencyDto) {
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
        return currencyService.deleteCurrency(id);
    }
}
