package chess.database.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by aleksanderr on 25/06/17.
 */
@Entity
@Data
public class EloGamesHistory {

    @PrePersist
    void createdAt() {
        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="eloGameHistory_seq")
    @SequenceGenerator(name="eloGameHistory_seq", sequenceName="elogameshistory_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Basic
    @Column(name = "old_elo", nullable = false)
    private Integer oldElo;

    @Basic
    @Column(name = "is_win")
    private Boolean isWin;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "engine_name_id")
    private EngineName engineNameId;

}
