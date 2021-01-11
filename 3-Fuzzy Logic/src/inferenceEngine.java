
import static java.lang.System.out;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hema
 */
public class inferenceEngine {

    public void solve(float funding, float experience) {
        float veryLowFund;
        float lowFund;
        float mediumFund;
        float highFund;
        float beginnerExp;
        float intermediateExp;
        float expertExp;
        float lowRisk;    
        float normalRisk;
        float highRisk;
        float highRisk1;
        
        float value;
        
        float low_risk;
        float normal_risk;
        float high_risk;

        fuzzificationModule fuzzy = new fuzzificationModule();
        rulesModule rules = new rulesModule();
        defuzzificationModule defuzzy= new defuzzificationModule();
        
        veryLowFund=fuzzy.getVeryLowProjectFunding(funding);
        lowFund = fuzzy.getLowProjectFunding(funding);
        mediumFund=fuzzy.getMediumProjectFunding(funding);
        highFund=fuzzy.getHighProjectFunding(funding);
        
        beginnerExp=fuzzy.getBeginnerProjectExp(experience);
        intermediateExp=fuzzy.getIntermediateProjectExp(experience);
        expertExp=fuzzy.getExpertProjectExp(experience);
        
        lowRisk= rules.getLowRisk(highFund, expertExp);
        normalRisk= rules.getNormalRisk(mediumFund, intermediateExp, beginnerExp);
        highRisk= rules.getHighRisk(lowFund, beginnerExp);
        highRisk1= veryLowFund;
        
        value=defuzzy.getRiskValue(lowRisk, normalRisk, highRisk, highRisk1);
        
        
        low_risk=fuzzy.getLowRisk(value);
        normal_risk = fuzzy.getNormalRisk(value);
        high_risk=fuzzy.getHighRisk(value);

        out.println("Predicted value (Risk)= "+value);
        if (high_risk>low_risk&&high_risk>normal_risk)
            out.println("Risk is high");
        else if (normal_risk>low_risk&&normal_risk>high_risk)
            out.println("Risk is normal");
        else if (low_risk>normal_risk&&low_risk>high_risk)
            out.println("Risk is low");
        else
            out.println("Risk is not determenied");
        
    }

}
