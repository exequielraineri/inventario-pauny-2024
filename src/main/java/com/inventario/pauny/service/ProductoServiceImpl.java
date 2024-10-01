/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Producto;
import com.inventario.pauny.entity.Stock;
import com.inventario.pauny.entity.Sucursal;
import com.inventario.pauny.repository.ProductoRepository;
import com.inventario.pauny.repository.StockRepository;
import com.inventario.pauny.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author exera
 */
@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtener(Long id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public Producto guardar(Producto elemento) {
        return productoRepository.save(elemento);
    }

    @Override
    public Producto eliminar(Long id) {
        Producto productoEliminado = productoRepository.findById(id).get();
        productoRepository.deleteById(id);
        return productoEliminado;
    }

    public void actualizarStock(Long productoId, Long sucursalId, Integer cantidad) {
        Producto producto = obtener(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + productoId);
        }

        Sucursal sucursal = sucursalRepository.findById(sucursalId).orElseThrow(()
                -> new IllegalArgumentException("Sucursal no encontrada: " + sucursalId)
        );

        Stock stock = stockRepository.findBySucursalAndProducto(sucursalId, productoId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock no encontrado para el producto " + productoId + " en la sucursal " + sucursalId);
        }

        int nuevaCantidad = stock.getCantidad() - cantidad;
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("Cantidad insuficiente en stock para el producto " + productoId);
        }

        stock.setCantidad(nuevaCantidad);
        stockRepository.save(stock);
    }

}
