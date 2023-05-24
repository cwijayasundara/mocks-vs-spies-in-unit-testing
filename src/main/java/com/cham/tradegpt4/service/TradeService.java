package com.cham.tradegpt4.service;

import com.cham.tradegpt4.repository.TradeRepository;
import com.cham.tradegpt4.domain.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Trade save(Trade trade) {
        log.info("Saving trade: {}", trade);
        if (!validateTrade(trade)) {
            throw new RuntimeException("Trade is not valid");
        }
        return tradeRepository.save(trade);
    }
    private boolean validateTrade(Trade trade) {
        log.info("Validation trade: {}", trade);
        if(trade.getId() == null || trade.getId().isEmpty())
            return false;
        if(trade.getTrader()==null || trade.getTrader().isEmpty())
            return false;
        if(trade.getDate()==null)
            return false;
        return true;
    }

    public Optional<Trade> findById(String id) {
        return tradeRepository.findById(id);
    }

    public Iterable<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public void deleteById(String id) {
        tradeRepository.deleteById(id);
    }
}

