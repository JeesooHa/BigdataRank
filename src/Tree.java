

import java.io.*;
import java.util.*;

public class Tree {
	public Node buildTree(ArrayList<Record> records, Node root, LearningSet learningSet) {
		
		int bestAttribute = -1;
		double bestGain = 0;
		
		double[] result = new double[2];
		result[0] = Entropy.calculateEntropy(root.getData())[0];
		result[1] = Entropy.calculateEntropy(root.getData())[1];
		
		//System.out.println(	result[0]+"		"+result[1]);
		root.setEntropy(result[0]);
		root.setResult(result[1]);
		
		
		for(int i = 1; i < HousePrices.NUM_ATTRS - 1; i++) {
			
			if(!HousePrices.isAttributeUsed(i)) {
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				
				for(int j = 1; j < HousePrices.NUM_ATTRS - 1; j++) {
					ArrayList<Record> subset = subset(root, i, j);
					setSizes.add(subset.size());
					
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
		}
		
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
