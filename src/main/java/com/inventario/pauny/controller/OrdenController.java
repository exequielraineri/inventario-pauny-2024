/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.controller;

import com.inventario.pauny.dtos.OrdenDTO;
import com.inventario.pauny.entity.Orden;
import com.inventario.pauny.entity.OrdenDetalle;
import com.inventario.pauny.entity.Producto;
import com.inventario.pauny.entity.Stock;
import com.inventario.pauny.mapper.OrdenMapper;
import com.inventario.pauny.repository.StockRepository;
import com.inventario.pauny.service.OrdenDetalleServiceImpl;
import com.inventario.pauny.service.OrdenServiceImpl;
import com.inventario.pauny.service.ProductoServiceImpl;
import com.inventario.pauny.service.StockServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@RestController
@RequestMapping("ordenes")
public class OrdenController {

    @Autowired
    private OrdenServiceImpl ordenService;

    @Autowired
    private OrdenDetalleServiceImpl ordenDetalleService;

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private StockServiceImpl stockService;

    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            List<Orden> ordenes = ordenService.listar();
            List<OrdenDTO> ordenesDto = ordenes.stream().map((orden) -> {
                return OrdenMapper.toDto(orden);
            }).collect(Collectors.toList());
            response.put("data", ordenesDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Orden orden) {
        try {
            response = new HashMap<>();
            orden.setFechaRegistro(new Date());
            BigDecimal total = new BigDecimal(0);

            List<OrdenDetalle> detalles = new ArrayList<>(orden.getOrdenDetalle());
            orden.getOrdenDetalle().clear();
            for (OrdenDetalle detalle : detalles) {
                System.out.println("Detalle:" + detalle.getProducto().getPrecioUnidad());
                total = total.add(detalle.getSubtotal());
                detalle.setOrden(orden);
                //orden.setTotal(orden.getTotal().add(detalle.getSubtotal()));
                ordenDetalleService.guardar(detalle);
                orden.getOrdenDetalle().add(detalle);
                Producto p = productoService.obtener(detalle.getProducto().getId());
                for (Stock stock : p.getStocks()) {
                    if (stock.getSucursal().getId() == orden.getSucursal().getId()) {
                        stock.setCantidad(stock.getCantidad() - detalle.getCantidad());
                        break;
                    }
                }
                productoService.guardar(p);
            }

            orden.setTotal(total);
            orden = ordenService.guardar(orden);

            response.put("data", OrdenMapper.toDto(orden));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        try {
            response = new HashMap<>();
            Orden orden = ordenService.obtener(id);

            if (orden == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            OrdenDTO ordenDto = OrdenMapper.toDto(orden);
            response.put("data", ordenDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        try {
            response = new HashMap<>();
            Orden orden = ordenService.eliminar(id);
            response.put("data", orden);
            if (orden == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{idOrden}/agregar-detalle")
    public ResponseEntity<Map<String, Object>> agregarDetalle(@PathVariable Long idOrden, @RequestBody OrdenDetalle detalle) {
        try {
            response = new HashMap<>();
            Orden orden = ordenService.obtener(idOrden);
            if (orden == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            detalle.setOrden(orden);
            detalle.setSubtotal(BigDecimal.valueOf(detalle.getCantidad() * detalle.getProducto().getPrecioUnidad().doubleValue()));

            orden.getOrdenDetalle().add(detalle);
            orden = ordenService.guardar(orden);
            response.put("data", orden);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
