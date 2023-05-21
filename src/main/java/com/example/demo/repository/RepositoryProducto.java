package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface RepositoryProducto extends JpaRepository<Producto, Long> { //long es una reserva de memoria


    @Query(value = "SELECT * FROM productos", nativeQuery = true) //extrae lo uqe hay en esa consulta, el native query lo que le dice es que estamos escribiendo igualq ue en la consola
    public List<Producto> findAll(); //siempre debe ser publica

    @Query(value = "SELECT * FROM productos WHERE ID = ?1", nativeQuery = true)

    public Optional<Producto> findById(int id);

    @Modifying
    @Transactional
    @Query(
            value =
                    "INSERT INTO productos (nombre, precio) values (:Nombre, :Precio)", //se usa lo que he comentado dos lineas mas abajo
            nativeQuery = true)
    void addProducto(@Param("Nombre") String first_name, @Param("Precio") double precio );//aqui se le da los nombres a los parámetros para usarlos en  dos lineas mas arriba


    @Modifying //para modificar necesitas escribir esto y la de abajo
    @Transactional //esta tambien
    @Query(value = "DELETE FROM productos WHERE ID=:id", nativeQuery = true)
    void deleteProducto(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos SET nombre=:Nombre, precio=:Precio WHERE ID=:id", nativeQuery = true)
    void editProducto(@Param("id") int id, @Param("Nombre") String nombre, @Param("Precio") double precio);


}