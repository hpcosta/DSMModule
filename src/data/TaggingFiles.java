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
package data;

import java.util.List;

import constantsfilesfolders.Constants;
import constantsfilesfolders.FilesAndFolders;
import corporamodel.corpus.documents.DocumentModel;
import corporamodel.corpus.documents.sentences.PhraseModel;
import wrappers.DocumentAnalyser;

/**
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class TaggingFiles
{
	private DocumentAnalyser docAnalyser = null;
	private String language = "";

	/**
	 * Constructor
	 */
	public TaggingFiles(String language)
	{
		this.language = language;
		docAnalyser = new DocumentAnalyser(language);
	}

	/**
	 * 
	 * Loads the file content and then saves the processed content into a new file
	 * 
	 * @param pathToSourceFile
	 *           - path to source file
	 * @param sourceEncoding
	 *           - source file encoding
	 * @param pathToTargetFile
	 *           - path to target file
	 * @param targetEncoding
	 *           - target file encoding
	 */
	public void taggingFile(String pathToSourceFile, String sourceEncoding, String pathToTargetFile, String targetEncoding)
	{
		tagFile(pathToSourceFile, sourceEncoding, pathToTargetFile, targetEncoding);
	}

	/**
	 * Loads all the files from a directory, processes them and finally the precessed content is stored into a new file.
	 * 
	 * @param pathToSourceDirectory
	 *           - path to source directory
	 * @param sourceEncoding
	 *           - source file encoding
	 * @param pathToTargetDirectory
	 *           - path to target directory
	 * @param targetEncoding
	 *           - target file encoding
	 */
	public void taggingFiles(String pathToSourceDirectory, String sourceEncoding, String pathToTargetDirectory, String targetEncoding)
	{
		List<String> filesName = FilesAndFolders.getTXTfilesFromPath(pathToSourceDirectory);
		System.out.println("Processing files:");
		for (String filename : filesName){
			System.out.println("> "+ filename);
			tagFile(pathToSourceDirectory + filename, sourceEncoding, pathToTargetDirectory + filename, targetEncoding);
		}
	}

	/**
	 * Loads the file content and then saves the processed content into a new file
	 * 
	 * @param pathToSourceFile
	 *           - path to source file
	 * @param sourceEncoding
	 *           - source file encoding
	 * @param pathToTargetFile
	 *           - path to target file
	 * @param targetEncoding
	 *           - target file encoding
	 */
	private void tagFile(String pathToSourceFile, String sourceEncoding, String pathToTargetFile, String targetEncoding)
	{
		StringBuffer content = FilesAndFolders.loadTextFromFile(pathToSourceFile, sourceEncoding);
		DocumentModel docModel = docAnalyser.getDocumentModel(content.toString());
		FilesAndFolders.saveData(docModel.getProcessedDocument(), false, pathToTargetFile, targetEncoding);
	}

}
