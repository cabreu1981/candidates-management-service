package com.cabreu.candidatesmanagementservice.domain.model;


public class ClientMetrics {
    private double averageAge;
    private double standardDeviation;

    public ClientMetrics(double averageAge, double standardDeviation) {
        this.averageAge = averageAge;
        this.standardDeviation = standardDeviation;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
