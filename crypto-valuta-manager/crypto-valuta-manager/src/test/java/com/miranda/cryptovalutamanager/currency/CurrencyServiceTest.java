package com.miranda.cryptovalutamanager.currency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    private final String ID = "XRP";
    private final String NAME = "Ripple";
    private final Double NUMBER_OF_COINS = 1000000.00;
    private final Double MARKET_CAP = 2000000.00;

    private final Currency currency = new CurrencyBuilder(ID)
            .setName(NAME)
            .setNumberOfCoins(NUMBER_OF_COINS)
            .setMarketCap(MARKET_CAP)
            .build();
    @Mock
    private CurrencyRepository currencyRepository;
    @InjectMocks
    private CurrencyService testedService;

    @Test
    void ShouldGetCurrenciesWithPagingAndSorting() {
        Page<Currency> currencyPage = Mockito.mock(Page.class);
        when(currencyRepository.findAll(Mockito.any(Pageable.class))).thenReturn(currencyPage);

        List<Currency> currencyList = testedService.getCurrencies(0, 5, "ticker");

        Assertions.assertThat(currencyList).isNotNull();
    }

    @Test
    void shouldGetCurrencyById() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.ofNullable(currency));

        Optional<Currency> currencyById = testedService.getCurrencyById(ID);

        Assertions.assertThat(currencyById).isNotNull();
    }

    @Test
    void ShouldAddNewCurrency() {
        when(currencyRepository.save(Mockito.any(Currency.class))).thenReturn(currency);

        ResponseEntity<Currency> reponseEntity = testedService.addNewCurrency(currency);
        HttpStatusCode httpStatus = reponseEntity.getStatusCode();

        Assertions.assertThat(httpStatus).isEqualTo(HttpStatus.OK);
    }

    @Test
    void currencyAlreadyExists() {
        when(currencyRepository.existsById(ID)).thenReturn(true);

        ResponseEntity<Currency> reponseEntity = testedService.addNewCurrency(currency);
        HttpStatusCode httpStatus = reponseEntity.getStatusCode();

        Assertions.assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateCurrency() {
        String expected = "different name";
        Currency newCurrency = new CurrencyBuilder(ID)
                .setName(expected)
                .setNumberOfCoins(NUMBER_OF_COINS)
                .setMarketCap(MARKET_CAP)
                .build();

        when(currencyRepository.save(newCurrency)).thenReturn(newCurrency);

        Currency changedCurrency = testedService.updateCurrency(ID, newCurrency);
        Assertions.assertThat(changedCurrency.getName()).isEqualTo(expected);
    }

    @Test
    void ShouldDeleteCurrency() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.ofNullable(currency));
        doNothing().when(currencyRepository).deleteById(ID);

        ResponseEntity<Optional<Currency>> reponseEntity = testedService.deleteCurrency(ID);
        HttpStatusCode httpStatus = reponseEntity.getStatusCode();

        Assertions.assertThat(httpStatus).isEqualTo(HttpStatus.OK);
    }

    @Test
    void CanNotDeleteCurrencyThatDoesNotExist() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<Optional<Currency>> reponseEntity = testedService.deleteCurrency(ID);
        HttpStatusCode httpStatus = reponseEntity.getStatusCode();

        Assertions.assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}