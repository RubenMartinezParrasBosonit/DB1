package io.github.ruben.alumnosEstudios.domain;

import io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator;
import io.github.ruben.student.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "estudios")
@Data
@NoArgsConstructor
public class AlumnosEstudios {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idEstudio")
    @GenericGenerator(
            name = "idEstudio",
            strategy = "io.github.ruben.shared.sequences.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EST"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
            }
    )
    @Column(name = "id_estudio")
    private String idEstudio;

    @ManyToMany(mappedBy = "estudios")
    private List<Student> students;

    @NonNull
    private String estudio;

    private String comments;

    @NonNull
    private Date initialDate;

    private Date finishDate;
}
