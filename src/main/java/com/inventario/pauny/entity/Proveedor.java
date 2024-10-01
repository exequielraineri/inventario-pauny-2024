
package com.inventario.pauny.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 *
 * @author exera
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "productos")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String telefono;
    private String email;
    private Date fechaRegistro;
    private boolean estado;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proveedor")
    @JsonManagedReference(value = "proveedor")
    private List<Producto> productos = new ArrayList<>();
}
