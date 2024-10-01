/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.mapper;

import com.inventario.pauny.dtos.ProductoDTO;
import com.inventario.pauny.dtos.ProveedorDTO;
import com.inventario.pauny.dtos.StockSucursalDTO;
import com.inventario.pauny.entity.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Exequiel
 */
public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setCategoria(producto.getCategoria());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecioUnidad(producto.getPrecioUnidad());
        productoDTO.setCodigo(producto.getCodigo());
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        proveedorDTO.setDescripcion(producto.getProveedor().getDescripcion());
        proveedorDTO.setId(producto.getProveedor().getId());
        proveedorDTO.setDireccion(producto.getProveedor().getTelefono());

        productoDTO.setProveedor(proveedorDTO);

        List<StockSucursalDTO> stockSucursal = new ArrayList<>();

        stockSucursal = producto.getStocks().stream().map((stock) -> {
            StockSucursalDTO stockDto = new StockSucursalDTO(stock.getId(),
                    stock.getProducto().getId(),
                    stock.getSucursal().getId(),stock.getSucursal().getProvincia(),
                    stock.getCantidad()
            );
            return stockDto;
        }).collect(Collectors.toList());

        productoDTO.setStockSucursal(stockSucursal);

        return productoDTO;
    }

}
