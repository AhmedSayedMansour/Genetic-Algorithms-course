/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hema
 */
public class defuzzificationModule {
    
    public float getRiskValue(float low , float normal, float high1 , float high2){
        float value=0;
        if (low>0&&normal>0){
            value=(normal*50+low*75)/(low+normal);
        }
        else if (high1>0&&normal>0){
            value=(normal*50+high1*25)/(high1+normal);
        }
        else if (high2>0&&normal>0){
            value=(normal*50+high2*25)/(high2+normal);
        }
        return value;
    }
    
}
