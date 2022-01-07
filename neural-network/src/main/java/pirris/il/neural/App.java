package pirris.il.neural;

import java.util.Objects;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.w3c.dom.html.HTMLIsIndexElement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NeuralNetworkBuilder networkBuilder = new NeuralNetworkBuilder();


        networkBuilder.setupLayers(1, 1 ,1 ,1);

        Neuron outputNeuron = networkBuilder.getOutputLayer().get(0);

        Neuron hiddenNeuron = outputNeuron.getPreviousLayer().get(0);

        System.out.println(hiddenNeuron.getNextLayerMap().get(outputNeuron));


        System.out.println(networkBuilder.getOutputLayer().get(0).generateOutput());
    }
}
