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
@Table(name = "cluster", schema = "public", catalog = "dbdbt2hu9ketmn")
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
    @Column(name="white_games", nullable = false)
    private Integer whiteGames;

    @Basic
    @Column(name="black_games", nullable = false)
    private Integer blackGames;

    @Basic
    @Column(name = "elo_score", nullable = false)
    private Integer eloScore;

    public Cluster(int epochNumber, int eloScore){
        this.epochNumber = epochNumber;
        this.eloScore = eloScore;
    }

    public Cluster(int epochNumber, int eloScore, int whiteGames, int blackGames){
        this.epochNumber = epochNumber;
        this.eloScore = eloScore;
        this.whiteGames = whiteGames;
        this.blackGames = blackGames;
    }
}
