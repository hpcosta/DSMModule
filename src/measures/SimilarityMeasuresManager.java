/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import measures.similaritymeasures.MySimilarityMeasures;
import nlp.NLPManager;
import nlp.tokenizer.MyTokenizer;
import constantsfilesfolders.Constants;

/**
 * This class is responsible for calling the {@link MySimilarityMeasures} class. It can be used to measure the similarity between two
 * lemmatised texts/sentences or two stemmatised texts/sentences.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class SimilarityMeasuresManager
{
	private MySimilarityMeasures mySimilarityMeasures = null;

	/**
	 * Default constructor.
	 * 
	 * @param language
	 *           language
	 */

	public SimilarityMeasuresManager(String language)
	{
		mySimilarityMeasures = new MySimilarityMeasures(language);
	}

	/**
	 * Receives two tokenized texts/sentences and returns an hashMap with all the similarity scores (MeasureName - Score).
	 * 
	 * @param tokenizedText1
	 *           tokenized text 1
	 * @param tokenizedText2
	 *           tokenized text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return hashMap with all the similarity scores (MeasureName - Score)
	 */
	public HashMap<String, Double> getSimilarityValuesForTokens(List<String> tokenizedText1, List<String> tokenizedText2,
			boolean consideringStopwords)
	{
		return mySimilarityMeasures.getSimilarityValues_forTokens(tokenizedText1, tokenizedText2, consideringStopwords).getMeasures();
	}
	
	/**
	 * Receives two lemmatised texts/sentences and returns an hashMap with all the similarity scores (MeasureName - Score).
	 * 
	 * @param lemmatisedText1
	 *           lemmatised text 1
	 * @param lemmatisedText2
	 *           lemmatised text 2
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return hashMap with all the similarity scores (MeasureName - Score)
	 */
	public HashMap<String, Double> getSimilarityValuesForLemmas(List<String> lemmatisedText1, List<String> lemmatisedText2,
			boolean consideringStopwords)
	{
		return mySimilarityMeasures.getSimilarityValues_forLemmas(lemmatisedText1, lemmatisedText2, consideringStopwords).getMeasures();
	}

	/**
	 * Receives two stemmatised texts/sentences and returns an hashMap with all the similarity scores (MeasureName - Score).
	 * 
	 * @param stemmedText1
	 *           stemmatised text 1
	 * @param stemmedText2
	 *           stemmatised text 1
	 * @param consideringStopwords
	 *           true if you want to consider a stopword list
	 * @return hashMap with all the similarity scores (MeasureName - Score)
	 */
	public HashMap<String, Double> getSimilarityValuesForStemms(List<String> stemmedText1, List<String> stemmedText2, boolean consideringStopwords)
	{
		return mySimilarityMeasures.getSimilarityValues_forStemms(stemmedText1, stemmedText2, consideringStopwords).getMeasures();
	}

	/**
	 * ================== For testing purposes ==================
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		SimilarityMeasuresManager myMeasures = new SimilarityMeasuresManager(Constants.ES);

		//String rawTextES1 = "San Vicente del Raspeig (en valenciano y cooficialmente, Sant Vicent del Raspeig) es un municipio español situado en el noroeste del área metropolitana de Alicante, de la provincia de Alicante, en la Comunidad Valenciana (España). Cuenta con 55.781 habitantes (INE 2013).";
		 String rawTextES1 = "spa mice spa servicio de OCIO Gimnasio bien equipar en el sexto planta con TV de pantalla plano área para actividad cardiopulmonares y musculación , tonificación y stretching Sauna finlandés para xxxx persona y solárium en el azotea ";
		// "En el término municipal de San Vicente del Raspeig se encuentra, desde su fundación en 1979, el campus de la Universidad de Alicante. ";
		//String rawTextES2 = "En el término municipal de San Vicente del Raspeig se encuentra, desde su fundación en 1979, el campus de la Universidad de Alicante. ";
		String rawTextES2 = "Spa mouse servicio & Beaut Pagin principalEstablecimientosOfertasSp & Detent a 280€Spa terminolog Par su bienest y su relax , Relais & Châteaux le propon aprovech los benefici de un Spa . ";
		/*1) Tokenise the text*/
		ArrayList<String> tokensSentence1 = null;
		ArrayList<String> tokensSentence2 = null;
		MyTokenizer tokenizer = null;

		tokenizer = new MyTokenizer(Constants.ES);
		tokensSentence1 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES1);
		tokensSentence2 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES2);
		NLPManager nlp = new NLPManager(Constants.EN);
		nlp.getTaggedSentenceList(rawTextES1);
		List<String> s1 = nlp.getLemmatizedSentenceList(rawTextES1);
		nlp.getTaggedSentenceList(rawTextES2);
		List<String> s2 = nlp.getLemmatizedSentenceList(rawTextES2);
		
		System.err.println(s1.toString());
		System.err.println(s2.toString());
		
		
		HashMap<String, Double> similarityMeasures_forLemmas = myMeasures.getSimilarityValuesForLemmas(s1, s2, true);
		System.out.println(similarityMeasures_forLemmas.toString());
		
		nlp.getTaggedSentenceList(rawTextES1);
		s1 = nlp.getStemmedSentenceList(rawTextES1);
		
		nlp.getTaggedSentenceList(rawTextES2);
		s2 = nlp.getStemmedSentenceList(rawTextES2);
		
		HashMap<String, Double> similarityMeasures_forStemms = myMeasures.getSimilarityValuesForStemms(s1, s2, true);
		System.out.println(similarityMeasures_forStemms.toString());
	}
}
