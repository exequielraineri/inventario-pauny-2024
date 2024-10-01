/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Usuario;
import java.util.Optional;

/**
 *
 * @author exera
 */
public interface IUsuarioService extends IBasicService<Usuario>{
 
    
   Usuario findByEmailAndPassword(String email,String password);
}
