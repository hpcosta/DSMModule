/**
 * @author Hernani Costa
 * iCorpora
 * EXPERT (EXPloiting Empirical appRoaches to Translation)
 * ESR3 - Collection & preparation of multilingual data for multiple corpus-based approaches to translation
 * Department of Translation and Interpreting 
 * Faculty of Philosophy and Humanities 
 *
 * Copyright (c) 2013-2016 University of Malaga, Spain.
 * All rights reserved.
 */
package measures.similaritymeasures.measures.scc;

/**
 * Auxiliary class to calculate the Spearman's Rank Correlation Coefficient.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class RankedFloatArray
{
	boolean tiesFound = false;
	double[] ranks;
	
	public RankedFloatArray (boolean tiesFound, double[] ranks){
		this.tiesFound = tiesFound;
		this.ranks = ranks;
	}

	public double[] getRanks() {
		return ranks;
	}

	public boolean isTiesFound() {
		return tiesFound;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		for (double f : ranks)
			s.append(f+"\n");
		return s.toString();
	}
}
