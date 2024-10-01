/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Orden;
import com.inventario.pauny.repository.OrdenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Exequiel
 */
@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public List<Orden> listar() {
        return ordenRepository.findByAllOrderByFechaRegistroDesc();
    }

    @Override
    public Orden obtener(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }

    @Override
    public Orden guardar(Orden elemento) {
        return ordenRepository.save(elemento);
    }

    @Override
    public Orden eliminar(Long id) {
        Orden ordenEliminado = ordenRepository.findById(id).get();
        ordenRepository.deleteById(id);
        return ordenEliminado;
    }

}
