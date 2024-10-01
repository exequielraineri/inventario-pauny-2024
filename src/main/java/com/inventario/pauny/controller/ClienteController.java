/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.controller;

import com.inventario.pauny.entity.Cliente;
import com.inventario.pauny.service.ClienteServiceImpl;
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
@RequestMapping("clientes")
public class ClienteController{
    
    @Autowired
    private ClienteServiceImpl clienteService;
    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            List<Cliente> clientes = clienteService.listar();
            response.put("data", clientes);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Cliente cliente) {
        try {
            response = new HashMap<>();
            cliente.setFechaRegistro(new Date());
            cliente = clienteService.guardar(cliente);
            response.put("data", cliente);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            response = new HashMap<>();
            Cliente clienteBD = clienteService.obtener(id);

            if (clienteBD == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            clienteBD.setApellido(cliente.getApellido());
            clienteBD.setCuit(cliente.getCuit());
            clienteBD.setNombre(cliente.getNombre());
            clienteBD.setTelefono(cliente.getTelefono());

            clienteBD = clienteService.guardar(clienteBD);

            response.put("data", clienteBD);
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
            Cliente cliente = clienteService.obtener(id);
            response.put("data", cliente);
            if (cliente == null) {
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
            Cliente cliente = clienteService.eliminar(id);
            response.put("data", cliente);
            if (cliente == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
