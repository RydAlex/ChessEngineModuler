package chess.ui.data;

import lombok.Data;

import java.util.List;

@Data
public class NameWithRuleHolder {
	List<String> names;
	String rule;
}
