package data;

import main.Common;

// Parent for class "Constraint"
public class Function {
	protected String name;
	
	private double[] coefs;
	private String letter;
	private String dir;
	
	public Function(double[] coefs, String dir) {
		this(coefs, "x", dir);
	}
	
	public Function(double[] coefs, String letter, String dir) {
		this.name = Common.makePolynomName(coefs, letter, "--> " + dir);
		this.coefs = coefs.clone();
		this.letter = letter;
		this.dir = dir;
	}
	
	public double[] getCoefs() {
		return coefs;
	}

	public String getLetter() {
		return letter;
	}
	
	public String getDir() {
		return dir;
	}

	public int getVarsAmount() {
		return coefs.length;
	}
	
	public String getVarsVector() {
		String res = "(";
		
		for (int i = 0; i < coefs.length; i++) {
			res += letter + (i+1);
			if (i != coefs.length - 1)
				res += ", ";
		}
		
		res += ")";
		
		return res;
	}
	
	@Override
	public String toString() {
		return name;
	}

}