/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
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
public class RankSample
{
	// fields
	int index;
	double rank;
	double value;

	public RankSample(int index, double value)
	{
		this.index = index;
		this.value = value;
	}

	public String toString()
	{
		return "index: " + index + " rank: " + rank + " value:" + value;
	}

}
