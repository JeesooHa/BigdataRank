

abstract public class Attribute {
	private String name;
	private double value;
	private boolean isUnknown;

	public Attribute(String name, double value) {
		this.name = name;
		this.value = value;
		isUnknown = false;
	}
	
	public Attribute(String name, String value) {
		this.name = name;
		try {
			this.value = Double.valueOf(value);
			this.isUnknown = false;
		}
		catch(NumberFormatException nfe) {
			this.value = -1;
			this.isUnknown = true;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setUnknown(boolean isUnknown) {
		this.isUnknown = isUnknown;
	}

	public boolean isUnknown() {
		return isUnknown;
	}
}
