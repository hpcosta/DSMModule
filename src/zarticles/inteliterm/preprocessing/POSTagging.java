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
package zarticles.inteliterm.preprocessing;

import zarticles.inteliterm.IntelitermConstants;
import constantsfilesfolders.Constants;
import data.TaggingFiles;

/**
 * Tags the INTELITERM files
 * 
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class POSTagging
{

	public static void main(String args[])
	{
		TaggingFiles tg = null;

		// tg = new TaggingFiles(Constants.EN);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_EN_TO, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_EN_TO,
		// Constants.ENCODING_UTF8);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_EN_TOTD, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_EN_TOTD,
		// Constants.ENCODING_UTF8);

		// tg = new TaggingFiles(Constants.ES);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_ES_TO, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_ES_TO,
		// Constants.ENCODING_UTF8);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_ES_TOTD, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_ES_TOTD,
		// Constants.ENCODING_UTF8);

		// tg = new TaggingFiles(Constants.ES);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_ES_EUROPARL, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_ES_EUROPARL,
		// Constants.ENCODING_UTF8);
		// tg = new TaggingFiles(Constants.EN);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_EN_EUROPARL, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_EN_EUROPARL,
		// Constants.ENCODING_UTF8);

		// tg = new TaggingFiles(Constants.IT);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_IT_TO, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_IT_TO,
		// Constants.ENCODING_UTF8);
		// tg.taggingFiles(IntelitermConstants.SOURCE_RAW_IT_EUROPARL, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_IT_EUROPARL,
		// Constants.ENCODING_UTF8);
		
		//tg = new TaggingFiles(Constants.ES);
		//tg.taggingFiles(IntelitermConstants.SOURCE_RAW_ES_BOOTCAT, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_ES_BOOTCAT, Constants.ENCODING_UTF8);

		tg = new TaggingFiles(Constants.IT);
		tg.taggingFiles(IntelitermConstants.SOURCE_RAW_IT_BOOTCAT, Constants.ENCODING_UTF8, IntelitermConstants.SOURCE_POS_IT_BOOTCAT, Constants.ENCODING_UTF8);
		


	}

}
