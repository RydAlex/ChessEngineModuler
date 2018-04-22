package chess.database.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "enginename", schema = "public", catalog = "d5n5g3s0tqt8q5")
public class EngineName {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="engineName_seq")
    @SequenceGenerator(name="engineName_seq", sequenceName="enginename_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Getter
    @Setter
    @Column(name = "engine_name")
    private String engineName;

    @Basic
    @Getter
    @Setter
    @Column(name = "type_of_game")
    private String typeOfGame;

    @Basic
    @Getter
    @Setter
    @Column(name = "current_elo")
    private Integer currentElo;
}
