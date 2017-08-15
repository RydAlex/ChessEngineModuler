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
public class VotingStats {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="votingstats_seq")
	@SequenceGenerator(name="votingstats_seq", sequenceName="votingstats_id_seq", allocationSize=1)
	@Access(AccessType.PROPERTY)
	private Integer id;

	@Basic
	@Column(name = "vote_pack_name")
	private String votePackName;

	@Basic
	@Column(name = "vote_amount")
	private Integer voteAmount;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "engine_name_id")
	private EngineName engineNameId;
}
