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
package zarticles.inteliterm.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import measures.SimilarityMeasuresManager;
import measures.measureswrapper.MeasuresWrapper;
import measures.stsmmodels.PivotSimilarityModel;
import measures.stsmmodels.PivotsSimilarityModelWrapper;
import measures.stsmmodels.STSMModel;
import constantsfilesfolders.Constants;
import constantsfilesfolders.FilesAndFolders;
import corporamodel.corpus.documents.DocumentModel;
import corporamodel.corpus.documents.sentences.PhraseModel;
import corporamodel.corpus.documents.sentences.tokens.TokenModel;

/**
 * This class is responsible for processing the Semantic Textual Similarity between all documents in a specific directory.
 * The documents within the directory need to the be POSTagged using the {@link POSTagging} class.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class ProcessingSimilarity
{
	// path to the directory that contains all the files/documents
	private String pathToSourceDirectory = "";
	private SimilarityMeasuresManager simMeasures = null;
	// path to the CSV file where all the scores will be stored
	private String pathToCSVFile = "";
	private LoadStoreSTSModel lsSTSMModels = null;

	/**
	 * Constructor
	 */
	public ProcessingSimilarity(String pathToSourceDirectory, String language, String pathToCSVFile)
	{
		this.pathToSourceDirectory = pathToSourceDirectory;
		this.pathToCSVFile = pathToCSVFile;

		simMeasures = new SimilarityMeasuresManager(language);
		// if (language.equalsIgnoreCase(Constants.EN))
		// semMeasures = new SemanticMeasuresManager(language);
		// else System.err.println("Semantic Measures are only available for English, not for " + language);

		lsSTSMModels = new LoadStoreSTSModel();
		calculatingPivotSimilarity();
	}

	/**
	 * This method has the main algorithm to calculate the Semantic Textual Similarity between all the documents. The scores are stored in a
	 * CSV file.
	 */
	private void calculatingPivotSimilarity()
	{
		// getting files from path directory
		List<String> files = FilesAndFolders.getTXTfilesFromPath(pathToSourceDirectory);

		// document models
		DocumentModel pivotDocumentModel = null, nodeDocumentModel = null;

		// pivot and pivots models
		PivotSimilarityModel pivotModel = null;
		PivotsSimilarityModelWrapper pivotsModelsWrapper = null;

		// temporary counter
		int k = 0;
		boolean flag = true;
		/**
		 * for all the pivots
		 */
		for (int i = 0; i < files.size() - 1; i++)
		{
			// recreating the {@link DocumentModel}
			pivotDocumentModel = recreatingDocumentModel(pathToSourceDirectory, files.get(i), Constants.ENCODING_UTF8);

			System.out.print("pivot:" + (i + 1) + "/" + files.size() + "\t");
			System.out.println(pivotDocumentModel.getDocumentName());

			// creating pivot model. It will be used to store all the pairwise scores
			pivotModel = new PivotSimilarityModel(pivotDocumentModel.getDocumentName());

			/**
			 * lest's measure the similarity between the pivot and all the nodes
			 */
			for (int j = i + 1; j < files.size(); j++)
			{
				System.out.print("\t\t\tnode:" + j + "/" + files.size() + "\t");
				// recreating the {@link DocumentModel}
				nodeDocumentModel = recreatingDocumentModel(pathToSourceDirectory, files.get(j), Constants.ENCODING_UTF8);
				System.out.println(nodeDocumentModel.getDocumentName());

				// measuring the similarity between the pivot and the node
				STSMModel stsModel = paiwiseSimilarity(pivotDocumentModel, nodeDocumentModel);
				pivotModel.addPairwiseSimilarityModel(stsModel);
				// System.out.println(stsModel.toString());

				// if (k == 0) return;
			}

			/**
			 * let's store the pivots scores
			 */
			// this time I will store the scores for each pivot individually
			pivotsModelsWrapper = new PivotsSimilarityModelWrapper(pathToCSVFile);
			pivotsModelsWrapper.addPivotSimilarityModel(pivotModel);
			lsSTSMModels.writePivotsSTSMToCSV(pathToCSVFile, pivotsModelsWrapper, true, Constants.CSV_COLUMN_SEP, flag);
			flag = false;
		}
	}

	/**
	 * This method really measures the similarity between two documents (Semantic and Similarity Measures). Note: Semantic Measures only work
	 * for English!
	 * 
	 * @param pivotDocumentModel1
	 *           - pivot
	 * @param nodeDocumentModel2
	 *           - node
	 * @param pathToCSVFile
	 *           - path to the CSV file where all the scores will be stored
	 */
	private STSMModel paiwiseSimilarity(DocumentModel pivotDocumentModel1, DocumentModel nodeDocumentModel2)
	{
		// this wrapper will store all the measures
		MeasuresWrapper wrapper = new MeasuresWrapper();

		// for (String s : pivotDocumentModel1.getTokenisedTokensDocumentList()){
		// System.out.println(s);
		// }
		// for (String s : pivotDocumentModel1.getLemmatisedTokensDocumentList()){
		// System.out.println(s);
		// }
		// for (String s : pivotDocumentModel1.getStemmedTokensDocumentList()){
		// System.out.println(s);
		// }

		// System.out.println("--------");

		HashMap<String, Double> similarityMeasures_forTokens = simMeasures.getSimilarityValuesForTokens(
				toLower(pivotDocumentModel1.getTokenisedTokensDocumentList()), toLower(nodeDocumentModel2.getTokenisedTokensDocumentList()), true);

		HashMap<String, Double> similarityMeasures_forLemmas = simMeasures.getSimilarityValuesForLemmas(
				toLower(pivotDocumentModel1.getLemmatisedTokensDocumentList()), toLower(nodeDocumentModel2.getLemmatisedTokensDocumentList()), true);

		HashMap<String, Double> similarityMeasures_forStemms = simMeasures.getSimilarityValuesForStemms(
				toLower(pivotDocumentModel1.getStemmedTokensDocumentList()), toLower(nodeDocumentModel2.getStemmedTokensDocumentList()), true);

		// Wrapping the Token, Stemm and Lemma Measures into a {@link MeasuresWrapper}
		// System.out.println(similarityMeasures_forTokens.toString());
		wrapper.addMeasures(similarityMeasures_forTokens);
		// System.out.println(wrapper.toString());
		wrapper.addMeasures(similarityMeasures_forLemmas);
		wrapper.addMeasures(similarityMeasures_forStemms);

		// Creating and returning the STSModel
		return new STSMModel(pivotDocumentModel1.getDocumentName(), nodeDocumentModel2.getDocumentName(), wrapper);
	}

	private List<String> toLower(List<String> cenas)
	{
		List<String> temp = new ArrayList<String>();
		for (String s : cenas)
		{
			temp.add(s.toLowerCase());
		}
		// System.out.println(temp.toString());
		return temp;
	}

	/**
	 * Recreates the DocumentModel object from file.
	 * 
	 * @param pathToSourceDirectory
	 *           - path to the directory
	 * @param filename
	 *           - file name
	 * @param sourceEncoding
	 *           - source encoding
	 * @return {@link DocumentModel}
	 */
	private DocumentModel recreatingDocumentModel(String pathToSourceDirectory, String filename, String sourceEncoding)
	{
		// loading file content
		String pathToFile = pathToSourceDirectory + filename;
		StringBuffer fileContent = FilesAndFolders.loadTextFromFile(pathToFile, sourceEncoding);

		// splitting the content into sentences
		String[] sentences = fileContent.toString().split("\n");

		// recreating the document model
		DocumentModel documentModel = new DocumentModel(filename);
		PhraseModel phraseModel = null;
		for (int i = 0; i < sentences.length; i += 5)
		{
			if (i + 5 <= sentences.length)
			{
				phraseModel = recreatingPhraseModel(sentences[i], sentences[i + 1], sentences[i + 2], sentences[i + 3], sentences[i + 4]);
				if (phraseModel != null)
				{
					documentModel.addPhraseModel(phraseModel);
				}
			}
		}

		return documentModel;
	}

	/**
	 * From the sentences, this method recreates the PhraseModel
	 * 
	 * @param tokenSentence
	 * @param tagSentence
	 * @param lemmaSentence
	 * @param stemmSentence
	 * @param stopSentence
	 * @return {@link PhraseModel}
	 */
	private PhraseModel recreatingPhraseModel(String tokenSentence, String tagSentence, String lemmaSentence, String stemmSentence, String stopSentence)
	{
		PhraseModel phraseModel = new PhraseModel();
		TokenModel tokenModel = null;
		String[] tokSentence = tokenSentence.split(Constants.TAG_TOKEN)[1].split(" ");
		String[] taSentence = tagSentence.split(Constants.TAG_TAG)[1].split(" ");
		String[] lemSentence = lemmaSentence.split(Constants.TAG_LEMMA)[1].split(" ");
		String[] steSentence = stemmSentence.split(Constants.TAG_STEMM)[1].split(" ");
		String[] stoSentence = stopSentence.split(Constants.TAG_STOP)[1].split(" ");
		int size_tokSentence = tokSentence.length;
		int size_taSentence = taSentence.length;
		int size_lemSentence = lemSentence.length;
		int size_steSentence = steSentence.length;
		int size_stoSentence = stoSentence.length;

		if ((size_tokSentence != size_taSentence) || (size_tokSentence != size_lemSentence) || (size_tokSentence != size_steSentence)
				|| (size_tokSentence != size_stoSentence))
		{
			System.err
					.print(size_tokSentence + " " + size_taSentence + " " + size_lemSentence + " " + size_steSentence + " " + size_stoSentence + " | ");
			return null;
		}

		for (int j = 0; j < size_tokSentence; j++)
		{
			tokenModel = new TokenModel(tokSentence[j].trim(), taSentence[j].trim(), lemSentence[j].trim(), steSentence[j].trim());
			tokenModel.setIsStopWord(Boolean.valueOf(stoSentence[j].trim()));
			phraseModel.addTokenModelObject(tokenModel);
		}
		return phraseModel;
	}

	public static void main(String[] args)
	{
		ISA_article();

		// ProcessingSimilarity procSimilarity = null;
		// // English
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/ento/inteliterm_en_00/",
		// Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_ento_00.csv");
		// System.out.println("English inteliterm_en_00 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/ento/inteliterm_en_05/",
		// Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_ento_05.csv");
		// System.out.println("English inteliterm_en_05 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/ento/inteliterm_en_10/",
		// Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_ento_10.csv");
		// System.out.println("English inteliterm_en_10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/ento/inteliterm_en_15/",
		// Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_ento_15.csv");
		// System.out.println("English inteliterm_en_15 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/ento/inteliterm_en_20/",
		// Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_ento_20.csv");
		// System.out.println("English inteliterm_en_20 DONE");
		//
		// // Spanish
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/esto/inteliterm_es_00/",
		// Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_esto_00.csv");
		// System.out.println("Spanish inteliterm_es_00 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/esto/inteliterm_es_05/",
		// Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_esto_05.csv");
		// System.out.println("Spanish inteliterm_es_05 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/esto/inteliterm_es_10/",
		// Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_esto_10.csv");
		// System.out.println("Spanish inteliterm_es_10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/esto/inteliterm_es_15/",
		// Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_esto_15.csv");
		// System.out.println("Spanish inteliterm_es_15 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/esto/inteliterm_es_20/",
		// Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_esto_20.csv");
		// System.out.println("Spanish inteliterm_es_20 DONE");

		// Italian
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/it/inteliterm_it_00/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_it_00.csv");
		// System.out.println("Spanish inteliterm_it_00 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/it/inteliterm_it_05/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_it_05.csv");
		// System.out.println("Spanish inteliterm_it_05 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/it/inteliterm_it_10/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_it_10.csv");
		// System.out.println("Spanish inteliterm_it_10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/it/inteliterm_it_15/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_it_15.csv");
		// System.out.println("Spanish inteliterm_it_15 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/it/inteliterm_it_20/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_it_20.csv");
		// System.out.println("Spanish inteliterm_it_20 DONE");
	}

	private void TIA_article()
	{
		// English
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/en/inteliterm_en_00/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_en_00.csv");
		// System.out.println("English inteliterm_en_00 DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/en/inteliterm_en_05/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_en_05.csv");
		// System.out.println("English inteliterm_en_05 DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/en/inteliterm_en_10/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_en_10.csv");
		// System.out.println("English inteliterm_en_10 DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/en/inteliterm_en_15/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_en_15.csv");
		// System.out.println("English inteliterm_en_15 DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/pos/en/inteliterm_en_20/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/articleTIA/results/inteliterm_en_20.csv");
		// System.out.println("English inteliterm_en_20 DONE");
	}

	private static void ISA_article()
	{
		ProcessingSimilarity procSimilarity = null;

		// English
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/bootcatEN/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_bc.csv");
		// System.out.println("English Bootcat DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to.csv");
		// System.out.println("English TO DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/td/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_td.csv");
		// System.out.println("English TD DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_td10/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_td10.csv");
		// System.out.println("English TO_TD10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_td20/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_td20.csv");
		// System.out.println("English TO_TD20 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_td30/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_td30.csv");
		// System.out.println("English TO_TD30 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_td100/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_td100.csv");
		// System.out.println("English TO_TD100 DONE");
		// System.out.println("English TO_TD DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc10/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc10.csv");
		// System.out.println("English TO_BC10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc20/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc20.csv");
		// System.out.println("English TO_BC20 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc30/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc30.csv");
		// System.out.println("English TO_BC30 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc40/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc40.csv");
		// System.out.println("English TO_BC40 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc50/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc50.csv");
		// System.out.println("English TO_BC50 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc60/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc60.csv");
		// System.out.println("English TO_BC60 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc70/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc70.csv");
		// System.out.println("English TO_BC70 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc80/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc80.csv");
		// System.out.println("English TO_BC80 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc90/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc90.csv");
		// System.out.println("English TO_BC90 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/en/to_bc100/", Constants.EN,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/en_to_bc100.csv");
		// System.out.println("English TO_BC100 DONE");
		// System.out.println("English TO_BC DONE");

		/****************************************************************************************************************************
		 * */

		// // Spanish
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/bootcatES/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_bc.csv");
		// System.out.println("Spanish Bootcat DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to.csv");
		// System.out.println("Spanish TO DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/td/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_td.csv");
		// System.out.println("Spanish TD DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_td10/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_td10.csv");
		// System.out.println("Spanish TO_TD10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_td20/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_td20.csv");
		// System.out.println("Spanish TO_TD20 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_td30/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_td30.csv");
		// System.out.println("Spanish TO_TD30 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_td100/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_td100.csv");
		// System.out.println("Spanish TO_TD100 DONE");
		// System.out.println("Spanish TO_TD DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc10/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc10.csv");
		// System.out.println("Spanish TO_BC10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc20/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc20.csv");
		// System.out.println("Spanish TO_BC20 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc30/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc30.csv");
		// System.out.println("Spanish TO_BC30 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc40/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc40.csv");
		// System.out.println("Spanish TO_BC40 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc50/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc50.csv");
		// System.out.println("Spanish TO_BC50 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc60/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc60.csv");
		// System.out.println("Spanish TO_BC60 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc70/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc70.csv");
		// System.out.println("Spanish TO_BC70 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc80/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc80.csv");
		// System.out.println("Spanish TO_BC80 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc90/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc90.csv");
		// System.out.println("Spanish TO_BC90 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/es/to_bc100/", Constants.ES,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/es_to_bc100.csv");
		// System.out.println("Spanish TO_BC100 DONE");
		// System.out.println("Spanish TO_BC DONE");

		/****************************************************************************************************************************
		 * */

		// German
		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/bootcatDE/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_bc.csv");
		System.out.println("German Bootcat DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to.csv");
		System.out.println("German TO DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/td/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_td.csv");
		System.out.println("German TD DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_td10/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_td10.csv");
		System.out.println("German TO_TD10 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_td20/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_td20.csv");
		System.out.println("German TO_TD20 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_td30/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_td30.csv");
		System.out.println("German TO_TD30 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_td100/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_td100.csv");
		System.out.println("German TO_TD100 DONE");
		System.out.println("German TO_TD DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc10/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc10.csv");
		System.out.println("German TO_BC10 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc20/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc20.csv");
		System.out.println("German TO_BC20 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc30/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc30.csv");
		System.out.println("German TO_BC30 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc40/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc40.csv");
		System.out.println("German TO_BC40 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc50/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc50.csv");
		System.out.println("German TO_BC50 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc60/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc60.csv");
		System.out.println("German TO_BC60 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc70/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc70.csv");
		System.out.println("German TO_BC70 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc80/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc80.csv");
		System.out.println("German TO_BC80 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc90/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc90.csv");
		System.out.println("German TO_BC90 DONE");

		procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/de/to_bc100/", Constants.DE,
				"/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/de_to_bc100.csv");
		System.out.println("German TO_BC100 DONE");
		System.out.println("German TO_BC DONE");

		/****************************************************************************************************************************
		 * */

		// Italian

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/bootcatIT/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_bc.csv");
		// System.out.println("Italian Bootcat DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to.csv");
		// System.out.println("Italian TO DONE");

		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc10/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc10.csv");
		// System.out.println("Italian TO_BC10 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc20/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc20.csv");
		// System.out.println("Italian TO_BC20 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc30/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc30.csv");
		// System.out.println("Italian TO_BC30 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc40/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc40.csv");
		// System.out.println("Italian TO_BC40 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc50/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc50.csv");
		// System.out.println("Italian TO_BC50 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc60/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc60.csv");
		// System.out.println("Italian TO_BC60 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc70/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc70.csv");
		// System.out.println("Italian TO_BC70 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc80/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc80.csv");
		// System.out.println("Italian TO_BC80 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc90/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc90.csv");
		// System.out.println("Italian TO_BC90 DONE");
		//
		// procSimilarity = new ProcessingSimilarity("/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/pos/it/to_bc100/", Constants.IT,
		// "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/it_to_bc100.csv");
		// System.out.println("Italian TO_BC100 DONE");
		//
		// System.out.println("Italian TO_BC DONE");

		System.out.println("DONE");
	}
}
