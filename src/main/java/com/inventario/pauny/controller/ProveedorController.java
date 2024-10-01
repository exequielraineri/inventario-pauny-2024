
package com.inventario.pauny.controller;

import com.inventario.pauny.entity.Proveedor;
import com.inventario.pauny.service.ProveedorServiceImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@RestController
@RequestMapping("proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorServiceImpl proveedorService;
    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap();
            List<Proveedor> proveedores = proveedorService.listar();
            response.put("data", proveedores);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Proveedor proveedor) {
        try {
            response = new HashMap();
            if (proveedor.getProductos().isEmpty()) {
                System.out.println("es vacio");
            }
            proveedor.setFechaRegistro(new Date());
            proveedor.setEstado(true);
            proveedor = proveedorService.guardar(proveedor);
            response.put("data", proveedor);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        try {
            response = new HashMap();
            Proveedor proveedorBD = proveedorService.obtener(id);

            if (proveedorBD == null) {
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }

            proveedorBD.setDescripcion(proveedor.getDescripcion());
            proveedorBD.setEmail(proveedor.getEmail());
            proveedorBD.setTelefono(proveedor.getTelefono());
            proveedorBD.setEstado(proveedor.isEstado());

            proveedorBD = proveedorService.guardar(proveedorBD);

            response.put("data", proveedorBD);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        try {
            response = new HashMap();
            Proveedor proveedor = proveedorService.obtener(id);
            response.put("data", proveedor);
            if (proveedor == null) {
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        try {
            response = new HashMap();
            Proveedor proveedor = proveedorService.eliminar(id);
            response.put("data", proveedor);
            if (proveedor == null) {
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
