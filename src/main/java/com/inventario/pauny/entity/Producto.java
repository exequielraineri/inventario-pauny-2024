/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.pauny.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author exera
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data @Getter @Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @JsonBackReference(value = "proveedor")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora las propiedades problemáticas
    private Proveedor proveedor;

    private String descripcion;
    private BigDecimal precioUnidad;
    private String categoria;
    private boolean estado;
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @JsonIgnore
    @JsonManagedReference(value = "prod_stock")
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    public String getCodigo() {
        int cantCaracteres = codigo.length();
        int minCaracteres = 7;
        String resultado = "";
        if (cantCaracteres < minCaracteres) {

            int disponible = minCaracteres - cantCaracteres;
            while (disponible > 0) {
                resultado += "0";
                disponible--;
            }

        }
        resultado += codigo;
        setCodigo(resultado);
        return codigo;
    }

    
    
}
