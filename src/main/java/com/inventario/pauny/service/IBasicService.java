/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author exera
 */
public interface IBasicService<T> {
    
    @Transactional(readOnly = true)
    public List<T> listar();
    
    @Transactional(readOnly = true)
    public T obtener(Long id);
    
    @Transactional
    public T guardar(T elemento);
    
    @Transactional
    public T eliminar(Long id);
    
}
