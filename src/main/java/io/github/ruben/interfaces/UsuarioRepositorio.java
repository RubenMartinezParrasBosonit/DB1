package io.github.ruben.interfaces;

import io.github.ruben.objetos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

}
