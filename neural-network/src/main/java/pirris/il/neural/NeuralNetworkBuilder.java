package pirris.il.neural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class NeuralNetworkBuilder {

    List<Neuron> inputLayer;
    List<List<Neuron>> hiddenLayersList;
    List<Neuron> outputLayer;

    
    public NeuralNetworkBuilder setupLayers(int inputNeurons, int outputNeurons, Integer... hiddenNeurons) {
        log.info("Generating neural network");

        this.hiddenLayersList = new ArrayList<>();

        log.info("Generating input layer");
        this.inputLayer = generateLayer(inputNeurons, "INPUT LAYER");

        log.info("Generating hidden layers");
        Arrays
            .asList(hiddenNeurons)
            .stream()
            .map(hiddenLayerSize -> generateLayer(hiddenLayerSize, "HIDDEN LAYER"))
            .forEach(this.hiddenLayersList::add);
        
        log.info("Generating output layer");
        this.outputLayer = generateLayer(outputNeurons, "OUTPUT LAYER");

        this.linkNetwork();

        log.info("Network linking complete");


        return this;
    }


    private List<Neuron> generateLayer(int size, String label){
        log.info("--- creating layer with {} neurons", size);

        List<Neuron> layer = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            layer.add(new Neuron(i, label));
        }

        return layer;
    }

    private void linkNetwork(){
        log.info("Linking neural network");

        AtomicInteger nextLayerIndex = new AtomicInteger(1);
        AtomicInteger currentLayerIndex = new AtomicInteger(0);

        log.info("Linking input layer with first hidden layer");
        this.inputLayer.forEach(neuron -> neuron.setupNextLayer(this.hiddenLayersList.get(0))); // input layer to 1st hidden layer
        this.hiddenLayersList.get(0).forEach(neuron -> neuron.setupPreviousLayer(this.inputLayer)); //1st hidden layer to input

        for (Integer i = 0; i < hiddenLayersList.size() - 1; i++) {
            log.info("Linking hidden layer {} with hidden layer {}", i, nextLayerIndex.get());
            hiddenLayersList.get(i).forEach(neuron -> neuron.setupNextLayer(hiddenLayersList.get(nextLayerIndex.get())));
            hiddenLayersList.get(nextLayerIndex.get()).forEach(neuron -> neuron.setupPreviousLayer(hiddenLayersList.get(currentLayerIndex.get())));
            nextLayerIndex.incrementAndGet();
            currentLayerIndex.incrementAndGet();
        }

        log.info("Linking last hidden layer ({}) with output layer", this.hiddenLayersList.size() - 1);
        this.hiddenLayersList
            .get(this.hiddenLayersList.size() - 1)
            .forEach(neuron -> neuron.setupNextLayer(outputLayer));
        this.outputLayer
            .forEach(neuron -> neuron.setupPreviousLayer(this.hiddenLayersList.get(this.hiddenLayersList.size() - 1)));

    }


    


    
}
