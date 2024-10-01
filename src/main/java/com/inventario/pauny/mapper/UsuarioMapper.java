/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.mapper;

import com.inventario.pauny.dtos.UsuarioDTO;
import com.inventario.pauny.entity.Usuario;

/**
 *
 * @author Exequiel
 */
public class UsuarioMapper {

    public static UsuarioDTO toDto(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setSucursal(SucursalMapper.toDto(usuario.getSucursal()));
        usuarioDTO.setRol(usuario.getRol());
        return usuarioDTO;
    }
}
