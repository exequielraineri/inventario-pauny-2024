/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Proveedor;
import com.inventario.pauny.entity.Sucursal;
import com.inventario.pauny.repository.ProveedorRepository;
import com.inventario.pauny.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Exequiel
 */
@Service
public class SucursalServiceImpl implements ISucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<Sucursal> listar() {
        return sucursalRepository.findAll();
    }

    @Override
    public Sucursal obtener(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    @Override
    public Sucursal guardar(Sucursal elemento) {
        return sucursalRepository.save(elemento);
    }

    @Override
    public Sucursal eliminar(Long id) {
        Sucursal sucursalEliminada = sucursalRepository.findById(id).get();
        sucursalRepository.deleteById(id);
        return sucursalEliminada;
    }
}
