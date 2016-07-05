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
import constantsfilesfolders.FilesAndFolders;

/**
 * Converts the CP1252 encoding to UTF-8
 * 
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class ConvertEncodingToUTF8
{

	public static void main(String args[])
	{
		// String oldDirectoryEN_to = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/en/to/";
		// String oldDirectoryEN_totd = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/en/totd/";
		// String oldDirectoryEN_europarl = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/en-europarl/";
		// String newDirectoryEN_to = IntelitermConstants.SOURCE_RAW_EN_TO;
		// String newDirectoryEN_totd = IntelitermConstants.SOURCE_RAW_EN_TOTD;
		// String newDirectoryEN_totd = IntelitermConstants.SOURCE_RAW_EN_EUROPARL;

		String oldDirectoryIT_to = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/it/to/";
		// String oldDirectoryIT_totd = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/it/totd/";
		String newDirectoryIT_to = IntelitermConstants.SOURCE_RAW_IT_TO;
		// String newDirectoryIT_totd = IntelitermConstants.SOURCE_RAW_IT_TOTD;

		String oldDirectoryDE_to = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/de/to/";
		String oldDirectoryDE_totd = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/de/totd/";
		String newDirectoryDE_to = IntelitermConstants.SOURCE_RAW_DE_TO;
		String newDirectoryDE_totd = IntelitermConstants.SOURCE_RAW_DE_TOTD;

		// String oldDirectoryES_to = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/es/to/";
		// String oldDirectoryES_totd = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/es/totd/";
		// String oldDirectoryES_europarl = "/Users/hpcosta/Docs/Corpus/ITELITERM/article/raw/withoutEncoding/es/es-europarl/";
		// String newDirectoryES_to = IntelitermConstants.SOURCE_RAW_ES_TO;
		// String newDirectoryES_totd = IntelitermConstants.SOURCE_RAW_ES_TOTD;
		// String newDirectoryES_europarl = IntelitermConstants.SOURCE_RAW_ES_EUROPARL;

		// Reading CP1252 and converting them to UTF-8
		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryEN_to, Constants.ENCODING_CP1252, newDirectoryEN_to, Constants.ENCODING_UTF8);
		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryEN_totd, Constants.ENCODING_CP1252, newDirectoryEN_totd,
		// Constants.ENCODING_UTF8);

		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryES_to, Constants.ENCODING_CP1252, newDirectoryES_to, Constants.ENCODING_UTF8);
		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryES_totd, Constants.ENCODING_CP1252, newDirectoryES_totd,
		// Constants.ENCODING_UTF8);

		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryES_europarl, Constants.ENCODING_CP1252, newDirectoryES_europarl,
		// Constants.ENCODING_UTF8);
		// FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryEN_europarl, Constants.ENCODING_CP1252, newDirectoryES_europarl,
		// Constants.ENCODING_UTF8);

		FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryIT_to, Constants.ENCODING_CP1252, newDirectoryIT_to, Constants.ENCODING_UTF8);

		FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryDE_to, Constants.ENCODING_CP1252, newDirectoryDE_to, Constants.ENCODING_UTF8);
		FilesAndFolders.decodingEncodingTXTFiles(oldDirectoryDE_totd, Constants.ENCODING_CP1252, newDirectoryDE_totd, Constants.ENCODING_UTF8);

	}

}
