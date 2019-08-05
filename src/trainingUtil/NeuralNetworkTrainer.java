package trainingUtil;

import maine.*;
import trainingObjects.TrainingSample;

/**
 * class containing methods that defines training
 */
public class NeuralNetworkTrainer
{
	double learningRate;
	
	/**
	 * default constructor
	 * @param d - learning rate
	 */
	public NeuralNetworkTrainer(double d)
	{
		this.learningRate = d;
	}
	
	/**
	 * do one backprop iteration
	 * @param nn - neural network to be used
	 * @param ts - training sample to be applied
	 * @return error rate as an object
	 */
	public TrainResult iterateOnce(NeuralNetwork nn, TrainingSample ts)
	{
		double[] input = ts.getInput();
		double[] output = ts.getOutput();
		
		//obtain LayerOutputList from foward propagation
		LayerOutputList lol = nn.fowardPropagate(input);
		//obtain GradientList by calculating the error gradient
		GradientList gl = nn.calculateErrorGradient2(output, lol);
		//create GradientPair and perform back propagation
		GradientPair gp = new GradientPair(lol, gl);
		WeightUpdateMatrixCollection wumc = gp.gradientDescent();
		//perform gradient descent
		nn.updateWeights3(wumc, this.learningRate);
		
		//result of the neural network
		double nnResult = lol.getOutput()[0];
		double target = output[0];
		//second value
		double nnRes2 = lol.getOutput()[1];
		double target2 = output[1];
		//the difference between output and target values
		//double error = Math.abs(target - nnResult);
		double error = (Math.abs(target - nnResult) + Math.abs(target2 - nnRes2)) / 2;
		
		//debug
		//System.out.println("result from network: " + nnResult + " , target output: " + target + " , error:" + error);
		
		boolean correct;
		//if correct output[0] is 1.0(higher than output[1] = -1.0)(positive sample)
		if(target > target2)
		{
			correct = nnResult > nnRes2;
		}
		else//if reverse(negative sample)
		{
			correct = nnResult < nnRes2;
		}
		
		return new TrainResult(error, correct);
	}
	
	/**
	 * train a neural network in a specific batch
	 * @param nn - neural network to be used
	 * @param samples - array of samples
	 * @return report of training
	 */
	public TrainBatchReport trainBatch(NeuralNetwork nn, TrainingSample[] samples)
	{
		int len = samples.length;
		double totalError = 0;
		int totalCorrect = 0;
		
		for(int ct=0; ct<len; ct++)
		{
			//propogate and record error
			TrainResult tr = this.iterateOnce(nn, samples[ct]);
			//count the error
			totalError += tr.getAvgError();
			if(tr.getCorrect())
			{
				totalCorrect++;
			}
		}
		
		//calculate average error
		double avgError = totalError / len;
		return new TrainBatchReport(avgError, totalCorrect, len);
	}
}
