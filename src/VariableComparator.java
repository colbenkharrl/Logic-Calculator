//	Colben Kharrl, March 18, 2016. Truth Table Generator.

import java.util.Comparator;

public class VariableComparator implements Comparator<Variable> {

	@Override public int compare(Variable o1, Variable o2) {
		if (o1.getCharValue() < o2.getCharValue()) {
			return -1;
		} else if (o1.equals(o2)) {
			return 0;
		} else {
			return 1;
		}
	}
}
