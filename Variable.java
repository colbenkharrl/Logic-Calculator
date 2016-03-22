public class Variable {
	
	private Character charValue;
	private int bitValue;
	private boolean isInverted;
	private Variable originalVar;
	
	public Variable(char value) {
		charValue = value;
		isInverted = false;
		bitValue = 0;
		originalVar = new Variable(charValue, bitValue, isInverted);
	}
	
	public Variable(char value, int nBit, boolean is) {
		charValue = value;
		bitValue = nBit;
		isInverted = is;
	}
	
	public Variable(int nBit) {
		charValue = Integer.toString(nBit).charAt(0);
		isInverted = false;
		bitValue = nBit;
		originalVar = new Variable(charValue, bitValue, isInverted);
	}
	
	public Variable(Character value, boolean inverted) {
		charValue = value;
		isInverted = inverted;
		bitValue = 0;
		originalVar = new Variable(charValue, bitValue, isInverted);
	}
	
	public Variable(Variable addedValue) {
		charValue = addedValue.getCharValue();
		bitValue = addedValue.getBitValue();
		isInverted = addedValue.isInverted();
		originalVar = new Variable(charValue, bitValue, isInverted);
	}

	public Character getCharValue() {
		return charValue;
	}
	
	public int getBitValue() {
		return bitValue;
	}
	
	public boolean isInverted() {
		return isInverted;
	}
	
	public void setBitValue(int bit) {
		this.bitValue = bit;
	}
	
	public String toString() {
		
		return "" + charValue + bitValue + Boolean.toString(isInverted).charAt(0);
	}
	
	public void reset() {
		charValue = originalVar.charValue;
		bitValue = originalVar.bitValue;
		isInverted = originalVar.isInverted;
	}

	public int getInverted() {
		int returnBit;
		if (bitValue == 0) {
			returnBit = 1;
		} else {
			returnBit = 0;
		}
		return returnBit;
	}
}
