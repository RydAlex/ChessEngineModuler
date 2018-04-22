package chess.database.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
@Table(name = "battle", schema = "public", catalog = "d5n5g3s0tqt8q5")
public class Battle {

    @PrePersist
    void createdAt() {
        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="battle_seq")
    @SequenceGenerator(name="battle_seq", sequenceName="battle_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Getter
    @Setter
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Basic
    @Getter
    @Setter
    @Column(name = "gameplay_string")
    private String gameplayString;

    @Basic
    @Getter
    @Setter
    @Column(name = "win_of_first")
    private Boolean winOfFirst;


    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "engine_name_one_id")
    private EngineName engineNameOneId;


    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "engine_name_two_id")
    private EngineName engineNameTwoId;
}
