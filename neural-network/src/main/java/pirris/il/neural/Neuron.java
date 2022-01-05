package pirris.il.neural;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Neuron {

    List<Neuron> previousLayer;
    //List<Neuron> nextLayer;
    /* Neurons on witch this neuron is linked with relatives weight */
    Map<Neuron, Float> nextLayerMap;
    int indexInLayer;

    //List<Float> forwardWeights;
    Float bias;
    
    Float outputCache; /* 0....1 range */
    Float error;

    public Neuron(int indexInLayer) {
        this.bias = MathUtility.randomFloatGenerator();
        this.indexInLayer = indexInLayer;

        /*
        if (previousLayer != null) {
            this.previousLayer = previousLayer;

            this.forwardWeights = Stream
                .generate(MathUtility::randomFloatGenerator)
                .limit(previousLayer.size())
                .collect(Collectors.toList()); 
            
        }
        */
    }

    public void setupNextLayer(List<Neuron> nextLayerNeurons) { // not in output neuron
        nextLayerMap = new HashMap<>();

        nextLayerNeurons
            .forEach(neuron -> nextLayerMap.put(neuron, MathUtility.randomFloatGenerator()));
    }


    public void updateOutput(float newOutput){
        this.outputCache = newOutput;
    }

    public Float generateOutput(){
        float sum = 0;
        if (outputCache == null) {
            if (previousLayer != null) {
                
                sum = (float) previousLayer
                .stream()
                .mapToDouble(previousNeuron -> previousNeuron.getNextLayerMap().get(this) * previousNeuron.generateOutput())
                .sum();

                sum = sum + this.bias;
            }

            this.outputCache = MathUtility.sig(sum);
        }

        return outputCache;
    }

    public void reset() {
        this.outputCache = null;
        this.error = null;
    }

    public void generateError(int label) // errore neurone output
    {
        int expected = label == this.indexInLayer ? 1 : 0;
        this.error = MathUtility.sig_p(this.generateOutput()) * (expected - generateOutput());
    }

    public void generateError() //errore hidden neuron
    {
        this.error = 0.0f;

        this.nextLayerMap
            .entrySet()
            .stream()
            .forEach(entrySet -> error += entrySet.getKey().getError() * entrySet.getValue());

        this.error =  MathUtility.sig_p(this.getOutputCache()) * this.error;
    }



}
