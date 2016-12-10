

import java.util.ArrayList;

public class Entropy {	
	public static double[] calculateEntropy(ArrayList<Record> data) {
		double[] result = new double[2]; 
		double	entropy = 0;
		
		int m_c = 0;
		int m_i = -1;
		
		if(data.size() == 0) {	//noting to do
			result[0] = 0;
			result[1] = -1;
			return result;
		}
		
		//System.out.println(HousePrices.setSize(80));		
		for(int i = 0; i < HousePrices.setSize(80); i++) {
			int count = 0;
			for(int j = 0; j < data.size(); j++) {
				Record record = data.get(j);
				if(record.getAttributes().get(record.getAttributes().size()-1).getValue() == i) {	
					count++;
				}
			}
			if(m_c < count){	//priority result
				m_i = i;
				m_c = count;
			}
		
			double probability = count / (double)data.size();
			if(count > 0) {
				entropy += -probability * (Math.log(probability) / Math.log(2));	
			}
		}
		
		result[0] = entropy;
		result[1] = m_i;
		
		return result;
	}
	
	public static double calculateGain(double rootEntropy, ArrayList<Double> subEntropies, ArrayList<Integer> setSizes, int data) {
		double gain = rootEntropy; 
		
		for(int i = 0; i < subEntropies.size(); i++) {
			gain += -((setSizes.get(i) / (double)data) * subEntropies.get(i));
		}
		
		return gain;
	}
}
