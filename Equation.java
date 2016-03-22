import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Equation {

	private EquationList<Variable> currentEquation;
	private EquationList<Variable> knownVariables;
	private static Character[] acceptedNonADNumbers = {'+', '*', '(', ')'};
	private ArrayList<String> minterms;
	private ArrayList<String> maxterms;

	
	public Equation(String input) {
		currentEquation = new EquationList<Variable>();
		knownVariables = new EquationList<Variable>();
		currentEquation = parseEquation(input);
		minterms = new ArrayList<String>();
		maxterms = new ArrayList<String>();
	}
	
	public EquationList<Variable> getVariables() {
		Collections.sort(knownVariables, new VariableComparator());
		return knownVariables;
	}

	public int getVariableNumber() {
		return knownVariables.size();
	}
	
	private EquationList<Variable> parseEquation(String input) {
		
		System.out.println("Beginning Equation Parse...");
		EquationList<Variable> returnArray = new EquationList<Variable>();
		Variable addedValue;
		for (int i = 0; i < input.length(); i++) {
			addedValue = new Variable (input.charAt(i));
			if (Character.isLetter(input.charAt(i))) {
				if (i != input.length() - 1 && input.charAt(i + 1) == '\'') {
					addedValue = new Variable(input.charAt(i), true);
					returnArray.add(addedValue);
					i++;
				} else {
					addedValue = new Variable(input.charAt(i), false);
					returnArray.add(addedValue);
				}
				boolean isAVariable = false;
				for (int j = 0; j < knownVariables.size(); j++) {
					if (addedValue.getCharValue() == knownVariables.get(j).getCharValue()) {
						isAVariable = true;
					}
				}
				if (!isAVariable) {
					knownVariables.add(new Variable(addedValue));
				}
			} else {
				returnArray.add(addedValue);
			}
		}
		for (int i = returnArray.size() - 1; i >0; i--) {
			if (Character.isLetter(returnArray.get(i).getCharValue()) && Character.isLetter(returnArray.get(i - 1).getCharValue())) {
				returnArray.add(i, new Variable('*'));
			} else if (returnArray.get(i).getCharValue() == '(' && returnArray.get(i - 1).getCharValue() == ')') {
				returnArray.add(i, new Variable('*'));
			} else if (returnArray.get(i).getCharValue() == '(' && Character.isLetter(returnArray.get(i - 1).getCharValue())) {
				returnArray.add(i, new Variable('*'));
			} else if (returnArray.get(i - 1).getCharValue() == ')' && Character.isLetter(returnArray.get(i).getCharValue())) {
				returnArray.add(i, new Variable('*'));
			}
		}
		System.out.println("Equation Successfully Parsed: " + returnArray);
		return returnArray;
	}

	public static boolean isParsable(String input) {
		
		System.out.println("Testing if String is Parsable..");
		for (int i = 0; i < input.length(); i++) {
			for (int j = 0; j < acceptedNonADNumbers.length; j++) {
				if (!(input.charAt(i) == '+' || input.charAt(i) == '\'' || input.charAt(i) == '*' || input.charAt(i) == '(' || input.charAt(i) == ')' || Character.isAlphabetic(input.charAt(i)) || Character.isDigit(input.charAt(i)))) {
					System.out.println("String is not parsable..");
					return false;
				}
				
			}
		}
		System.out.println("String is parsable..");
		return true;
	}
	
	public String binaryValue(int iteration) {
		
		System.out.println("Fetching Binary Value..");
		String returnValue = Integer.toBinaryString(iteration);
		while (returnValue.length() < knownVariables.size()) {
			returnValue = "0" + returnValue;
		}
		System.out.println("Binary value is " + returnValue);
		return returnValue;
	}
	
	public EquationList<Variable> operate(EquationList<Variable> inputEquation) {
		
		EquationList<Variable> returnValue = inputEquation;
		System.out.println("Beginning operation on " + returnValue);
		Variable tempVariable;
		while (hasOperator(returnValue, '*')) {
			for (int i = 0; i < returnValue.size(); i++) {
				if (returnValue.get(i).getCharValue() == '*') {
					int product = 3;
					if (returnValue.get(i - 1).getBitValue() == 0 || returnValue.get(i + 1).getBitValue() == 0) {
						product = 0;
					} else {
						product = 1;
					}
					returnValue.removeRange((i - 1), (i + 2));
					tempVariable = new Variable(product);
					returnValue.add(i - 1, tempVariable);
					System.out.println("Result after multiplication: " + returnValue);
				}
			}
			
		}
		while(hasOperator(returnValue, '+')) {
			for (int i = 0; i < returnValue.size(); i++) {
				if (returnValue.get(i).getCharValue() == '+') {
					int sum = 3;
					if (returnValue.get(i - 1).getBitValue() == 0 && returnValue.get(i + 1).getBitValue() == 0) {
						sum = 0;
					} else {
						sum = 1;
					}
					returnValue.removeRange((i - 1), (i + 2));
					tempVariable = new Variable(sum);
					returnValue.add(i - 1, tempVariable);
					System.out.println("Result after addition: " + returnValue);
				}
			}
		}
		return returnValue;
	}
	
//						solveForInputString FOR INPUT VALUES METHOD
	
	public int solveForInputString(String inputVariables) {
		String insideInputs = inputVariables;
		@SuppressWarnings("unchecked")
		EquationList<Variable> insideEquation = (EquationList<Variable>) currentEquation.clone();
		System.out.println("Solving for equation " + insideEquation + ", and inputs: " + insideInputs);
		
		Integer inputInt;
		
		for (int i = 0; i < knownVariables.size(); i++) {
			
			inputInt = new Integer(Integer.parseInt(Character.toString(insideInputs.charAt(i))));
			
			knownVariables.get(i).setBitValue(inputInt);
		}
		System.out.println("Variable bit values set to: " + knownVariables.toString());
		for (int i = 0; i < insideEquation.size(); i++) {
			for (int j = 0; j < knownVariables.size(); j++) {
				
				if (insideEquation.get(i).getCharValue() == knownVariables.get(j).getCharValue()) {
					if (insideEquation.get(i).isInverted()) {
						System.out.println("Setting " + insideEquation.get(i));
						insideEquation.get(i).setBitValue(knownVariables.get(j).getInverted());
						System.out.println("Set and inverted to " + insideEquation.get(i));
					} else {
						System.out.println("Setting " + insideEquation.get(i));
						insideEquation.get(i).setBitValue(knownVariables.get(j).getBitValue());
						System.out.println("Set to " + insideEquation.get(i));
					}
				}
			}
		}
		System.out.println("Equation after bitValue update: " + insideEquation);
		System.out.println("Variables after bitValue update: " + knownVariables + "\n");
		while (hasParentheses(insideEquation)) {
			System.out.println("Checking for and solving parentheses in equation..");
			int[] smallParentheses = smallestParentheses(insideEquation);
			System.out.println(insideEquation);
			insideEquation = solveParentheses(insideEquation, smallParentheses);
		}
		System.out.println("Equation to be operated after parentheses extraction: " + insideEquation);
		insideEquation = operate(insideEquation);
		for (int i = 0; i < knownVariables.size(); i++) {
			knownVariables.get(i).reset();
		}
		if (insideEquation.get(0).getBitValue() == 1) {
			minterms.add(insideInputs);
		} else {
			maxterms.add(insideInputs);
		}
	
		return insideEquation.get(0).getBitValue();
		
	}
	
//						FIND AND solveForInputString PARENTHESIS
	
	public int[] smallestParentheses(EquationList<Variable> insideEquation) {
		System.out.println("Finding the inner-most parentheses..");
		int[] returnParentheses = {-1, -1};
		int maxLevel = 0;
		int level = 0;
		for (int i=0;i<insideEquation.size();i++) {
			if (insideEquation.get(i).getCharValue() == '(') {
				level++;
				if (returnParentheses[0] == -1) {
					returnParentheses[0] = i;
				} else {
					if (level > maxLevel) {
						returnParentheses[0] = i;
					}
				}
			} else if (insideEquation.get(i).getCharValue() == ')') {
				if (returnParentheses[1] == -1) {
					returnParentheses[1] = i;
					maxLevel = level;
					level = 0;
				} else if (level > maxLevel) {
					returnParentheses[1] = i;
					maxLevel = level;
					level = 0;
				}
			}
		}
		System.out.println("Inner-most parentheses are: " + returnParentheses[0] + ", " + returnParentheses[1]);
		return returnParentheses;
	}
	
	public EquationList<Variable> solveParentheses(EquationList<Variable> insideEquation, int[] pSubstring) {
		
		@SuppressWarnings("unchecked")
		EquationList<Variable> returnArray = (EquationList<Variable>) insideEquation.clone();
		EquationList<Variable> equateableValues = returnArray.subList(pSubstring[0] + 1, pSubstring[1]);
		equateableValues = operate(equateableValues);
		returnArray.removeRange(pSubstring[0], (pSubstring[1] + 1));
		for (int i = 0; i < equateableValues.size(); i++) {
			returnArray.add((pSubstring[0]), equateableValues.get(i));
		}
		
		return returnArray;
	}
	
//						BOOLEAN TESTER METHODS
	
	public boolean hasOperator(EquationList<Variable> insideEquation, char inputChar) {
		for (int i = 0; i < insideEquation.size(); i++) {
			if (insideEquation.get(i).getCharValue() == inputChar) {
				return true;
			}
		} 
		return false;
	}
	
	private boolean hasParentheses(EquationList<Variable> insideEquation) {
		for (int i=0;i<insideEquation.size();i++) {
			if (insideEquation.get(i).getCharValue() == '(' || insideEquation.get(i).getCharValue() == ')') {
				return true;
			}
		}
		return false;
	}
	
	public String canonicalSOP() {
		String returnString = "";
		for (int i = 0; i < minterms.size(); i++) {
			for (int j = 0; j < knownVariables.size(); j++) {
				if (minterms.get(i).charAt(j) == '0') {
					returnString += knownVariables.get(j).getCharValue();
				} else {
					returnString += knownVariables.get(j).getCharValue() + "\'";
				}
			}
			if (!(i == minterms.size() - 1)) {
				returnString += "+";
			}
		}
		return returnString;
	}
	
	public String canonicalPOS() {
		String returnString = "";
		for (int i = 0; i < maxterms.size(); i++) {
			returnString += "(";
			for (int j = 0; j < knownVariables.size(); j++) {
				if (maxterms.get(i).charAt(j) == '1') {
					if (j == knownVariables.size() - 1) {
						returnString += knownVariables.get(j).getCharValue();
					} else {
						returnString += knownVariables.get(j).getCharValue() + "+";
					}
				} else {
					if (j == knownVariables.size() - 1) {
						returnString += knownVariables.get(j).getCharValue() + "\'";
					} else {
						returnString += knownVariables.get(j).getCharValue() + "\'" + "+";
					}				}
			}
			returnString += ")";
		}
		return returnString;
	}
}