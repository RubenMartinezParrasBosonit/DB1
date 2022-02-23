package io.github.ruben.student.domain;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.profesor.domain.Profesor;
import io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator;
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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idStudent")
    @GenericGenerator(
            name = "idStudent",
            strategy = "io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "STUD"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
            }
    )
    @Column(name = "id_student")
    private String idStudent;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "fk_persona")
    private Persona idPersona;

    @NonNull
    private Integer numHoursWeek;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_profesor")
    private Profesor idProfesor;

    @NonNull
    private String branch;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "student_estudio",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "estudio_id")}
    )
    List<AlumnosEstudios> estudios;
}
