/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.measures.chisquare;

import java.util.ArrayList;
import java.util.List;

import measures.similaritymeasures.entities.CommonEntities;
import measures.similaritymeasures.entities.Entity;

/**
 * Computes the ChiSquare measure. ChiSquare: {max = 0; min = ?}
 * See: http://math.hws.edu/javamath/ryan/ChiSquare.html
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class ChiSquare
{
	private double chiSquareScore = Double.MAX_VALUE;

	/**
	 * Default constructor.
	 */
	public ChiSquare()
	{

	}

	/**
	 * Computes the ChiSquare.
	 * 
	 * @param commonEntities
	 *                {@link CommonEntities}
	 */
	public void calculatingChiSquare(CommonEntities commonEntities)
	{
		List<Double> rowTotal = new ArrayList<Double>();
		double text1_column_total = 0.0, text2_column_total = 0.0, total = 0.0;

		/**
		 * calculating the totals (rows and columns)
		 */
		for (Entity entity : commonEntities.getCommonEntities())
		{
			rowTotal.add((double) (entity.getText1_observedValue() + entity.getText2_observedValue()));
			text1_column_total += (double) entity.getText1_observedValue();
			text2_column_total += (double) entity.getText2_observedValue();
		}
		total = text1_column_total + text2_column_total;

		/**
		 * calculating chiSquare value
		 */
		int i = 0;
		double _temp_ei_d1 = 0.0, _temp_ei_d2 = 0.0;
		double _temp_ei_d1_expected = 0.0, _temp_ei_d2_expected = 0.0;
		chiSquareScore = 0.0;
		for (Entity entity : commonEntities.getCommonEntities())
		{
			_temp_ei_d1_expected = (text1_column_total / total) * rowTotal.get(i);
			_temp_ei_d2_expected = (text2_column_total / total) * rowTotal.get(i);

			_temp_ei_d1 = Math.pow((double) (entity.getText1_observedValue() - _temp_ei_d1_expected), 2) / _temp_ei_d1_expected;
			_temp_ei_d2 = Math.pow((double) (entity.getText2_observedValue() - _temp_ei_d2_expected), 2) / _temp_ei_d2_expected;

			chiSquareScore = chiSquareScore + _temp_ei_d1 + _temp_ei_d2;
			i++;
		}
	}

	/**
	 * @return the chiSquare score
	 */
	public double getChiSquareScore()
	{
		return chiSquareScore;
	}

}
