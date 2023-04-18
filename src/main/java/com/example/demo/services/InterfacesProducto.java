package com.example.demo.services;

import com.example.demo.model.Producto;
        import java.util.List;
        import java.util.Optional;

public interface InterfacesProducto {

    List<Producto> getAllProductos();
    public Optional<Producto> findById(int id);

    void addProducto(String nombre, double precio);
    void deleteProducto(int id);
    void editProducto(int id, String nombre, double precio);


}
