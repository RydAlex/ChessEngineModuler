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
public class Score {

    @PrePersist
    void createdAt() {
        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="score_seq")
    @SequenceGenerator(name="score_seq", sequenceName="score_id_seq", allocationSize=20)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Basic
    @Column(name = "score", nullable = false)
    private Integer score;

    @Basic
    @Column(name = "engine_name_id")
    private Integer engineNameId;
}
