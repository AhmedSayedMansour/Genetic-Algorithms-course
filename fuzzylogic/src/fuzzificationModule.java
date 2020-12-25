/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hema
 */
public class fuzzificationModule {

    public float getValFromTwoPoints(float x1, float y1, float x2, float y2, float val) {
        float slope =(y2 - y1) / (x2 - x1);
        float b = y1 - slope * x1;
        float value = slope * val + b;
        return Math.abs(value);
    }

    public float getVeryLowProjectFunding(float point) {
        float value=0;
        if (point >= 0 && point <= 30) {
            if (point>=0&&point<=10)
                value=1;
            else if (point>10&&point<30)
                value= this.getValFromTwoPoints(10,1,30,0,point);
        }
        return value;
    }
    
    
    public float getLowProjectFunding(float point) {
        float value=0;
        if (point >= 10 && point <= 60) {
            if (point>10&&point<30)
                value= this.getValFromTwoPoints(10,0,30,1,point);
            else if (point>=30&&point<=40)
                value=1;
            else if (point>40&&point<60)
                value= this.getValFromTwoPoints(40,1,60,0,point);
        }
        return value;
    }
    
    
    public float getMediumProjectFunding(float point) {
        float value=0;
        if (point >= 40 && point <= 90 ) {
            if (point>40&&point<60)
                value= this.getValFromTwoPoints(40,0,60,1,point);
            else if (point>=60&&point<=70)
                value=1;
            else if (point>70&&point<90)
                value= this.getValFromTwoPoints(70,1,90,0,point);
        }
        return value;
    }
    
    public float getHighProjectFunding(float point) {
        float value=0;
        if (point >= 70 && point <= 100) {
            if (point>70&&point<90)
                value= this.getValFromTwoPoints(70,0,90,1,point);
            else if (point>=90&&point<=100)
                value=1;
        }
        return value;
    }
    
    
    public float getBeginnerProjectExp(float point) {
        float value=0;
        if (point >= 0 && point <= 30) {
            if (point>0&&point<15)
                value= this.getValFromTwoPoints(0,0,15,1,point);
            else if (point>15&&point<30)
                value= this.getValFromTwoPoints(15,1,30,0,point);
        }
        return value;
    }
    
    public float getIntermediateProjectExp(float point) {
        float value=0;
        if (point >= 15 && point <= 45) {
            if (point>15&&point<30)
                value= this.getValFromTwoPoints(15,0,30,1,point);
            else if (point>30&&point<45)
                value= this.getValFromTwoPoints(30,1,45,0,point);
        }
        return value;
    }
    
    public float getExpertProjectExp(float point) {
        float value=0;
        if (point >= 30 && point <= 60) {
            if (point>30&&point<60)
                value= this.getValFromTwoPoints(30,0,60,1,point);
            else 
                value= 1;
        }
        return value;
    }
    
    
     public float getHighRisk(float point) {
        float value=0;
        if (point >= 0 && point <= 50) {
            if (point>0&&point<25)
                value= this.getValFromTwoPoints(0,0,25,1,point);
            else if (point>25&&point<50)
                value= this.getValFromTwoPoints(25,1,50,0,point);
        }
        return value;
    }
     
     public float getNormalRisk(float point) {
        float value=0;
        if (point >= 25 && point <= 75) {
            if (point>25&&point<50)
                value= this.getValFromTwoPoints(25,0,50,1,point);
            else if (point>50&&point<75)
                value= this.getValFromTwoPoints(50,1,75,0,point);
        }
        return value;
    }
     
    public float getLowRisk(float point) {
        float value=0;
        if (point >= 50 && point <= 100) {
            if (point>50&&point<100)
                value= this.getValFromTwoPoints(50,0,100,1,point);
            else 
                value= 1;
        }
        return value;
    }
}
