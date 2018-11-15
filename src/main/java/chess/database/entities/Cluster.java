package chess.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cluster", schema = "public", catalog = "d4o36i322pqtbl")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getTopClusters",
                query = "SELECT * FROM cluster WHERE epoch_number = :epochNumber ORDER BY elo_score DESC LIMIT :bestLimit ;",
                resultClass = Cluster.class),

        @NamedNativeQuery(
                name = "getAllClusters",
                query = "SELECT * FROM cluster WHERE epoch_number = :epochNumber ;",
                resultClass = Cluster.class)
})
public class Cluster implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cluster_seq")
    @SequenceGenerator(name="cluster_seq", sequenceName="cluster_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "epoch_number", nullable = false)
    private Integer epochNumber;

    @Basic
    @Column(name = "elo_score", nullable = false)
    private Integer eloScore;
}
