package com.example.demo.controllers;

import com.example.demo.model.CarritoCompra;
import com.example.demo.model.Producto;
import com.example.demo.repository.CarritoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ServicesProducto;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControllerPrueba {

    @Autowired
    private final ServicesProducto productoServices; //esto es de lo ultimo uqe ha explicado mirar registroAlumno y studen controller con el get y post
    private final CarritoCompraRepository carritoCompraRepository;

    public ControllerPrueba(ServicesProducto productoService, CarritoCompraRepository carritoCompraRepository){

        this.productoServices = productoService;

        this.carritoCompraRepository = carritoCompraRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/greeting/{nombre}/{edad}")
    public String hola(Model model, @PathVariable(name = "nombre") String nombre,
                       @PathVariable(name = "edad") int edad) {
        model.addAttribute("name", nombre);
        model.addAttribute("edad", edad);
        model.addAttribute("hola", "hola soy Isabel");
        return "greeting";
    }

    @GetMapping("/productos")
    public String hola(Model model) {
        System.out.println("HOLA");
        List<Producto> productos = productoServices.getAllProductos();
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/productos/nuevo") //mirar en profesor
    public String productosNuevo(Model model){
        model.addAttribute("producto", new Producto());
        /*model.addAttribute()*/
        return "registroProducto";
    }

    @PostMapping("/productos/nuevo")
    public RedirectView guardarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        productoServices.save(producto);
        return new RedirectView("/productos");
    }


    @GetMapping("/productos/delete")
    public String showDeleteProductForm() {
        return "eliminarProducto";
    }

    @PostMapping("/productos/delete")
    public RedirectView deleteProduct(@RequestParam("id") int id) {
        productoServices.deleteProducto(id);
        return new RedirectView("/productos");
    }

    @GetMapping("/productos/edit")
    public String editProducto(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Optional<Producto> aux = productoServices.findById(id);
            Producto producto = aux.orElseThrow(() ->
                    new RuntimeException("El producto no existe")
            );
            model.addAttribute("producto", producto);
            return "modificarProducto";
        } else {
            return "redirect:/productos/select";
        }
    }

    @PostMapping("/productos/edit/{id}")
    public Object saveProduct(@ModelAttribute("producto") Producto producto, @PathVariable int id, Model model) {
        String mensaje = new String("null");
        if(!mensaje.equals("null")){
            model.addAttribute("mensaje", mensaje);
            return "/error";
        }
        producto.setId(id);
        productoServices.save(producto);
        return new RedirectView("/productos");
    }

    @GetMapping("/productos/select")
    public String selectProducto() {
        return "seleccionarProducto";
    }

    private CarritoCompra obtenerCarritoExistente() {
        List<CarritoCompra> carritos = carritoCompraRepository.findAll();
        if (carritos.isEmpty()) {
            return new CarritoCompra();
        } else {
            return carritos.get(0);
        }
    }

    @GetMapping ("/productos/comprar/{id}")
    public RedirectView comprarProducto(@PathVariable int id) {
        System.out.println("SALIDA");
        Optional<Producto> optionalProducto = productoServices.findById(id);
        Producto producto = optionalProducto.orElseThrow(() ->
                new RuntimeException("El producto no existe")
        );

        // Agregar el producto al carrito de compras
        CarritoCompra carrito = new CarritoCompra();
        carrito.getProductos().add(producto);
        carrito.setNombre("Test");

        // Guardar el carrito de compras en el repositorio
        try {
            carritoCompraRepository.save(carrito);
        }catch(Exception e){
            System.out.println(e);
        }

        return new RedirectView("/productos");
    }


    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        List<CarritoCompra> carritos = carritoCompraRepository.findAll(); // Obtener todos los carritos de compra
        CarritoCompra carrito = carritos.get(0); // Obtener el primer carrito de compra
        List<Producto> productos = carrito.getProductos(); // Obtener la lista de productos del carrito

        model.addAttribute("carrito", productos);
        return "carrito";
    }

}