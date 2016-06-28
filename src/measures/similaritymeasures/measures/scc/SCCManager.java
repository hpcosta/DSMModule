/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.measures.scc;

import java.util.List;

import measures.similaritymeasures.entities.CommonEntities;

/**
 * Calculates the Spearmen's Rank Correlation Coefficient. The score varies between: max 1; min 0.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class SCCManager
{

	private SpearmansRankCorrelationCoefficient scc = null;

	/**
	 * Default constructor.
	 */
	public SCCManager()
	{

	}

	/**
	 * Receives a list of CommonEntities and two lists of Strings (text1 and text2).
	 * 
	 * @param commonEntities
	 * @param text1
	 * @param text2
	 */
	public void calculatingSCC(CommonEntities commonEntities, List<String> text1, List<String> text2)
	{
		scc = new SpearmansRankCorrelationCoefficient(commonEntities, text1, text2);
	}

	/**
	 * @return the spearmansCorrelationCoefficientScore
	 */
	public double getSpearmansCorrelationCoefficientScore()
	{
		return scc.getSpearmansCorrelationCoefficientScore();
	}
}
