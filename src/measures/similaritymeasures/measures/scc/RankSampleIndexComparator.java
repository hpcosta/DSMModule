/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.measures.scc;

import java.util.Comparator;

/**
 * Auxiliary class to calculate the Spearman's Rank Correlation Coefficient.
 * Sorts by index, small to large.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class RankSampleIndexComparator implements Comparator
{

	public int compare(Object arg0, Object arg1)
	{
		RankSample first = (RankSample) arg0;
		RankSample second = (RankSample) arg1;
		if (second.index < first.index)
			return 1;
		if (second.index > first.index)
			return -1;
		return 0;
	}
}
