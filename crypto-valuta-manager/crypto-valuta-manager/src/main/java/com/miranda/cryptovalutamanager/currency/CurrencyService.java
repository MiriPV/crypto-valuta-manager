package com.miranda.cryptovalutamanager.currency;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Returns Currency objects paginated and sorted
     * @param page the page which is returned
     * @param size the number of objects that are displayed per page
     * @param sort the name of the object field the results are sorted on
     * @return List<Currency>
     */
    public List<Currency> getCurrencies(Integer page, Integer size, String sort) {
        Pageable pagingAndSorting = PageRequest.of(page, size, Sort.by(sort).ascending());
        return currencyRepository.findAll(pagingAndSorting).getContent();
    }

    /**
     * Returns a currency with the requested id, returns null if the object doesn't exist
     * @param id The currency id
     * @return Optional<Currency>
     */
    public Optional<Currency> getCurrencyById(String id) {
        return currencyRepository.findById(id);
    }

    /**
     * Inserts a new currency into the database
     * @param currency
     * @return ResponseEntity<Currency>
     */
    public ResponseEntity<Currency> addNewCurrency(Currency currency) {
        String id = currency.getTicker();
        if (currencyRepository.existsById(id)) {
            return new ResponseEntity<>(currency, HttpStatus.BAD_REQUEST);
        } else {
            currencyRepository.save(currency);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }
    }

    /**
     * Updates values of a currency entity in the database
     * @param id The id of the currency entity that needs to be updated
     * @param currency The new currency entity
     * @return Optional<Currency>
     */
    public Optional<Currency> updateCurrency(String id, Currency currency) {
        currencyRepository
                .findById(id)
                .map(oldCurrency -> {
                    oldCurrency.setName(currency.getName());
                    oldCurrency.setNumberOfCoins(currency.getNumberOfCoins());
                    oldCurrency.setMarketCap(currency.getMarketCap());
                    return currencyRepository.save(currency);
                });
        return Optional.empty();
    }

    /**
     * Deletes a currency entity from the database
     * @param id The id of the entity that needs to be deleted
     * @return ResponseEntity<Optional<Currency>>
     */
    public ResponseEntity<Optional<Currency>> deleteCurrency(String id) {
        if (currencyRepository.findById(id).isPresent()) {
            currencyRepository.deleteById(id);
            return new ResponseEntity<>(currencyRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }
    }
}
