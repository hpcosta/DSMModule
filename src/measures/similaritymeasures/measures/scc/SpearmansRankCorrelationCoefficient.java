/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.measures.scc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import measures.similaritymeasures.entities.CommonEntities;
import measures.similaritymeasures.entities.Entity;
import constantsfilesfolders.PublicFunctions;

/**
 * Calculates the Spearman's Rank Correlation Coefficient.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class SpearmansRankCorrelationCoefficient
{
	private CommonEntities commonEntities = null;
	private List<String> text1 = null;
	private List<String> text2 = null;
	private HashMap<String, Integer> text1_Occurrences = null;
	private HashMap<String, Integer> text2_Occurrences = null;
	private HashMap<String, Double> text1_Ranks = null;
	private HashMap<String, Double> text2_Ranks = null;

	private double spearmansCorrelationCoefficientScore = Double.MIN_VALUE;

	/**
	 * Default constructor. Receives a list of CommonEntities and two lists of Strings (text1 and text2).
	 * 
	 * @param commonEntities
	 *                common entities
	 * @param text1
	 *                text 1
	 * @param text2
	 *                text 2
	 */
	public SpearmansRankCorrelationCoefficient(CommonEntities commonEntities, List<String> text1, List<String> text2)
	{
		this.commonEntities = commonEntities;
		this.text1 = text1;
		this.text2 = text2;
		text1_Occurrences = new HashMap<String, Integer>();
		text2_Occurrences = new HashMap<String, Integer>();
		text1_Ranks = new HashMap<String, Double>();
		text2_Ranks = new HashMap<String, Double>();
		rankingCommonEntities();
	}

	/**
	 * Calculates the Spearman's Rank Correlation Coefficient.
	 */
	private void rankingCommonEntities()
	{
		// Creating an hashMap with the entity and the number of occurrences in the texts
		for (Entity commonEntity : commonEntities.getCommonEntities())
		{
			text1_Occurrences.put(commonEntity.getEntity(), Collections.frequency(text1, commonEntity.getEntity()));
			text2_Occurrences.put(commonEntity.getEntity(), Collections.frequency(text2, commonEntity.getEntity()));
		}

		text1_Occurrences = (HashMap<String, Integer>) PublicFunctions.sortByValues(text1_Occurrences, false);
		double[] _occurrencesMatrixText1 = new double[text1_Occurrences.size()];
		int i = 0;
		for (int value : text1_Occurrences.values())
			_occurrencesMatrixText1[i++] = value;

		text2_Occurrences = (HashMap<String, Integer>) PublicFunctions.sortByValues(text2_Occurrences, false);
		double[] _occurrencesMatrixText2 = new double[text2_Occurrences.size()];
		int j = 0;
		for (int value : text2_Occurrences.values())
			_occurrencesMatrixText2[j++] = value;

		// Calculating average ranks
		RankedFloatArray rankedDoubleMatrixText1 = rankPositions(_occurrencesMatrixText1);
		RankedFloatArray rankedDoubleMatrixText2 = rankPositions(_occurrencesMatrixText2);

		// System.out.println(text1_Occurrences.toString());
		// System.out.println(rankedDoubleMatrixText1.toString());
		// System.out.println(" ");
		// System.out.println(text2_Occurrences.toString());
		// System.out.println(rankedDoubleMatrixText2.toString());
		// System.out.println(" ");

		// Creating an hashMap with the entity and the rank based on their occurrences in the texts
		int jj = 0;
		for (String s : text1_Occurrences.keySet())
		{
			text1_Ranks.put(s, rankedDoubleMatrixText1.getRanks()[jj]);
			jj++;
		}

		int kk = 0;
		for (String s : text2_Occurrences.keySet())
		{
			text2_Ranks.put(s, rankedDoubleMatrixText2.getRanks()[kk]);
			kk++;
		}

		text1_Ranks = (HashMap<String, Double>) PublicFunctions.sortByValues(text1_Ranks, true);
		text2_Ranks = (HashMap<String, Double>) PublicFunctions.sortByValues(text2_Ranks, true);
		// System.out.println(text1_Ranks.toString());
		// System.out.println(rankedDoubleMatrixText1.toString());
		// System.out.println(" ");
		// System.out.println(text2_Ranks.toString());
		// System.out.println(rankedDoubleMatrixText2.toString());
		// System.out.println(" ");

		// Creating a new object Common entities with ranks
		CommonEntities newCommonEntitiesWithRanks = new CommonEntities();
		List<Entity> _commonEntitiesList = commonEntities.getCommonEntities();
		Entity _tempEntity = null;
		String entity = "";
		double text1_rank = 0.0;
		double text2_rank = 0.0;
		int text1_observedValue = 0;
		int text2_observedValue = 0;
		for (Entity e : _commonEntitiesList)
		{
			entity = e.getEntity();
			text1_rank = text1_Ranks.get(entity);
			text2_rank = text2_Ranks.get(entity);
			text1_observedValue = e.getText1_observedValue();
			text2_observedValue = e.getText2_observedValue();
			_tempEntity = new Entity(entity, text1_observedValue, text1_rank, text2_observedValue, text2_rank);
			newCommonEntitiesWithRanks.addEntity(_tempEntity);
		}

		// System.out.println(newCommonEntitiesWithRanks.toString());
		List<Entity> ceList = newCommonEntitiesWithRanks.getCommonEntities();
		double sumSqrDiffs = 0.0;
		double diff = 0.0;
		for (Entity e : ceList)
		{
			// calculate sum of square differences
			diff = e.getText1_rank() - e.getText2_rank();
			sumSqrDiffs += (diff * diff);
		}
		// // numerator 6(sumSqrDiffs)
		double numerator = 6 * sumSqrDiffs;
		// // denominator (n^3-n)
		double n = ceList.size();
		double denomenator = (n * n * n) - n;
		// // rho
		spearmansCorrelationCoefficientScore = 1.0d - (numerator / denomenator);

		if (Double.isNaN(spearmansCorrelationCoefficientScore))
			spearmansCorrelationCoefficientScore = 0d;
		//System.out.println(spearmansCorrelationCoefficientScore);

	}

	private RankedFloatArray rankPositions(double[] f)
	{
		RankSample[] rs = new RankSample[f.length];
		for (int i = 0; i < f.length; i++)
		{
			rs[i] = new RankSample(i, f[i]);
		}
		// sort by value
		RankSampleValueComparator valueComp = new RankSampleValueComparator();
		Arrays.sort(rs, valueComp);
		// rank
		return rankSamples(rs);
	}

	/**
	 * Ranks a sorted array of RankSample based on value. If no ties are found then this is simply their array index number+1. (ie
	 * 1,2,3,4...) If ties are encountered, ties are assigned the average of their index positions+1. (ie if index+1's: 2,3,4 have the
	 * same absolute difference, all are assigned a rank of 3).
	 */
	private RankedFloatArray rankSamples(RankSample[] rs)
	{
		int num = rs.length;
		boolean tiesFound = false;
		// assign ranks as index+1
		for (int i = 0; i < num; i++)
		{
			rs[i].rank = i + 1;
		}
		// check for ties
		int start;
		int end;
		for (int i = 0; i < num; i++)
		{
			start = i;
			end = i;
			// advance end until the former and latter don't have the same value and the end
			// of the array hasn't been reached
			while (++end < num && rs[start].value == rs[end].value)
			{
			}
			// check if i was advanced
			if (end - start != 1)
			{// ties found
				tiesFound = true;
				// get average of ranks
				double ave = getAverageInts((int) rs[start].rank, (int) rs[end - 1].rank);
				// assign averages
				for (int x = start; x < end; x++)
					rs[x].rank = ave;
				// reset i
				i = end - 1;
			}
		}

		// sort by original position
		RankSampleIndexComparator indexComp = new RankSampleIndexComparator();
		Arrays.sort(rs, indexComp);
		// make float[] of ranks
		double[] ranks = new double[rs.length];
		for (int i = 0; i < rs.length; i++)
		{
			ranks[i] = rs[i].rank;
			// System.out.println("Fin "+rs[i].index+" "+rs[i].value+" "+rs[i].rank+" "+tiesFound);
		}
		rs = null;
		return new RankedFloatArray(tiesFound, ranks);

	}

	/**
	 * Gets the average of the integers bracketed and including the start and end. (i.e. 3,6 returns the average of 3+4+5+6/4= 4.5)
	 */
	private double getAverageInts(int start, int end)
	{
		int endOne = end + 1;
		int len = endOne - start;
		int sum = 0;
		for (int i = start; i < endOne; i++)
		{
			sum += i;
		}
		return (double) sum / (double) len;
	}

	/**
	 * @return the spearmansCorrelationCoefficientScore
	 */
	public double getSpearmansCorrelationCoefficientScore()
	{
		return spearmansCorrelationCoefficientScore;
	}

}
