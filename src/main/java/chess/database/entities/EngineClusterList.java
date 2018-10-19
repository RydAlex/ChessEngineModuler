package chess.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "engineclusterlist", schema = "public", catalog = "d4o36i322pqtbl")
public class EngineClusterList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="engineclusterlist_seq")
    @SequenceGenerator(name="engineclusterlist_seq", sequenceName="engineclusterlist_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Basic
    @Column(name = "sequence_no", nullable = false)
    private int sequenceNo;


    @Basic
    @Column(name = "engine_id", nullable = false)
    private int engineByEngineId;

    @Basic
    @Column(name = "cluster_id", nullable = false)
    private int clusterByClusterId;
}
