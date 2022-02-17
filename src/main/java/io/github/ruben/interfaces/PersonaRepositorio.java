package io.github.ruben.interfaces;

import io.github.ruben.objetos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepositorio extends JpaRepository<Persona, Integer> {
    public List<Persona> findByUsuario(String usuario);
    public void deleteById(Integer id);
}
