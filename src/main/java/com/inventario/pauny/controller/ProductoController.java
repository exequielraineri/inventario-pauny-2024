/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.controller;

import com.inventario.pauny.dtos.ProductoDTO;
import com.inventario.pauny.entity.Producto;
import com.inventario.pauny.entity.Stock;
import com.inventario.pauny.entity.Sucursal;
import com.inventario.pauny.mapper.ProductoMapper;
import com.inventario.pauny.service.ProductoServiceImpl;
import com.inventario.pauny.service.StockServiceImpl;
import com.inventario.pauny.service.SucursalServiceImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@RestController
@RequestMapping("productos")
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;
    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private SucursalServiceImpl sucursalService;
    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            List<Producto> productos = productoService.listar();
            List<ProductoDTO> productosDto = productos.stream().map((prod) -> {
                return ProductoMapper.toDTO(prod);
            }).collect(Collectors.toList());
            response.put("data", productosDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stocks")
    public ResponseEntity<Map<String, Object>> listarConStock() {
        try {
            response = new HashMap<>();

            List<Stock> stocks = stockService.listar();

            // Forzar la carga completa de los datos de sucursal y producto
            stocks.forEach(stock -> {
                stock.getSucursal().getId(); // Forzar la carga de Sucursal
                stock.getProducto().getId(); // Forzar la carga de Producto
            });

            // Opcional: transformar los datos en un DTO para controlar mejor lo que se expone
            List<StockDTO> stockDTOs = stocks.stream().map(stock -> new StockDTO(
                    stock.getId(),
                    stock.getProducto(),
                    stock.getSucursal(),
                    stock.getCantidad(),
                    stock.getFechaActualizacion()
            )).toList();

            response.put("data", stocks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Producto producto, @RequestParam("idSucursal") Long idSucursal, @RequestParam(value = "cantidad", required = false) Integer cantidad) {
        try {
            System.out.println("Entrando al metodo");
            System.out.println("producto: " + (producto == null ? "si" : "no"));
            response = new HashMap<>();
            producto.setFechaRegistro(new Date());
            producto = productoService.guardar(producto);
            log.info(producto.toString());
            // Crear registros de stock para cada sucursal existente
            List<Sucursal> sucursales = sucursalService.listar();
            for (Sucursal sucursal : sucursales) {
                Stock nuevoStock = new Stock();
                nuevoStock.setProducto(producto);
                nuevoStock.setSucursal(sucursal);
                if (sucursal.getId().equals(idSucursal) && cantidad != null) {
                    nuevoStock.setCantidad(cantidad);
                } else {
                    nuevoStock.setCantidad(0); // Inicialmente en 0, o según tu lógica de negocio
                }
                stockService.guardar(nuevoStock);
            }

            response.put("data", producto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody Producto producto, @RequestParam(value = "idSucursal") Long idSucursal, @RequestParam(value = "cantidad") Integer cantidad) {
        try {
            response = new HashMap<>();
            Producto productoBD = productoService.obtener(id);

            if (productoBD == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            productoBD.setDescripcion(producto.getDescripcion());
            productoBD.setPrecioUnidad(producto.getPrecioUnidad());
            productoBD.setCategoria(producto.getCategoria());
            productoBD.setCodigo(producto.getCodigo());
            for (Stock stock : productoBD.getStocks()) {
                if (stock.getSucursal().getId() == idSucursal) {
                    stock.setCantidad(cantidad);
                    stock.setFechaActualizacion(new Date());
                    stockService.guardar(stock);
                }
            }
            productoBD = productoService.guardar(productoBD);

            response.put("data", productoBD);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        try {
            response = new HashMap<>();
            Producto producto = productoService.obtener(id);
            response.put("data", producto);
            if (producto == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
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
            Producto producto = productoService.eliminar(id);
            response.put("data", producto);
            if (producto == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DTO para controlar lo que se envía en la respuesta
    private static class StockDTO {

        private Long id;
        private Sucursal sucursal;
        private Producto producto;
        private Integer cantidad;
        private Date fechaActualizacion;

        public StockDTO(Long id, Producto producto, Sucursal sucursal, Integer cantidad, Date fechaActualizacion) {
            this.id = id;
            this.sucursal = sucursal;
            this.producto = producto;
            this.cantidad = cantidad;
            this.fechaActualizacion = fechaActualizacion;
        }

        // Getters y setters (puedes generarlos automáticamente si usas Lombok)
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Sucursal getSucursal() {
            return sucursal;
        }

        public void setSucursal(Sucursal sucursal) {
            this.sucursal = sucursal;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Date getFechaActualizacion() {
            return fechaActualizacion;
        }

        public void setFechaActualizacion(Date fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
        }

    }
}
