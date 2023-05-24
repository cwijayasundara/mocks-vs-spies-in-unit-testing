package com.cham.tradegpt4.controller;

import com.cham.tradegpt4.domain.Trade;
import com.cham.tradegpt4.service.TradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;

@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTestUsingSpies {

    @InjectMocks
    private TradeController tradeController;

    @Spy
    private TradeService spyTradeService;

    @Test
     public void testSave() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        Trade trade1 = new Trade("1", 100.0, date,"Chaminda" );
        Trade trade2 = new Trade("2", 200.0, date,"John" );

        Mockito.doReturn(trade1).when(spyTradeService).save(Mockito.any(Trade.class));
        ResponseEntity<Trade> responseEntity1 = tradeController.save(trade1);
        assertEquals(trade1, responseEntity1.getBody());
        //validate if the save method is called in the TradeService
        verify(spyTradeService).save(trade1);
        reset(spyTradeService);
        Mockito.doReturn(trade2).when(spyTradeService).save(Mockito.any(Trade.class));
        ResponseEntity<Trade> responseEntity2 = tradeController.save(trade2);
        verify(spyTradeService).save(trade2);
    }

    @Test
    public void testFindById() {
        String id = "1";
        Trade expectedTrade = new Trade();
        Mockito.doReturn(Optional.of(expectedTrade)).when(spyTradeService).findById(id);
        ResponseEntity<Trade> responseEntity = tradeController.findById(id);
        assertEquals(expectedTrade, responseEntity.getBody());
    }

    @Test
    public void testFindAll() {
        List<Trade> trades = Arrays.asList(new Trade(), new Trade());
        Mockito.doReturn(trades).when(spyTradeService).findAll();
        ResponseEntity<Iterable<Trade>> responseEntity = tradeController.findAll();
        assertEquals(trades, responseEntity.getBody());
    }

    @Test
    public void testDeleteById() {
        String id = "1";
        Mockito.doNothing().when(spyTradeService).deleteById(id);
        ResponseEntity<Void> responseEntity = tradeController.deleteById(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(spyTradeService).deleteById(id);
    }
}
