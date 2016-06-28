/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import measures.measureswrapper.MeasuresWrapper;
import measures.similaritymeasures.entities.CommonEntities;
import measures.similaritymeasures.entities.CommonEntitiesRecognizer;
import measures.similaritymeasures.measures.chisquare.ChiSquare;
import measures.similaritymeasures.measures.scc.SCCManager;
import nlp.tokenizer.MyTokenizer;
import constantsfilesfolders.Constants;

/**
 * This class is responsible for calling the ChiSquare and the Spearmen's Rank Correlation Coefficient. It can be used to measure the
 * similarity between two lemmatised sentences or two stemmatised sentences.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class MySimilarityMeasures
{
	private CommonEntitiesRecognizer commonEntitiesRecognizer = null;
	private CommonEntities commonEntities = null;

	private ChiSquare chiSquare = null;
	private SCCManager sccManager = null;

	/**
	 * Default constructor. The language is used to load the associated stopword list.
	 * 
	 * @param language
	 *           language
	 */
	public MySimilarityMeasures(String language)
	{
		commonEntitiesRecognizer = new CommonEntitiesRecognizer(language);

		chiSquare = new ChiSquare();
		sccManager = new SCCManager();
	}

	/**
	 * Receives two tokenized sentences and sends to them to the {@link ChiSquare} and {@link SCCManager} classes and them creates a
	 * {@link MeasuresWrapper} with the resulted values.
	 * 
	 * @param tokenizedText1
	 *           lemmatised text 1
	 * @param tokenizedText2
	 *           lemmatised text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return {@link MeasuresWrapper} containing all the measures
	 */
	public MeasuresWrapper getSimilarityValues_forTokens(List<String> tokenizedText1, List<String> tokenizedText2, boolean consideringStopwords)
	{
		MeasuresWrapper measuresWrapper = new MeasuresWrapper();
		
		//System.out.println(tokenizedText1);
		//System.out.println(tokenizedText2);
		
		commonEntities = commonEntitiesRecognizer.getCommonEntities(tokenizedText1, tokenizedText2, consideringStopwords);
		//System.out.println(commonEntities.getCommonEntities().size());
		
		measuresWrapper.addMeasure(Constants.MEASURE_NUMBEROFCOMMONTOKENS, commonEntities.getCommonEntities().size());

		HashMap<String, Double> _temp = getSimilarityValuesForText(tokenizedText1, tokenizedText2, consideringStopwords).getMeasures();
		for (String m : _temp.keySet())
		{
			measuresWrapper.addMeasure(Constants.MEASURE_TOKENS + m, _temp.get(m));
		}
		return measuresWrapper;
	}
	
	/**
	 * Receives two lemmatised sentences and sends to them to the {@link ChiSquare} and {@link SCCManager} classes and them creates a
	 * {@link MeasuresWrapper} with the resulted values.
	 * 
	 * @param lemmatisedText1
	 *           lemmatised text 1
	 * @param lemmatisedText2
	 *           lemmatised text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return {@link MeasuresWrapper} containing all the measures
	 */
	public MeasuresWrapper getSimilarityValues_forLemmas(List<String> lemmatisedText1, List<String> lemmatisedText2, boolean consideringStopwords)
	{
		//lemmatisedText1 = PublicFunctions.toLowerCase(lemmatisedText1);
		//lemmatisedText2 = PublicFunctions.toLowerCase(lemmatisedText2);
		
		MeasuresWrapper measuresWrapper = new MeasuresWrapper();
		commonEntities = commonEntitiesRecognizer.getCommonEntities(lemmatisedText1, lemmatisedText2, consideringStopwords);
		measuresWrapper.addMeasure(Constants.MEASURE_NUMBEROFCOMMONLEMMAS, commonEntities.getCommonEntities().size());

		HashMap<String, Double> _temp = getSimilarityValuesForText(lemmatisedText1, lemmatisedText2, consideringStopwords).getMeasures();
		for (String m : _temp.keySet())
		{
			measuresWrapper.addMeasure(Constants.MEASURE_LEMMAS + m, _temp.get(m));
		}
		return measuresWrapper;
	}

	/**
	 * Receives two stemmatised sentences and sends to them to the {@link ChiSquare} and {@link SCCManager} classes and them creates a
	 * {@link MeasuresWrapper} with the resulted values.
	 * 
	 * @param stemmedText1
	 *           stemmatised text 1
	 * @param stemmedText2
	 *           stemmatised text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return {@link MeasuresWrapper} containing all the measures
	 */
	public MeasuresWrapper getSimilarityValues_forStemms(List<String> stemmedText1, List<String> stemmedText2, boolean consideringStopwords)
	{
		//stemmedText1 = PublicFunctions.toLowerCase(stemmedText1);
		//stemmedText2 = PublicFunctions.toLowerCase(stemmedText2);
		MeasuresWrapper measuresWrapper = new MeasuresWrapper();
		commonEntities = commonEntitiesRecognizer.getCommonEntities(stemmedText1, stemmedText2, consideringStopwords);
		measuresWrapper.addMeasure(Constants.MEASURE_NUMBEROFCOMMONSTEMMS, commonEntities.getCommonEntities().size());

		HashMap<String, Double> _temp = getSimilarityValuesForText(stemmedText1, stemmedText2, consideringStopwords).getMeasures();
		for (String m : _temp.keySet())
		{
			measuresWrapper.addMeasure(Constants.MEASURE_STEMMS + m, _temp.get(m));
		}
		return measuresWrapper;
	}

	/**
	 * Receives two texts/sentences and sends to them to the {@link ChiSquare} and {@link SCCManager} classes and them creates a
	 * {@link MeasuresWrapper} with the resulted values.
	 * 
	 * @param text1
	 *           text 1
	 * @param text2
	 *           text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return {@link MeasuresWrapper} containing all the measures
	 */
	public MeasuresWrapper getSimilarityValues_forRawText(List<String> text1, List<String> text2, boolean consideringStopwords)
	{
		return getSimilarityValuesForText(text1, text2, consideringStopwords);
	}

	/**
	 * Calls a set of similarity measures. This method is called by the {@link #getSimilarityValues_forLemmas(List, List, boolean)} and by
	 * the {@link #getSimilarityValues_forStemms(List, List, boolean)}.
	 * 
	 * @param text1
	 *           text1 (lemmas, stemms or raw text)
	 * @param text2
	 *           text2 (lemmas, stemms or raw text)
	 * @param consideringStopwords
	 *           (true or false)
	 * @return {@link MeasuresWrapper} containing all the measures
	 */
	private MeasuresWrapper getSimilarityValuesForText(List<String> text1, List<String> text2, boolean consideringStopwords)
	{
		MeasuresWrapper measuresWrapper = new MeasuresWrapper();

		/**
		 * ########## Processing chiSquare
		 */
		chiSquare.calculatingChiSquare(commonEntities);
		measuresWrapper.addMeasure(Constants.MEASURE_CHISQUARE, chiSquare.getChiSquareScore());

		/**
		 * ########## Processing SCC - Spearman's Rank Correlation Coefficient
		 */
		sccManager.calculatingSCC(commonEntities, text1, text2);
		measuresWrapper.addMeasure(Constants.MEASURE_SCC, sccManager.getSpearmansCorrelationCoefficientScore());

		return measuresWrapper;
	}

	/**
	 * ================== For testing purposes ==================
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		MySimilarityMeasures myMeasures = new MySimilarityMeasures(Constants.ES);

		String rawTextES1 = "Spa & Beaut Pagin principalEstablecimientosOfertasSp & Detent a 280€Spa terminolog Par su bienest y su relax , Relais & Châteaux le propon aprovech los benefici de un Spa . ";
		// String rawTextES1 =
		// "En el término municipal de San Vicente del Raspeig se encuentra, desde su fundación en 1979, el campus de la Universidad de Alicante. ";
		String rawTextES2 = "spa Beaut servicio de OCIO Gimnasio bien equipar en el sexto planta con TV de pantalla plano área para actividad cardiopulmonares y musculación , tonificación y stretching Sauna finlandés para xxxx persona y solárium en el azotea ";

		ArrayList<String> tokensSentence1 = null;
		ArrayList<String> tokensSentence2 = null;
		MyTokenizer tokenizer = null;

		tokenizer = new MyTokenizer(Constants.ES);

		tokensSentence1 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES1);
		tokensSentence2 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES2);
		for(String s: tokensSentence1)
			System.out.println(s);
		
		for(String s: tokensSentence2)
			System.out.println(s);

		MeasuresWrapper mWrapper = myMeasures.getSimilarityValues_forLemmas(tokensSentence1, tokensSentence2, false);
		System.out.println(mWrapper.toString());
	}
}
