package io.github.ruben.objetos;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    String id;

    @NonNull
    String nombreUsuario;

    int edad;
    String ciudad;
}
