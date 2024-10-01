/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.mapper;

import com.inventario.pauny.dtos.SucursalDTO;
import com.inventario.pauny.entity.Sucursal;

/**
 *
 * @author Exequiel
 */
public class SucursalMapper {
    
    public static SucursalDTO toDto(Sucursal sucursal) {
        SucursalDTO sucursalDTO = new SucursalDTO();
        sucursalDTO.setId(sucursal.getId());
        sucursalDTO.setNombre(sucursal.getNombre());
        sucursalDTO.setDireccion(sucursal.getDireccion());
        sucursalDTO.setProvincia(sucursal.getProvincia());
        return sucursalDTO;
    }
}
