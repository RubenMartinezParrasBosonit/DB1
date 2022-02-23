package io.github.ruben.profesor.domain;

import io.github.ruben.persona.domain.Persona;
import io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator;
import io.github.ruben.student.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idProfesor")
    @GenericGenerator(
            name = "idProfesor",
            strategy = "io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PROF"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
            }
    )
    @Column(name = "id_profesor")
    private String idProfesor;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "fk_persona")
    private Persona idPersona;

    private String comments;

    @NonNull
    private String branch;

    @OneToMany(mappedBy = "idProfesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;


}
