package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Data//lombok
@Entity//Es una entidad
@Table(name="CarritoCompra")//Como quiereas que se llame la tabla
public class CarritoCompra {

    public CarritoCompra() {
        this.productos = new ArrayList<>();
        this.nombre="";
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "nombre", nullable = false)
    public String nombre;

    @Column(name = "precio")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private Double precio;

    @ManyToMany
    @JoinTable(name = "carrito_compra_productos",
            joinColumns = @JoinColumn(name = "carrito_compra_id"),
            inverseJoinColumns = @JoinColumn(name = "productos_id"))
    private List<Producto> productos = new ArrayList<>();

}