package chess.geneticAlgorithm;

public enum GeneticAlgorithmPhase {

    INITIALIZATION("Init"),
    EPOCH_EVALUATION("Epoch"),
    SELECTION_CROSSOVER_MUTATION("Selection_Crossover_Mutation");


    String phaseName;

    GeneticAlgorithmPhase(String phaseName){
        this.phaseName = phaseName;
    }

    public String getName(){
        return phaseName;
    }

    public static GeneticAlgorithmPhase fromString(String phaseName) {
        if (phaseName != null) {
            for (GeneticAlgorithmPhase goOption : GeneticAlgorithmPhase.values()) {
                if (phaseName.equalsIgnoreCase(goOption.phaseName)) {
                    return goOption;
                }
            }
        }
        return null;
    }
}
