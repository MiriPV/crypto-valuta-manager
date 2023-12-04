package com.miranda.cryptovalutamanager.currency;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
}
