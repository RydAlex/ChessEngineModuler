package chess.database.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "engine", schema = "public", catalog = "d4o36i322pqtbl")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getEnginesWhichAreUsedInCluster",
                query = "SELECT engine.id, engine.engine_name FROM engine engine, engineclusterlist ecl, cluster cluster" +
                        "  WHERE ecl.engine_id = engine.id AND ecl.cluster_id = cluster.id AND cluster.id= :clusterNumber ORDER BY sequence_no",
                resultClass = Engine.class),
        @NamedNativeQuery(
                name = "getEngineByName",
                query = "SELECT * FROM engine WHERE engine_name = :engineName",
                resultClass = Engine.class)
})
public class Engine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="engine_seq")
    @SequenceGenerator(name="engine_seq", sequenceName="engine_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "engine_name", nullable = false, length = 200)
    private String engineName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }
}
