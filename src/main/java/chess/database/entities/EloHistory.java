package chess.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "elohistory", schema = "public", catalog = "d6qgde391i0dir")
// @Table(name = "elohistory", schema = "public", catalog = "ducepsca8gpf2") 6 second
// @Table(name = "elohistory", schema = "public", catalog = "d4o36i322pqtbl")
public class EloHistory {

    @PrePersist
    public void init(){
        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="elohistory_seq")
    @SequenceGenerator(name="elohistory_seq", sequenceName="elohistory_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private int id;

    @Basic
    @Column(name = "elo_value", nullable = false)
    private int eloValue;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Basic
    @Column(name = "games_moves", nullable = false)
    private String gamesMoves;

    @Basic
    @Column(name = "cluster_id", nullable = false)
    private Integer clusterId;
}
