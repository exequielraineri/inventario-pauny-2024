/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.mapper;

import com.inventario.pauny.dtos.ClienteDTO;
import com.inventario.pauny.dtos.OrdenDTO;
import com.inventario.pauny.dtos.OrdenDetalleDTO;
import com.inventario.pauny.dtos.SucursalDTO;
import com.inventario.pauny.entity.Orden;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Exequiel
 */
public class OrdenMapper {

    public static OrdenDTO toDto(Orden orden) {
        OrdenDTO ordenDto = new OrdenDTO();

        ordenDto.setId(orden.getId());
        ordenDto.setNroOrden(orden.getNroOrden());
        ordenDto.setTipoOrden(orden.getTipoOrden());
        ordenDto.setMetodoPago(orden.getMetodoPago());
        ordenDto.setFechaRegistro(orden.getFechaRegistro());
        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setApellido(orden.getCliente().getApellido());
        clienteDto.setNombre(orden.getCliente().getNombre());
        clienteDto.setId(orden.getCliente().getId());

        SucursalDTO sucursalDto = SucursalMapper.toDto(orden.getSucursal());

        List<OrdenDetalleDTO> ordenDetalleDto = new ArrayList<>();
        BigDecimal[] total = {BigDecimal.ZERO};
        ordenDetalleDto = orden.getOrdenDetalle().stream().map((detalle) -> {
            // Sumar el subtotal al total
            total[0] = total[0].add(detalle.getSubtotal());

            return new OrdenDetalleDTO(detalle.getId(), ProductoMapper.toDTO(detalle.getProducto()), detalle.getCantidad());
        }).collect(Collectors.toList());

        ordenDto.setTotal(total[0]);
        ordenDto.setCliente(clienteDto);
        ordenDto.setSucursal(sucursalDto);
        ordenDto.setOrdenDetalle(ordenDetalleDto);
        return ordenDto;
    }
}
