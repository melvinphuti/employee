/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.web.app.employee.view.bean;

/**
 *
 * @author User1
 */
public class Statistics {
    
    private final int numberOfFemales;
    private final int numberOfMales;
    private final int numberOfBlackAfricans;
    private final int numberOfWhites;
    private final int numberOfIndianOrAsians;
    private final int numberOfNoneDominants;
    private final int numberOfBackEndDevelopers;
    private final int numberOfFrontEndDevelopers;
    private final int numberOfSeniorProjectManagers;
    private final int numberOfJuniorProjectManagers;

    public Statistics(int numberOfFemales, int numberOfMales, int numberOfBlackAfricans, int numberOfWhites, int numberOfIndianOrAsians, int numberOfNoneDominants, int numberOfBackEndDevelopers, int numberOfFrontEndDevelopers, int numberOfSeniorProjectManagers, int numberOfJuniorProjectManagers) {
        this.numberOfFemales = numberOfFemales;
        this.numberOfMales = numberOfMales;
        this.numberOfBlackAfricans = numberOfBlackAfricans;
        this.numberOfWhites = numberOfWhites;
        this.numberOfIndianOrAsians = numberOfIndianOrAsians;
        this.numberOfNoneDominants = numberOfNoneDominants;
        this.numberOfBackEndDevelopers = numberOfBackEndDevelopers;
        this.numberOfFrontEndDevelopers = numberOfFrontEndDevelopers;
        this.numberOfSeniorProjectManagers = numberOfSeniorProjectManagers;
        this.numberOfJuniorProjectManagers = numberOfJuniorProjectManagers;
    }

    public int getNumberOfFemales() {
        return numberOfFemales;
    }

    public int getNumberOfMales() {
        return numberOfMales;
    }

    public int getNumberOfBlackAfricans() {
        return numberOfBlackAfricans;
    }

    public int getNumberOfWhites() {
        return numberOfWhites;
    }

    public int getNumberOfIndianOrAsians() {
        return numberOfIndianOrAsians;
    }

    public int getNumberOfNoneDominants() {
        return numberOfNoneDominants;
    }

    public int getNumberOfBackEndDevelopers() {
        return numberOfBackEndDevelopers;
    }

    public int getNumberOfFrontEndDevelopers() {
        return numberOfFrontEndDevelopers;
    }

    public int getNumberOfSeniorProjectManagers() {
        return numberOfSeniorProjectManagers;
    }

    public int getNumberOfJuniorProjectManagers() {
        return numberOfJuniorProjectManagers;
    }
    
    
    
}
