package io.github.ruben.controladores;

import io.github.ruben.interfaces.UsuarioRepositorio;
import io.github.ruben.objetos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorUsuario {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioPorId(@PathVariable String id) throws Exception{
        return usuarioRepositorio.findById(id).orElseThrow(() -> new Exception("No encontrado"));
    }

    @PostMapping
    public Usuario aniadirUsuario(@RequestBody Usuario usuario){
        System.out.println("En el post");
        usuarioRepositorio.save(usuario);
        return usuario;
    }
}
