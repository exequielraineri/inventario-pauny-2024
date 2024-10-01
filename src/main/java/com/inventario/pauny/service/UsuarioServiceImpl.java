/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.service;

import com.inventario.pauny.entity.Usuario;
import com.inventario.pauny.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author exera
 */
@Service
public class UsuarioServiceImpl implements IUsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public Usuario obtener(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    
    @Override
    public Usuario guardar(Usuario elemento) {
        return usuarioRepository.save(elemento);
    }
    
    @Override
    public Usuario eliminar(Long id) {
        Usuario usuarioEliminado = usuarioRepository.findById(id).get();
        usuarioRepository.deleteById(id);
        return usuarioEliminado;
    }

    @Override
    public Usuario findByEmailAndPassword(String email,String password) {
        Usuario user = usuarioRepository.findByEmailAndPassword(email, password);
        System.out.println("user: "+user);
        return user;
    }
    
    
    
    
}
