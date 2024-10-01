/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.controller;

import com.inventario.pauny.entity.Sucursal;
import com.inventario.pauny.entity.Usuario;
import com.inventario.pauny.service.SucursalServiceImpl;
import com.inventario.pauny.service.UsuarioServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("sucursales")
@CrossOrigin(origins = "*")
public class SucursalController {

    @Autowired
    private SucursalServiceImpl sucursalService;
    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            List<Sucursal> sucursales = sucursalService.listar();
            response.put("data", sucursales);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Sucursal sucursal) {
        try {
            response = new HashMap<>();
            
            sucursal = sucursalService.guardar(sucursal);
            response.put("data", sucursal);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            response = new HashMap<>();
            Sucursal sucursalBD = sucursalService.obtener(id);

            if (sucursalBD == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            sucursalBD.setNombre(sucursal.getNombre());
            sucursalBD.setCorreo(sucursal.getCorreo());
            sucursalBD.setDireccion(sucursal.getDireccion());
            sucursalBD.setTelefono(sucursal.getTelefono());
            sucursalBD.setProvincia(sucursal.getProvincia());

            sucursalBD = sucursalService.guardar(sucursalBD);

            response.put("data", sucursalBD);
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
            Sucursal sucursal = sucursalService.obtener(id);
            response.put("data", sucursal);
            if (sucursal == null) {
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
            Sucursal sucursal = sucursalService.eliminar(id);
            response.put("data", sucursal);
            if (sucursal == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
