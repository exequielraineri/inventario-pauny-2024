/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Exequiel
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class StockSucursalDTO {

    private Long id;
    private Long idProducto;
    private Long idSucursal;
    private String nombreSucursal;
    private int cantidad;

}
