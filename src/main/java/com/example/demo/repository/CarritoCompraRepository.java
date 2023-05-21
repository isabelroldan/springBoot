package com.example.demo.repository;

import com.example.demo.model.CarritoCompra;
import com.example.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Integer> {
    @Query(value = "SELECT * FROM carrito_compra WHERE ID = ?1", nativeQuery = true)
    public Optional<CarritoCompra> findById(int id);
}
