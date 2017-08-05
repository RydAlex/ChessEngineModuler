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
public class RandomGameResult {

    @PrePersist
    void createdAt() {
        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="randomGameResult_seq")
    @SequenceGenerator(name="randomGameResult_seq", sequenceName="randomgameresult_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Basic
    @Column(name = "is_win")
    private Boolean isWin;

    @Basic
    @Column(name = "engine_name_id")
    private Integer engineNameId;
}