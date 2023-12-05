package com.miranda.cryptovalutamanager.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = CurrencyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    private final String ID = "XRP";
    private final String ID2 = "BTC";
    private final String NAME = "Ripple";
    private final String NAME2 = "Bitcoin";
    private final Double NUMBER_OF_COINS = 1000000.00;
    private final Double MARKET_CAP = 2000000.00;
    private final Currency currency = new CurrencyBuilder(ID)
            .setName(NAME)
            .setNumberOfCoins(NUMBER_OF_COINS)
            .setMarketCap(MARKET_CAP)
            .build();

    private final Currency currency2 = new CurrencyBuilder(ID2)
            .setName(NAME2)
            .setNumberOfCoins(NUMBER_OF_COINS)
            .setMarketCap(MARKET_CAP)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyService currencyService;


    @Test
    void shouldGetCurrenciesPaginatedAndSorted() throws Exception {
        List<Currency> mockList = List.of(currency2, currency);
        when(currencyService.getCurrencies(0,2, "ticker")).thenReturn(mockList);

        ResultActions response = mockMvc.perform(get("/api/currencies")
                .param("page","0")
                .param("size", "2")
                .param("sort", "ticker"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("[{\"ticker\":\"BTC\",\"name\":\"Bitcoin\",\"numberOfCoins\":1000000.0,\"marketCap\":2000000.0},{\"ticker\":\"XRP\",\"name\":\"Ripple\",\"numberOfCoins\":1000000.0,\"marketCap\":2000000.0}]"));
    }

    @Test
    void shouldGetCurrencyById() throws Exception {
        when(currencyService.getCurrencyById(ID2)).thenReturn(Optional.ofNullable(currency2));

        ResultActions response = mockMvc.perform(get("/api/currencies/" + ID2));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("{\"ticker\":\"BTC\",\"name\":\"Bitcoin\",\"numberOfCoins\":1000000.0,\"marketCap\":2000000.0}"));
    }

    @Test
    void shouldAddCurrency() throws Exception {
        when(currencyService.addNewCurrency(currency2))
                .thenReturn(new ResponseEntity<>(currency2, HttpStatus.OK));

        ResultActions response = mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency2)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCurrency() throws Exception {
        when(currencyService.updateCurrency(ID2, currency2)).thenReturn(currency2);

        ResultActions response = mockMvc.perform(put("/api/currencies/" + ID2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency2)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCurrency() throws Exception {
        when(currencyService.deleteCurrency(ID))
                .thenReturn(new ResponseEntity<>(Optional.ofNullable(currency), HttpStatus.OK));

        ResultActions response = mockMvc.perform(delete("/api/currencies/" + ID));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("{\"ticker\":\"XRP\",\"name\":\"Ripple\",\"numberOfCoins\":1000000.0,\"marketCap\":2000000.0}"));
    }

    @Test
    void canNotDeleteIfCurrencyDoesNotExist() throws Exception {
        when(currencyService.deleteCurrency(ID))
                .thenReturn(new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST));

        ResultActions response = mockMvc.perform(delete("/api/currencies/" + ID));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}