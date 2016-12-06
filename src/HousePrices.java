

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

	public String PriceSet(String s){
		int price = Integer.parseInt(s);
		if(price<35000) s = 33750+"";
				else if(price<37500) s = 36250+"";
				else if(price<40000) s = 38750+"";
				else if(price<42500) s = 41250+"";
				else if(price<45000) s = 43750+"";
				else if(price<47500) s = 46250+"";
				else if(price<50000) s = 48750+"";
				else if(price<52500) s = 51250+"";
				else if(price<55000) s = 53750+"";
				else if(price<57500) s = 56250+"";
				else if(price<60000) s = 58750+"";
				else if(price<62500) s = 61250+"";
				else if(price<65000) s = 63750+"";
				else if(price<67500) s = 66250+"";
				else if(price<70000) s = 68750+"";
				else if(price<72500) s = 71250+"";
				else if(price<75000) s = 73750+"";
				else if(price<77500) s = 76250+"";
				else if(price<80000) s = 78750+"";
				else if(price<82500) s = 81250+"";
				else if(price<85000) s = 83750+"";
				else if(price<87500) s = 86250+"";
				else if(price<90000) s = 88750+"";
				else if(price<92500) s = 91250+"";
				else if(price<95000) s = 93750+"";
				else if(price<97500) s = 96250+"";
				else if(price<100000) s = 98750+"";
				else if(price<102500) s = 101250+"";
				else if(price<105000) s = 103750+"";
				else if(price<107500) s = 106250+"";
				else if(price<110000) s = 108750+"";
				else if(price<112500) s = 111250+"";
				else if(price<115000) s = 113750+"";
				else if(price<117500) s = 116250+"";
				else if(price<120000) s = 118750+"";
				else if(price<122500) s = 121250+"";
				else if(price<125000) s = 123750+"";
				else if(price<127500) s = 126250+"";
				else if(price<130000) s = 128750+"";
				else if(price<132500) s = 131250+"";
				else if(price<135000) s = 133750+"";
				else if(price<137500) s = 136250+"";
				else if(price<140000) s = 138750+"";
				else if(price<142500) s = 141250+"";
				else if(price<145000) s = 143750+"";
				else if(price<147500) s = 146250+"";
				else if(price<150000) s = 148750+"";
				else if(price<152500) s = 151250+"";
				else if(price<155000) s = 153750+"";
				else if(price<157500) s = 156250+"";
				else if(price<160000) s = 158750+"";
				else if(price<162500) s = 161250+"";
				else if(price<165000) s = 163750+"";
				else if(price<167500) s = 166250+"";
				else if(price<170000) s = 168750+"";
				else if(price<172500) s = 171250+"";
				else if(price<175000) s = 173750+"";
				else if(price<177500) s = 176250+"";
				else if(price<180000) s = 178750+"";
				else if(price<182500) s = 181250+"";
				else if(price<185000) s = 183750+"";
				else if(price<187500) s = 186250+"";
				else if(price<190000) s = 188750+"";
				else if(price<192500) s = 191250+"";
				else if(price<195000) s = 193750+"";
				else if(price<197500) s = 196250+"";
				else if(price<200000) s = 198750+"";
				else if(price<202500) s = 201250+"";
				else if(price<205000) s = 203750+"";
				else if(price<207500) s = 206250+"";
				else if(price<210000) s = 208750+"";
				else if(price<212500) s = 211250+"";
				else if(price<215000) s = 213750+"";
				else if(price<217500) s = 216250+"";
				else if(price<220000) s = 218750+"";
				else if(price<222500) s = 221250+"";
				else if(price<225000) s = 223750+"";
				else if(price<227500) s = 226250+"";
				else if(price<230000) s = 228750+"";
				else if(price<232500) s = 231250+"";
				else if(price<235000) s = 233750+"";
				else if(price<237500) s = 236250+"";
				else if(price<240000) s = 238750+"";
				else if(price<242500) s = 241250+"";
				else if(price<245000) s = 243750+"";
				else if(price<247500) s = 246250+"";
				else if(price<250000) s = 248750+"";
				else if(price<252500) s = 251250+"";
				else if(price<255000) s = 253750+"";
				else if(price<257500) s = 256250+"";
				else if(price<260000) s = 258750+"";
				else if(price<262500) s = 261250+"";
				else if(price<265000) s = 263750+"";
				else if(price<267500) s = 266250+"";
				else if(price<270000) s = 268750+"";
				else if(price<272500) s = 271250+"";
				else if(price<275000) s = 273750+"";
				else if(price<277500) s = 276250+"";
				else if(price<280000) s = 278750+"";
				else if(price<282500) s = 281250+"";
				else if(price<285000) s = 283750+"";
				else if(price<287500) s = 286250+"";
				else if(price<290000) s = 288750+"";
				else if(price<292500) s = 291250+"";
				else if(price<295000) s = 293750+"";
				else if(price<297500) s = 296250+"";
				else if(price<300000) s = 298750+"";
				else if(price<302500) s = 301250+"";
				else if(price<305000) s = 303750+"";
				else if(price<307500) s = 306250+"";
				else if(price<310000) s = 308750+"";
				else if(price<312500) s = 311250+"";
				else if(price<315000) s = 313750+"";
				else if(price<317500) s = 316250+"";
				else if(price<320000) s = 318750+"";
				else if(price<322500) s = 321250+"";
				else if(price<325000) s = 323750+"";
				else if(price<327500) s = 326250+"";
				else if(price<330000) s = 328750+"";
				else if(price<332500) s = 331250+"";
				else if(price<335000) s = 333750+"";
				else if(price<337500) s = 336250+"";
				else if(price<340000) s = 338750+"";
				else if(price<342500) s = 341250+"";
				else if(price<345000) s = 343750+"";
				else if(price<347500) s = 346250+"";
				else if(price<350000) s = 348750+"";
				else if(price<352500) s = 351250+"";
				else if(price<355000) s = 353750+"";
				else if(price<357500) s = 356250+"";
				else if(price<360000) s = 358750+"";
				else if(price<362500) s = 361250+"";
				else if(price<365000) s = 363750+"";
				else if(price<367500) s = 366250+"";
				else if(price<370000) s = 368750+"";
				else if(price<372500) s = 371250+"";
				else if(price<375000) s = 373750+"";
				else if(price<377500) s = 376250+"";
				else if(price<380000) s = 378750+"";
				else if(price<382500) s = 381250+"";
				else if(price<385000) s = 383750+"";
				else if(price<387500) s = 386250+"";
				else if(price<390000) s = 388750+"";
				else if(price<392500) s = 391250+"";
				else if(price<395000) s = 393750+"";
				else if(price<397500) s = 396250+"";
				else if(price<400000) s = 398750+"";
				else if(price<402500) s = 401250+"";
				else if(price<405000) s = 403750+"";
				else if(price<407500) s = 406250+"";
				else if(price<410000) s = 408750+"";
				else if(price<412500) s = 411250+"";
				else if(price<415000) s = 413750+"";
				else if(price<417500) s = 416250+"";
				else if(price<420000) s = 418750+"";
				else if(price<422500) s = 421250+"";
				else if(price<425000) s = 423750+"";
				else if(price<427500) s = 426250+"";
				else if(price<430000) s = 428750+"";
				else if(price<432500) s = 431250+"";
				else if(price<435000) s = 433750+"";
				else if(price<437500) s = 436250+"";
				else if(price<440000) s = 438750+"";
				else if(price<442500) s = 441250+"";
				else if(price<445000) s = 443750+"";
				else if(price<447500) s = 446250+"";
				else if(price<450000) s = 448750+"";
				else if(price<452500) s = 451250+"";
				else if(price<455000) s = 453750+"";
				else if(price<457500) s = 456250+"";
				else if(price<460000) s = 458750+"";
				else if(price<462500) s = 461250+"";
				else if(price<465000) s = 463750+"";
				else if(price<467500) s = 466250+"";
				else if(price<470000) s = 468750+"";
				else if(price<472500) s = 471250+"";
				else if(price<475000) s = 473750+"";
				else if(price<477500) s = 476250+"";
				else if(price<480000) s = 478750+"";
				else if(price<482500) s = 481250+"";
				else if(price<485000) s = 483750+"";
				else if(price<487500) s = 486250+"";
				else if(price<490000) s = 488750+"";
				else if(price<492500) s = 491250+"";
				else if(price<495000) s = 493750+"";
				else if(price<497500) s = 496250+"";
				else if(price<500000) s = 498750+"";
				else if(price<502500) s = 501250+"";
				else if(price<505000) s = 503750+"";
				else if(price<507500) s = 506250+"";
				else if(price<510000) s = 508750+"";
				else if(price<512500) s = 511250+"";
				else if(price<515000) s = 513750+"";
				else if(price<517500) s = 516250+"";
				else if(price<520000) s = 518750+"";
				else if(price<522500) s = 521250+"";
				else if(price<525000) s = 523750+"";
				else if(price<527500) s = 526250+"";
				else if(price<530000) s = 528750+"";
				else if(price<532500) s = 531250+"";
				else if(price<535000) s = 533750+"";
				else if(price<537500) s = 536250+"";
				else if(price<540000) s = 538750+"";
				else if(price<542500) s = 541250+"";
				else if(price<545000) s = 543750+"";
				else if(price<547500) s = 546250+"";
				else if(price<550000) s = 548750+"";
				else if(price<552500) s = 551250+"";
				else if(price<555000) s = 553750+"";
				else if(price<557500) s = 556250+"";
				else if(price<560000) s = 558750+"";
				else if(price<562500) s = 561250+"";
				else if(price<565000) s = 563750+"";
				else if(price<567500) s = 566250+"";
				else if(price<570000) s = 568750+"";
				else if(price<572500) s = 571250+"";
				else if(price<575000) s = 573750+"";
				else if(price<577500) s = 576250+"";
				else if(price<580000) s = 578750+"";
				else if(price<582500) s = 581250+"";
				else if(price<585000) s = 583750+"";
				else if(price<587500) s = 586250+"";
				else if(price<590000) s = 588750+"";
				else if(price<592500) s = 591250+"";
				else if(price<595000) s = 593750+"";
				else if(price<597500) s = 596250+"";
				else if(price<600000) s = 598750+"";
				else if(price<602500) s = 601250+"";
				else if(price<605000) s = 603750+"";
				else if(price<607500) s = 606250+"";
				else if(price<610000) s = 608750+"";
				else if(price<612500) s = 611250+"";
				else if(price<615000) s = 613750+"";
				else if(price<617500) s = 616250+"";
				else if(price<620000) s = 618750+"";
				else if(price<622500) s = 621250+"";
				else if(price<625000) s = 623750+"";
				else if(price<627500) s = 626250+"";
				else if(price<630000) s = 628750+"";
				else if(price<632500) s = 631250+"";
				else if(price<635000) s = 633750+"";
				else if(price<637500) s = 636250+"";
				else if(price<640000) s = 638750+"";
				else if(price<642500) s = 641250+"";
				else if(price<645000) s = 643750+"";
				else if(price<647500) s = 646250+"";
				else if(price<650000) s = 648750+"";
				else if(price<652500) s = 651250+"";
				else if(price<655000) s = 653750+"";
				else if(price<657500) s = 656250+"";
				else if(price<660000) s = 658750+"";
				else if(price<662500) s = 661250+"";
				else if(price<665000) s = 663750+"";
				else if(price<667500) s = 666250+"";
				else if(price<670000) s = 668750+"";
				else if(price<672500) s = 671250+"";
				else if(price<675000) s = 673750+"";
				else if(price<677500) s = 676250+"";
				else if(price<680000) s = 678750+"";
				else if(price<682500) s = 681250+"";
				else if(price<685000) s = 683750+"";
				else if(price<687500) s = 686250+"";
				else if(price<690000) s = 688750+"";
				else if(price<692500) s = 691250+"";
				else if(price<695000) s = 693750+"";
				else if(price<697500) s = 696250+"";
				else if(price<700000) s = 698750+"";
				else if(price<702500) s = 701250+"";
				else if(price<705000) s = 703750+"";
				else if(price<707500) s = 706250+"";
				else if(price<710000) s = 708750+"";
				else if(price<712500) s = 711250+"";
				else if(price<715000) s = 713750+"";
				else if(price<717500) s = 716250+"";
				else if(price<720000) s = 718750+"";
				else if(price<722500) s = 721250+"";
				else if(price<725000) s = 723750+"";
				else if(price<727500) s = 726250+"";
				else if(price<730000) s = 728750+"";
				else if(price<732500) s = 731250+"";
				else if(price<735000) s = 733750+"";
				else if(price<737500) s = 736250+"";
				else if(price<740000) s = 738750+"";
				else if(price<742500) s = 741250+"";
				else if(price<745000) s = 743750+"";
				else if(price<747500) s = 746250+"";

		return s;
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
					
					//11 case of price
					if(i == 80){		
						entries[i] = PriceSet(entries[i]);
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
						attributes.add(new DiscreteAttribute(attrMap.get(i),A.get(i).get(entries[i])));
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
					
					if(A.get(i).get(entries[i]) == null){
						tmp = (HashMap<String, Integer>) A.get(i).clone();
						tmp.put(entries[i], num_attribute[i]++);
						A.set(i, tmp);
					}	
						
				}//for
						
				for(int i = 0; i<entries.length; i++){
					if(Threshold.containsKey(i)){	//Numeric data
						if(entries[i].equalsIgnoreCase("NA")) entries[i] = -1+"";
						if(Integer.parseInt(entries[i]) < Threshold.get(i))	entries[i] = "No";
						else entries[i] = "Yes";
					}
					if(A.get(i).containsKey(entries[i])){
						attributes.add(new DiscreteAttribute(attrMap.get(i),A.get(i).get(entries[i])));
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
			//System.out.println("root name : " + root.getTestAttribute().getName() + "	root value : "+root.getTestAttribute().getValue());
			//System.out.print("r name : "+r.getAttributes().get(r.getAttributes().size()-1).getName());
			//System.out.print("r value : "+r.getAttributes().get(r.getAttributes().size()-1).getValue());
			
			for(String s : A.get(80).keySet()){				
				//System.out.println((int)root.getResult());
				if(A.get(80).get(s) == (int)r.getAttributes().get(r.getAttributes().size()-1).getValue())	
					result.add(r.getAttributes().get(0).getValue()+","+s);
			}
			
			/*
			if(root.getResult() == r.getAttributes().get(r.getAttributes().size()-1).getValue()) {
				System.out.println(root.getResult());				
			}	*/
			//System.out.println(r.getAttributes().get(r.getAttributes().size()-1).getValue());
			//result.add(r.getAttributes().get(0).getValue()+","+A.get(80).get(root.getResult()));
			
			//System.out.println();
		}
		
		return;
	}
	
	
	public static void main(String[] args) {
		//file path
		String path = HousePrices.class.getResource("").getPath();	
		String train_file = path+"train.csv";
		String test_file = path+"test.csv";
		String result_file = path+"result.csv";
		
		HousePrices hp = new HousePrices();
		hp.Initialize();
		ArrayList<Record> train_records = hp.GetTrainData(train_file);
		
		//hp.printTrain();
		//hp.printArrayInfo(train_records);
		ArrayList<Record> test_records = hp.GetTestData(test_file);
		//hp.printArrayInfo(test_records);
		
		LearningSet learningSet = new LearningSet();
		
		Node root = new Node();
		
		for(Record record : train_records) {
			root.getData().add(record);
			//record.printRecord();
		}
		
		Tree t = new Tree();
		t.buildTree(train_records, root, learningSet);
		System.out.println(test_records.size());
		for(int x = 0; x < test_records.size();x++){
			//test_records.get(x).printRecord();
			traverseTree(test_records.get(x), root);
		}
		//System.out.println(result.size());
		/*
		for(int i = 0; i < result.size(); i++)
			System.out.println(result.get(i));
			*/
		
		hp.SaveResult();
	}

}
