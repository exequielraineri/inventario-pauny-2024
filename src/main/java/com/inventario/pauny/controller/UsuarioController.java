/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.controller;

import com.inventario.pauny.dtos.UsuarioDTO;
import com.inventario.pauny.entity.Usuario;
import com.inventario.pauny.mapper.UsuarioMapper;
import com.inventario.pauny.service.UsuarioServiceImpl;
import java.awt.MultipleGradientPaint;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            List<Usuario> usuarios = usuarioService.listar();
            List<UsuarioDTO> usuariosDto = usuarios.stream().map((usuario) -> {
                return UsuarioMapper.toDto(usuario);
            }).collect(Collectors.toList());

            response.put("data", usuariosDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            usuario.setFechaRegistro(new Date());
            usuario = usuarioService.guardar(usuario);
            response.put("data", usuario);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            Usuario usuarioBD = usuarioService.obtener(id);

            if (usuario == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            usuarioBD.setNombre(usuario.getNombre());
            usuarioBD.setApellido(usuario.getApellido());
            usuarioBD.setEmail(usuario.getEmail());
            usuarioBD.setPassword(usuario.getPassword());

            usuarioBD = usuarioService.guardar(usuarioBD);

            response.put("data", usuarioBD);
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
            Usuario usuario = usuarioService.obtener(id);
            System.out.println("Sucursale: " + usuario.getSucursal());
            response.put("data", usuario);
            if (usuario == null) {
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
            Usuario usuario = usuarioService.eliminar(id);
            response.put("data", usuario);
            if (usuario == null) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario usuario) {
        try {
            System.out.println("usuraio; " + usuario);
            response = new HashMap<>();
            Usuario usuarioBD = usuarioService.findByEmailAndPassword(usuario.getEmail(), usuario.getPassword());

            if (usuarioBD == null) {
                response.put("mensaje", "Credenciales incorrectas");
            } else {
                response.put("data", usuarioBD);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
