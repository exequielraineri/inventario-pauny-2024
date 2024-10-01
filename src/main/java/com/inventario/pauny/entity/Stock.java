package com.inventario.pauny.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Exequiel
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@ToString(exclude = {"sucursal", "producto"})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")
    @JsonBackReference(value = "suc_stock")
    private Sucursal sucursal;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonBackReference(value = "prod_stock")
    private Producto producto;

    private Integer cantidad;

    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;

    public Map<String, Object> getProductoData() {
        Map<String, Object> data = new HashMap<>();
        if (producto != null) {
            data.put("id", this.producto.getId());
            data.put("codigo", this.producto.getCodigo());
            data.put("descripcion", this.producto.getDescripcion());
            data.put("precio", this.producto.getPrecioUnidad());
            data.put("categoria", this.producto.getCategoria());
        } else {
            data.put("id", null);
        }

        return data;
    }

    public Map<String, Object> getSucursalData() {
        Map<String, Object> data = new HashMap<>();
        if (sucursal != null) {
            data.put("id", sucursal.getId());
            data.put("nombre", sucursal.getNombre());
            data.put("provincia", sucursal.getProvincia());
            data.put("direccion", sucursal.getDireccion());
        } else {
            data.put("id", null);
        }
        return data;
    }

}
