

import java.io.*;
import java.util.*;

public class Tree {
	public Node buildTree(ArrayList<Record> records, Node root, LearningSet learningSet) {
		
		int bestAttribute = -1;
		double bestGain = 0;
		
		double[] result = new double[2];
		result = Entropy.calculateEntropy(root.getData());

		if(result[1] == -1){ //no data
			result[1] = root.getParent().getResult();		
		}
		root.setEntropy(result[0]);
		root.setResult(result[1]);	

		if(root.getEntropy() == 0) {	
			//System.out.println(	result[0]+"		"+result[1]);
			return root;	
		}
		if(root.getEntropy() < -0.7) {	
			//System.out.prin-tln(	result[0]+"		"+result[1]);
			return root;	
		}

			
		for(int i = 1; i < HousePrices.NUM_ATTRS - 1; i++) {
			
			System.out.println(i);
			
			if(!HousePrices.isAttributeUsed(i)) {
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				ArrayList<Double> entropy_tmp = new ArrayList<Double>();
						
				for(int j = 1; j < HousePrices.NUM_ATTRS - 1; j++) {
					
					System.out.println("  "+j);
					
					//if(!HousePrices.isAttributeUsed(j)) {
					ArrayList<Record> subset = subset(root, i, j);
					setSizes.add(subset.size());
					
					if(HousePrices.Threshold.containsKey(i)){	//numeric
						String tmp ="";
					
						for(String val : HousePrices.A.get(i).keySet()){	//기준
							int up_count=0;
							int dn_count=0;
							for(int k = 0; k < records.size(); k++) {// all input data
								Record record = records.get(k);			
										
								for(String s : HousePrices.A.get(i).keySet())	//real data
									if(HousePrices.A.get(i).get(s) == record.getAttributes().get(i).getValue())
										tmp = s;
								
								//data     기준
								if(Integer.parseInt(tmp) <Integer.parseInt(val) )
								{dn_count++;}
								else if(Integer.parseInt(tmp)>=Integer.parseInt(val) )
								{up_count++;}
								double prob_up = up_count / (up_count + dn_count);
								double prob_down = dn_count / (up_count + dn_count);
								
								entropy_tmp.add(-prob_up * (Math.log(prob_up) / Math.log(2)) -prob_down * (Math.log(prob_down) / Math.log(2)));	
								
								
							}
						}
						
						for(int n = 0;n<entropy_tmp.size();n++){
							if(entropy_tmp.get(n)<entropy)
								entropy = entropy_tmp.get(n);	
						}
						entropies.add(entropy);
					}
					
					if(HousePrices.Threshold.containsKey(j)){	//numeric
						String tmp ="";
					
						for(String val : HousePrices.A.get(j).keySet()){	//기준
							int up_count=0;
							int dn_count=0;
							for(int k = 0; k < records.size(); k++) {// all input data
								Record record = records.get(j);			
										
								for(String s : HousePrices.A.get(j).keySet())	//real data
									if(HousePrices.A.get(j).get(s) == record.getAttributes().get(j).getValue())
										tmp = s;
								
								//data     기준
								if(Integer.parseInt(tmp) <Integer.parseInt(val) )
								{dn_count++;}
								else if(Integer.parseInt(tmp)>=Integer.parseInt(val) )
								{up_count++;}
								double prob_up = up_count / (up_count + dn_count);
								double prob_down = dn_count / (up_count + dn_count);
								
								entropy_tmp.add(-prob_up * (Math.log(prob_up) / Math.log(2)) -prob_down * (Math.log(prob_down) / Math.log(2)));						
								
							}
						}
						
						for(int n = 0;n<entropy_tmp.size();n++){
							if(entropy_tmp.get(n)<entropy)
								entropy = entropy_tmp.get(n);	
						}
						entropies.add(entropy);
					}					
					
					if(subset.size() != 0) {
						entropy = Entropy.calculateEntropy(subset)[0];
						entropies.add(entropy);
					}
				}
				
				double gain = Entropy.calculateGain(root.getEntropy(), entropies, setSizes, root.getData().size());
				
				if(gain > bestGain) {
					bestAttribute = i;
					bestGain = gain;
				}
				
			}
		}
		
		
		/*
		 * for(int i = 1; i < HousePrices.NUM_ATTRS - 1; i++) {
			
			if(!HousePrices.isAttributeUsed(i)) {
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				ArrayList<Double> entropy_tmp = new ArrayList<Double>();
				
				
				if(HousePrices.Threshold.containsKey(i)){	//numeric
					String tmp ="";
				
					for(String val : HousePrices.A.get(i).keySet()){	//기준
						int up_count=0;
						int dn_count=0;
						for(int j = 0; j < records.size(); j++) {// all input data
							Record record = records.get(j);			
									
							for(String s : HousePrices.A.get(i).keySet())	//real data
								if(HousePrices.A.get(i).get(s) == record.getAttributes().get(i).getValue())
									tmp = s;
							
							//data     기준
							if(Integer.parseInt(tmp) <Integer.parseInt(val) )
							{dn_count++;}
							else if(Integer.parseInt(tmp)>=Integer.parseInt(val) )
							{up_count++;}
							double prob_up = up_count / (up_count + dn_count);
							double prob_down = dn_count / (up_count + dn_count);
							
							entropy_tmp.add(-prob_up * (Math.log(prob_up) / Math.log(2)) -prob_down * (Math.log(prob_down) / Math.log(2)));	
							
							
						}
					}
					
					for(int n = 0;n<entropy_tmp.size();n++)
					{
						if(entropy_tmp.get(n)>entropy)
							entropy = entropy_tmp.get(n);	
					}
					entropies.add(entropy);
				}
				else{	//categorical data
					for(int j = 1; j < HousePrices.NUM_ATTRS - 1; j++) {
						ArrayList<Record> subset = subset(root, i, j);
						setSizes.add(subset.size());
						
						if(subset.size() != 0) {
							entropy = Entropy.calculateEntropy(subset)[0];
							entropies.add(entropy);
						}
					}
						
				}
				
				double gain = Entropy.calculateGain(root.getEntropy(), entropies, setSizes, root.getData().size());
				
				if(gain > bestGain) {
					bestAttribute = i;
					bestGain = gain;
				}
			}
		}
		 * */
		/*
		if(bestAttribute != -1) {
			
			int setSize = HousePrices.setSize(bestAttribute);
			root.setTestAttribute(new DiscreteAttribute(HousePrices.attrMap.get(bestAttribute), 0));
			root.children = new Node[setSize];
			root.setUsed(true);
			HousePrices.usedAttributes.add(bestAttribute);
			
			for (int j = 0; j< setSize; j++) {
				root.children[j] = new Node();
				root.children[j].setParent(root);
				root.children[j].setData(subset(root, bestAttribute, j));
				root.children[j].getTestAttribute().setName(HousePrices.getLeafNames(bestAttribute, j));
				root.children[j].getTestAttribute().setValue(j);
			}

			for (int j = 0; j < setSize; j++) {
				buildTree(root.children[j].getData(), root.children[j], learningSet);
			}
			root.setData(null);
		}
		else {
			return root;
		}*/
		
		return root;
	}
	
	public ArrayList<Record> subset(Node root, int attr, int value) {
		ArrayList<Record> subset = new ArrayList<Record>();
		
		for(int i = 0; i < root.getData().size(); i++) {
			Record record = root.getData().get(i);
			
			if(record.getAttributes().get(attr).getValue() == value) {
				subset.add(record);
			}
		}
		return subset;
	}

}
