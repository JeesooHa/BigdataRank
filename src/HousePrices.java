import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HousePrices {
	public static final int NUM_ATTRS = 81;
	public static int[] num_attribute = new int[NUM_ATTRS];
	
	//////////////////////////////////
	//data #0~80
	//Numeric Data Threshold(feet data) - median value
	static int Thr_LotFrontage = 88;	//#3 NA => -1
	static int Thr_LotArea = 10625;		//#4
	
	static int Thr_MasVnrArea = 250;	//#26 NA => -1
	
	static int Thr_BsmtFinSF1 = 650; 	//#34
	static int Thr_BsmtFinSF2 = 345; 	//#36
	static int Thr_BsmtUnfSF = 708;		//#37
	static int Thr_TotalBsmtSF = 1144;	//#38
	
	static int Thr_1stFlrSF = 1190;		//#43
	static int Thr_2ndFlrSF  = 813;		//#44
	static int Thr_LowQualFinSF  = 384;	//#45
	static int Thr_GrLivArea = 1557;	//#46

	static int Thr_GarageArea = 550;	//#62
	static int Thr_WoodDeckSF = 130;	//#66
	static int Thr_OpenPorchSF = 114;	//#67
	static int Thr_EnclosedPorch = 148;	//#68
	static int Thr_3SsnPorch = 180;		//#69
	static int Thr_ScreenPorch = 189;	//#70	
	static int Thr_PoolArea = 519;		//#71
	static int Thr_MiscVal = 620;		//#75
	static int price_init = 35000;
	//////////////////////////////////////
	
	public static ArrayList<String> result = new ArrayList<String>();
	public static ArrayList<String> attrMap = new ArrayList<String>();
	public static ArrayList<Integer> usedAttributes = new ArrayList<Integer>();
	static HashMap<Integer, Integer> Threshold = new HashMap<Integer, Integer>();
	static ArrayList<HashMap<String, Integer>> A = new ArrayList<HashMap<String, Integer>>();
	
	public void Initialize(){		
		//Threshold add	
		Threshold.put(3,Thr_LotFrontage);
		Threshold.put(4,Thr_LotArea);		
		Threshold.put(26,Thr_MasVnrArea);	
		Threshold.put(34,Thr_BsmtFinSF1);
		Threshold.put(36,Thr_BsmtFinSF2);
		Threshold.put(37,Thr_BsmtUnfSF);
		Threshold.put(38,Thr_TotalBsmtSF);
		
		Threshold.put(43,Thr_1stFlrSF);
		Threshold.put(44,Thr_2ndFlrSF);
		Threshold.put(45,Thr_LowQualFinSF);
		Threshold.put(46,Thr_GrLivArea);
		
		Threshold.put(62,Thr_GarageArea);
		Threshold.put(66,Thr_WoodDeckSF);
		Threshold.put(67,Thr_OpenPorchSF);
		Threshold.put(68,Thr_EnclosedPorch);
		Threshold.put(69,Thr_3SsnPorch);
		Threshold.put(70,Thr_ScreenPorch);
		Threshold.put(71,Thr_PoolArea);
		Threshold.put(75,Thr_MiscVal);		
		//System.out.println(Threshold.entrySet());	
	}
	
	public ArrayList<Record> GetTrainData(String adds){

		ArrayList<Record> records = new ArrayList<Record>();
		Record r = null;
	    ArrayList<DiscreteAttribute> attributes;
	    
		BufferedReader inputStream = null;
		String line = null;
	
		try{
			inputStream = new BufferedReader(new FileReader(adds));		
			
			//attributes name add
			line = inputStream.readLine();
			line = line.trim();
			String att_name[] = line.split(",");
			for(int i = 0; i<att_name.length; i++){
				attrMap.add(att_name[i]);	
			}
					
			//attributes element add
			line = inputStream.readLine();
		
			while(line != null)	{	
				attributes = new ArrayList<DiscreteAttribute>();
				r = new Record();
				
				line = line.trim();	//System.out.println(line);			
				String entries[] = line.split(",");
				
				for(int i = 0; i<entries.length; i++){
					HashMap<String, Integer> tmp = new HashMap<String, Integer>();
					
					if(Threshold.containsKey(i)){	//Numeric data
						if(entries[i].equalsIgnoreCase("NA")) entries[i] = "No";
						else if(Integer.parseInt(entries[i]) < Threshold.get(i))	entries[i] = "No";
						else entries[i] = "Yes";
					}
					
					//case of price
					if(i == 80){	
						int price = Integer.parseInt(entries[i]);	
						entries[i] = price_condition.condition(price);
					}

					if(A.size()<NUM_ATTRS){ //initial add
						tmp.put(entries[i], num_attribute[i]++);
						A.add(i, tmp);
					}else{
						if(A.get(i).get(entries[i]) == null){
							tmp = (HashMap<String, Integer>) A.get(i).clone();
							tmp.put(entries[i], num_attribute[i]++);
							A.set(i, tmp);
						}	
					}	
					
				}//for
				
				for(int i = 0; i<entries.length; i++){	
						attributes.add(new DiscreteAttribute(attrMap.get(i), A.get(i).get(entries[i])));
				}//for
				
				r.setAttributes(attributes);
				records.add(r);	 			
				line = inputStream.readLine();
			}		
			
			inputStream.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}		
		
		return records;			
	}
	
	public ArrayList<Record> GetTestData(String adds){

		ArrayList<Record> records = new ArrayList<Record>();
		Record r = null;
	    ArrayList<DiscreteAttribute> attributes;
	    
		BufferedReader inputStream = null;
		String line = null;
	
		try{
			inputStream = new BufferedReader(new FileReader(adds));		
			
			//attributes name add
			line = inputStream.readLine();
			line = line.trim();
			String att_name[] = line.split(",");
					
			//attributes element add
			line = inputStream.readLine();
		
			while(line != null)	{	
				attributes = new ArrayList<DiscreteAttribute>();
				r = new Record();
				
				line = line.trim();	//System.out.println(line);			
				String entries[] = line.split(",");

				for(int i = 0; i<entries.length; i++){
					
					HashMap<String, Integer> tmp = new HashMap<String, Integer>();
					
					if(Threshold.containsKey(i)){	//Numeric data
						if(entries[i].equalsIgnoreCase("NA")) entries[i] = "No";
						else if(Integer.parseInt(entries[i]) < Threshold.get(i))	entries[i] = "No";
						else entries[i] = "Yes";
					}
					
					if(A.get(i).get(entries[i]) == null){
						tmp = (HashMap<String, Integer>) A.get(i).clone();
						tmp.put(entries[i], num_attribute[i]++);
						A.set(i, tmp);
					}	
						
				}//for
						
				for(int i = 0; i<entries.length; i++){
					if(A.get(i).containsKey(entries[i])){
						attributes.add(new DiscreteAttribute(attrMap.get(i), A.get(i).get(entries[i])));
					}
				}//for
				
				r.setAttributes(attributes);
				records.add(r);	 			
				line = inputStream.readLine();
			}		
			
			inputStream.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}		
		
		return records;			
	}
	
	public void printArrayInfo(ArrayList<Record> record){
		for(int i = 0; i < record.size(); i++){
			record.get(i).printRecord();
		}
	}
	
	
	public void SaveResult(){
		String path = HousePrices.class.getResource("").getPath();	
		BufferedWriter outputStream = null;
		try{
			outputStream = new BufferedWriter(new FileWriter("result.csv"));
		
			String s = "Id,SalePrice";
			outputStream.write(s); outputStream.newLine();
			for(int i = 0; i < result.size(); i++){
				String at[] = result.get(i).split(",");
				
				int id = (int)Double.parseDouble(at[0])+1;
				double gnt = Double.parseDouble(at[1]);
				
				outputStream.write(id+","+gnt);
				outputStream.newLine();
			}
			outputStream.close();
	    } catch (IOException e) {
	        System.err.println(e); // 에러가 있다면 메시지 출력
	        System.exit(1);
	    }	 
		
		System.out.println("data saved");
		
	}//initialize
	
	
	public void printTrain(){		
		System.out.println("=========== Train set =============");		
		for(int i = 0; i < NUM_ATTRS ; i++){	
			System.out.print(attrMap.get(i)+" : ");
			System.out.println(A.get(i).entrySet());
		}
	}
	

	public static String getLeafNames(int attributeNum, int valueNum) {
		
		for(String s : A.get(attributeNum).keySet())
			if(A.get(attributeNum).get(s) == valueNum)
				return s;
		return null;
	}
	
	public static boolean isAttributeUsed(int attribute) {
		if(usedAttributes.contains(attribute)) 	return true;
		else 	return false;	
	}
	
	public static int setSize(int i) {
		return A.get(i).size();
	}

	public static void traverseTree(Record r, Node root) {
		//System.out.println(root.getTestAttribute().getName());
	    //System.out.println(root.children.length);
		int nodeValue = 0;

		for(int i = 0; i < r.getAttributes().size(); i++) {	
			//System.out.println(r.getAttributes().get(i).getName() +"	"+r.getAttributes().get(i).getValue());
			if(r.getAttributes().get(i).getName().equalsIgnoreCase(root.getTestAttribute().getName())) {
				nodeValue = (int)r.getAttributes().get(i).getValue();
				//System.out.println( r.getAttributes().get(i).getName() +"	"+r.getAttributes().get(i).getValue());
				traverseTree(r, root.children[nodeValue]);	
				break;
			}
		}
			
		if(root.children == null){								
			for(String s : A.get(80).keySet()){				
				if(A.get(80).get(s) == (int)root.getResult()){
					result.add(r.getAttributes().get(0).getValue()+","+s);
					return;
				}
			}
			//not found - default		
			for(String s : A.get(80).keySet()){							
				result.add(r.getAttributes().get(0).getValue()+","+s);
				//System.out.println(r.getAttributes().get(0).getValue()+" "+root.getResult());	
				//System.out.println(root.getEntropy());	
				break;				
			}
			

		}
		
		return;
	}
	
	
	public static void main(String[] args) {
		//file path
		String path = HousePrices.class.getResource("").getPath();	
		String train_file = path+"train.csv";
		String test_file = path+"test.csv";

		HousePrices hp = new HousePrices();
		hp.Initialize();
		ArrayList<Record> train_records = hp.GetTrainData(train_file);		
		//hp.printArrayInfo(train_records);
		ArrayList<Record> test_records = hp.GetTestData(test_file);
		//hp.printArrayInfo(test_records);
		
		//result of train
		//hp.printTrain();
		
		//System.out.println(A.get(80).entrySet());
		//System.out.println(A.get(80).size());
		LearningSet learningSet = new LearningSet();
		
		Node root = new Node();
		
		for(Record record : train_records) {
			root.getData().add(record);
			//record.printRecord();
		}
		
		Tree t = new Tree();
		t.buildTree(train_records, root, learningSet);
		
		for(int x = 0; x < test_records.size();x++){
			//test_records.get(x).printRecord();
			traverseTree(test_records.get(x), root);
		}
		
		//print result		
		for(int i = 0; i < result.size(); i++)
			System.out.println(result.get(i));
			
		System.out.println("data size : "+result.size());	//1459
		hp.SaveResult();
	}

}
