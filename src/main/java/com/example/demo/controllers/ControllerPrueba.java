package com.example.demo.controllers;

import com.example.demo.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ServicesProducto;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class ControllerPrueba {

    @Autowired
    private final ServicesProducto productoServices; //esto es de lo ultimo uqe ha explicado mirar registroAlumno y studen controller con el get y post

    public ControllerPrueba(ServicesProducto productoService){

        this.productoServices = productoService;
    }

   /* @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/

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

    @GetMapping("/productos/delete/{id}")
    public RedirectView deleteProducto(@PathVariable int id){
        productoServices.deleteProducto(id);
        return new RedirectView("/productos");
    }


    @GetMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable int id, Model model){
        Optional<Producto> aux = productoServices.findById(id);
        Producto producto = aux.orElseThrow(() ->
                new RuntimeException("El producto no existe")
        );
        model.addAttribute("producto", producto);
        return ("modificarProducto");
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

}