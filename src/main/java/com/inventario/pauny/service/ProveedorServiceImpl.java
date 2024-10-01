/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Orden;
import com.inventario.pauny.entity.Proveedor;
import com.inventario.pauny.repository.OrdenRepository;
import com.inventario.pauny.repository.ProveedorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Exequiel
 */
@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtener(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor guardar(Proveedor elemento) {
        return proveedorRepository.save(elemento);
    }

    @Override
    public Proveedor eliminar(Long id) {
        Proveedor proveedorEliminado = proveedorRepository.findById(id).get();
        proveedorRepository.deleteById(id);
        return proveedorEliminado;
    }

}
