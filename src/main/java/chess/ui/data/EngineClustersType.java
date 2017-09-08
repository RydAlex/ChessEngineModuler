package chess.ui.data;

import java.util.LinkedList;
import java.util.List;

import static chess.ui.data.EngineLevel.*;

public enum EngineClustersType {
	FourStrong			("FourStrong", 			STRONG, STRONG, STRONG, STRONG),
	BlackSheep			("BlackSheep", 			STRONG, STRONG, STRONG, WEAK),
	FourAverage			("FourAverage", 		AVERAGE, AVERAGE, AVERAGE, AVERAGE),
	FourWeak			("FourWeak", 			WEAK, WEAK, WEAK, WEAK),
	WeaklingsWithLeader	("WeaklingsWithLeader", WEAK, WEAK, WEAK, STRONG);

	EngineLevel e1, e2, e3, e4;
	String name;
	EngineClustersType(String name, EngineLevel e1, EngineLevel e2, EngineLevel e3, EngineLevel e4){
		this.name = name;
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
		this.e4 = e4;
	}

	public static EngineClustersType findByName(String name){
		for(EngineClustersType type: values()){
			if(type.name.equals(name)){
				return type;
			}
		}
		return null;
	}

	public List<EngineLevel> getEnginesLevels(){
		List<EngineLevel> engineLevels = new LinkedList<>();
		engineLevels.add(e1);
		engineLevels.add(e2);
		engineLevels.add(e3);
		engineLevels.add(e4);
		return engineLevels;
	}
}
