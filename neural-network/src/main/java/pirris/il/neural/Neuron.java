package pirris.il.neural;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Neuron {

    List<Neuron> previousLayer;
    List<Neuron> nextLayer;
    int indexInLayer;

    List<Float> weights;
    Float bias;
    
    Float outputCache;

    public Neuron(int indexInLayer, List<Neuron> previousLayer) {
        this.bias = MathUtility.randomFloatGenerator();
        this.indexInLayer = indexInLayer;

        if (previousLayer != null) {
            this.previousLayer = previousLayer;

            this.weights = Stream
                .generate(MathUtility::randomFloatGenerator)
                .limit(previousLayer.size())
                .collect(Collectors.toList()); 
        }
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
                .mapToDouble(neuron -> neuron.getWeights().get(this.indexInLayer)*neuron.generateOutput())
                .sum();

                sum = sum + this.bias;
            }

            this.outputCache = MathUtility.sig(sum);
        }

        return outputCache;
    }




}
