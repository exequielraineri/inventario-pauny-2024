package com.inventario.pauny.service;

import com.inventario.pauny.entity.Stock;
import com.inventario.pauny.repository.StockRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements IBasicService<Stock> {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> listar() {
        return stockRepository.findAll();
    }

    @Override
    public Stock obtener(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Stock guardar(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock eliminar(Long id) {
        Stock stock = obtener(id);
        if (stock != null) {
            stockRepository.delete(stock);
        }
        return stock;
    }

}
