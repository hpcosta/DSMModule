/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.entities;

/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class Entity
{

	private String entity = "";
	private int text1_observedValue;
	private int text2_observedValue;
	private double text1_rank = Double.MIN_VALUE;
	private double text2_rank = Double.MIN_VALUE;

	/**
	 * Default constructor.
	 */
	public Entity(String entity, int text1_observedValue, int text2_observedValue)
	{
		this.entity = entity;
		this.text1_observedValue = text1_observedValue;
		this.text2_observedValue = text2_observedValue;
	}

	/**
	 * Default constructor.
	 */
	public Entity(String entity, int text1_observedValue, double text1_rank, int text2_observedValue, double text2_rank)
	{
		this.entity = entity;
		this.text1_observedValue = text1_observedValue;
		this.text2_observedValue = text2_observedValue;
		this.text1_rank = text1_rank;
		this.text2_rank = text2_rank;
	}

	/**
	 * @return the entity
	 */
	public String getEntity()
	{
		return entity;
	}

	/**
	 * @return the text1_observedValue
	 */
	public int getText1_observedValue()
	{
		return text1_observedValue;
	}

	/**
	 * @return the text2_observedValue
	 */
	public int getText2_observedValue()
	{
		return text2_observedValue;
	}

	/**
	 * @return the text1_rank
	 */
	public double getText1_rank()
	{
		return text1_rank;
	}

	/**
	 * @return the text2_rank
	 */
	public double getText2_rank()
	{
		return text2_rank;
	}

	public String toString()
	{
		if (text1_rank == Double.MIN_VALUE && text2_rank == Double.MIN_VALUE)
			return entity + "\n\t" + text1_observedValue + " : " + text2_observedValue;
		else
			return entity + "\n\t" + text1_observedValue + " " + text1_rank + " : " + text2_observedValue + " " + text2_rank;
	}

}
