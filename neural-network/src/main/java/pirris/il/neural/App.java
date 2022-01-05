package pirris.il.neural;

import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Neuron a = new Neuron(12);
        Neuron b = new Neuron(12);


        


        System.out.println( Objects.equals(a,b));
    }
}
