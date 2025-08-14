package org.example.model;

public class TrainingType {

    public TrainingType() {}

    public TrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public String getTrainingTypeName() { return trainingTypeName; }
    public void setTrainingTypeName(String trainingTypeName) { this.trainingTypeName = trainingTypeName; }

    private String trainingTypeName;

}
