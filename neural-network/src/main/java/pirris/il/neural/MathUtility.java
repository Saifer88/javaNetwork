package pirris.il.neural;

import java.util.Random;

public class MathUtility {


    public static float sig(float x){
        return 1.0f / (1.0f + (float)Math.exp(-x)); 
    } 
    

    public static float sig_p(float sig){
        return sig * (1 - sig);
    }

    public static float randomFloatGenerator(){
        return Float.valueOf((float)Math.random());
    }

    public static int randomIntGenerator(){
        Random random = new Random();
        return random.nextInt();
    }
}
