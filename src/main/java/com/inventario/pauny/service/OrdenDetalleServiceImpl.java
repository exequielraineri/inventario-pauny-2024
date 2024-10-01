/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.OrdenDetalle;
import com.inventario.pauny.repository.OrdenDetalleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Exequiel
 */
@Service
public class OrdenDetalleServiceImpl implements IOrdenDetalleService {

    @Autowired
    private OrdenDetalleRepository ordenDetalleRepository;

    @Override
    public List<OrdenDetalle> listar() {
        return ordenDetalleRepository.findAll();
    }

    @Override
    public OrdenDetalle obtener(Long id) {
        return ordenDetalleRepository.findById(id).orElse(null);
    }

    @Override
    public OrdenDetalle guardar(OrdenDetalle elemento) {
        return ordenDetalleRepository.save(elemento);
    }

    @Override
    public OrdenDetalle eliminar(Long id) {
        OrdenDetalle ordenDetalleEliminado = ordenDetalleRepository.findById(id).get();
        ordenDetalleRepository.deleteById(id);
        return ordenDetalleEliminado;
    }
    
}
