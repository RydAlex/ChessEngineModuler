package chess.database.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "eloengineshistory", schema = "public", catalog = "d5n5g3s0tqt8q5")
public class EloEnginesHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="enginesHistory_seq")
    @SequenceGenerator(name="enginesHistory_seq", sequenceName="eloengineshistory_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Getter
    @Setter
    @Column(name = "old_elo")
    private Integer oldElo;

    @Basic
    @Getter
    @Setter
    @Column(name = "current_elo")
    private Integer currentElo;


    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "engine_name_id")
    private EngineName engineNameId;
}
