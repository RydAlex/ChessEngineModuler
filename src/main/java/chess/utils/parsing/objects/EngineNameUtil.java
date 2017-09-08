package chess.utils.parsing.objects;

import chess.amqp.message.EngineEloPair;

import java.util.List;

public class EngineNameUtil {

	public static Integer changeRuleToNumber(String rule){
		return Integer.parseInt(rule.split("_")[1]);
	}

	public static String fetchNumberFromRule(String rule){
		return rule.split("_")[1];
	}

	public static String fetchRuleTypeFromRule(String rule){
		return rule.split("_")[0];
	}

	public static String changeNumberToGameRule(String ruleValue){
		return Integer.parseInt(ruleValue) > 100 ? ("timeout_" + ruleValue) : ("depth_" + ruleValue);
	}

	public static String constructClusterNameFromEngineName(List<String> engineNames){
		String clusterName = "";
		for(String name : engineNames ){
			if(clusterName.isEmpty()){
				clusterName = name;
			} else {
				clusterName = clusterName + "_" + name;
			}
		}
		return clusterName;
	}

	public static String constructClusterNameFromEloPairs(List<EngineEloPair> engineNames, String rule){
		String clusterName = "";
		for(EngineEloPair eloPair : engineNames) {
			if(clusterName.isEmpty()){
				clusterName = eloPair.getEngineName();
			} else {
				clusterName = clusterName + "_" + eloPair.getEngineName();
			}
		}
		if(rule.contains("_")){
			return clusterName + "_" + rule;
		} else {
			return clusterName + "_" + changeNumberToGameRule(rule);
		}
	}

	public static String constructClusterNameFromEngineName(List<String> engineNames, String rule){
		String clusterName = "";
		for(String name : engineNames ){
			if(clusterName.isEmpty()){
				clusterName = name;
			} else {
				clusterName = clusterName + "_" + name;
			}
		}
		if(rule.contains("_")){
			return clusterName + "_" + rule;
		} else {
			return clusterName + "_" + changeNumberToGameRule(rule);
		}
	}
}
