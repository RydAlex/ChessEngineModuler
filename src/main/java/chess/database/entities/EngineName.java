package chess.database.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by aleksanderr on 25/06/17.
 */
@Entity
@Data
public class EngineName {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="engineName_seq")
    @SequenceGenerator(name="engineName_seq", sequenceName="enginename_id_seq", allocationSize=1)
    @Access(AccessType.PROPERTY)
    private Integer id;


    @Basic
    @Column(name = "engine_name")
    private String engineName;


    @Basic
    @Column(name = "type_of_game_used_by_that_engine")
    private String typeOfGameUsedByThatEngine;

}
