/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hema
 */
public class rulesModule {
    
    public float getLowRisk(float highFunding, float expertExperience){
        return Math.max(highFunding, expertExperience);
    }
    public float getNormalRisk(float mediumFunding, float intermediateExperience,float beginnerExperience){
        return Math.min(mediumFunding, Math.max(intermediateExperience, beginnerExperience));
    }
    public float getHighRisk(float lowFunding,float beginnerExperience){
        return Math.min(lowFunding,beginnerExperience);
    }
    
}
