

import java.util.*;

public class Record {
	boolean isnumeic = false;
	private ArrayList<DiscreteAttribute> attributes;

	public ArrayList<DiscreteAttribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(ArrayList<DiscreteAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void IsNumeric(boolean isnumeic) {
		this.isnumeic = isnumeic;
	}
	public void printRecord(){
		for(int j = 0; j < attributes.size(); j++){
			System.out.print(attributes.get(j).getName()+" : "+attributes.get(j).getValue()+"	");
		}
		System.out.println();
	}
}
