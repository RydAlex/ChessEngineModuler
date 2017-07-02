package AMQPManagment.DatabaseConnections.Entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by aleksanderr on 25/06/17.
 */
@Entity
@Data
public class CurrentElo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="currentElo_seq")
    @SequenceGenerator(name="currentElo_seq", sequenceName="currentelo_id_seq", allocationSize=20)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "elo_value", nullable = false)
    private Integer eloValue;

    @Basic
    @Column(name = "engine_name_id")
    private String engineNameId;
}
