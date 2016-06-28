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
package measures.similaritymeasures.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class CommonEntities
{
	private List<Entity> commonEntities =null;
	
	/**
	 * 
	 */
	public CommonEntities()
	{
		commonEntities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity entity){
		commonEntities.add(entity);
	}

	/**
	 * @return the commonEntities
	 */
	public List<Entity> getCommonEntities()
	{
		return commonEntities;
	}
	

	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (Entity entity : commonEntities){
			buffer.append(entity.toString()+"\n");
		}
		return buffer.toString();
	}
	
	
}
