package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data//lombok
@Entity //esto es una entidad
@Table(name="productos")//como quieras que se llame la tabla

public class Producto implements Serializable {
    @Id//establece una id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//hace que se genere de forma autónoma como el autoincrement
    public int id;

    @Column(name = "nombre", nullable=false)
    public String nombre;

    @Column(name = "precio")
    public double precio;


}