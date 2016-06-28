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
package measures;

import java.util.HashMap;
import java.util.List;

import wrappers.SentenceAnalyser;
import constantsfilesfolders.Constants;
import corporamodel.corpus.documents.sentences.PhraseModel;

/**
 * This class wraps the SimilarityMeasures and the SemanticMeasures for English
 * 
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class MeasuresManagerEN
{
	private SimilarityMeasuresManager similarityMeasures = null;
	private SemanticMeasuresManager semanticMeasures = null;

	/**
	 * Constructor
	 */
	public MeasuresManagerEN(String language)
	{
		if (!language.equalsIgnoreCase(Constants.EN))
		{
			System.err.println("MeasuresManager ERROR!!! \nSemanticMeasures only works for English!");
			return;
		} else
		{
			similarityMeasures = new SimilarityMeasuresManager(language);
			semanticMeasures = new SemanticMeasuresManager(language);
		}
	}

	/**
	 * Calculates the Semantic Similarity between two raw sentences.
	 * 
	 * @param rawSentence1
	 *           raw sentence 1
	 * @param rawSentence2
	 *           raw sentence 2
	 * @return an HashMap with Semantic Similarity Measures - with and without disambiguation.
	 */
	public HashMap<String, Double> getSemanticMeasures(String rawSentence1, String rawSentence2)
	{
		semanticMeasures.calculatingSemanticSimilarityScores(rawSentence1, rawSentence2);

		HashMap<String, Double> _semanticMeasures = new HashMap<String, Double>();
		HashMap<String, Double> _semanticMeasuresWith = semanticMeasures.getSemanticSimilarityMeasures_With_Disambiguation();
		HashMap<String, Double> _semanticMeasuresWithout = semanticMeasures.getSemanticSimilarityMeasures_WITHOUT_Disambiguation();

		for (String s : _semanticMeasuresWith.keySet())
			_semanticMeasures.put(s + Constants.MEASURE_DIS, _semanticMeasuresWith.get(s));
		for (String s : _semanticMeasuresWithout.keySet())
			_semanticMeasures.put(s, _semanticMeasuresWithout.get(s));

		return _semanticMeasures;
	}

	/**
	 * Calculates the Similarity between two lemmatised sentences.
	 * 
	 * @param lemmatisedSentence1
	 *           lemmatised sentence 1
	 * @param lemmatisedSentence2
	 *           lemmatised sentence 2
	 * @param consideringStopwords
	 *           true if you want to use a stopword list
	 * @return an HashMap with Similarity Measures
	 */
	public HashMap<String, Double> getSimilarityMeasuresForLemmas(List<String> lemmatisedSentence1, List<String> lemmatisedSentence2,
			boolean consideringStopwords)
	{
		return similarityMeasures.getSimilarityValuesForLemmas(lemmatisedSentence1, lemmatisedSentence2, consideringStopwords);
	}

	/**
	 * Calculates the Similarity between two stemmatised sentences.
	 * 
	 * @param stemmedSentence1
	 *           stemmatised sentence 1
	 * @param stemmedSentence2
	 *           stemmatised sentence 2
	 * @param consideringStopwords
	 *           true if you want to use a stopword list
	 * @return an HashMap with Similarity Measures
	 */
	public HashMap<String, Double> getSimilarityMeasuresForStemms(List<String> stemmedSentence1, List<String> stemmedSentence2,
			boolean consideringStopwords)
	{
		return similarityMeasures.getSimilarityValuesForStemms(stemmedSentence1, stemmedSentence2, consideringStopwords);
	}

	/**
	 * ================== For testing purposes ==================
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		demoMeasures();
	}

	/**
	 * ================== Testing Measures ==================
	 */
	public static void demoMeasures()
	{
		MeasuresManagerEN mManager = new MeasuresManagerEN(Constants.EN);
		// String sentence1 = "The bird is bathing in the sink.";
		// String sentence2 = "Birdie is washing itself in the water basin.";
		// String sentence1 = "This guy is a bad ass.";
		// String sentence2 = "These guys are bad ass.";
		String rawSentence1 = "The udders of a dairy cow that is standing in a pasture near a large building.";
		String rawSentence2 = "A cows ass and some buildings.";

		/*****************************************************************/
		/** Semantic Similarity Measures - only for English */
		/*****************************************************************/
		HashMap<String, Double> semanticSimilarityMeasures_withDisambiguation = mManager.getSemanticMeasures(rawSentence1, rawSentence2);
		System.out
				.println("- Semantic Similarity Scores with and WITHOUT desambiguation! Measures ending eith '_dis' means that disambiguation is considered.");
		System.out.println(semanticSimilarityMeasures_withDisambiguation.toString());

		/*****************************************************************/
		/** Similarity Measures */
		/*****************************************************************/
		/**
		 * 1) First we need to lemmatise the sentences.
		 * 1.1) to do that, we need to call the SentenceAnalyser and Tagg the sentences first.
		 * Only works for English, Spanish and Portuguese.
		 */
		SentenceAnalyser sa = new SentenceAnalyser(Constants.ES);
		String rawTextES1 = "San Vicente del Raspeig (en valenciano y cooficialmente, Sant Vicent del Raspeig) es un municipio español situado en el noroeste del área metropolitana de Alicante, de la provincia de Alicante, en la Comunidad Valenciana (España). Cuenta con 55.781 habitantes (INE 2013).";
		String rawTextES2 = "En el término municipal de San Vicente del Raspeig se encuentra, desde su fundación en 1979, el campus de la Universidad de Alicante. ";

		PhraseModel phraseModelSentenceES1 = sa.getPhraseModel(rawTextES1);
		PhraseModel phraseModelSentenceES2 = sa.getPhraseModel(rawTextES2);
		
		List<String> lemmaTextES1 = phraseModelSentenceES1.getLemmatisedSentenceList();
		List<String> lemmaTextES2 = phraseModelSentenceES2.getLemmatisedSentenceList();
		List<String> stemmTextES1 = phraseModelSentenceES1.getStemmedSentenceList();
		List<String> stemmTextES2 = phraseModelSentenceES2.getStemmedSentenceList();

		HashMap<String, Double> similarityMeasures_forLemmas = mManager.getSimilarityMeasuresForLemmas(lemmaTextES1, lemmaTextES2, true);
		System.out.println("- similarity Measures for Lemmas: ");
		System.out.println(similarityMeasures_forLemmas.toString());

		HashMap<String, Double> similarityMeasures_forStemms = mManager.getSimilarityMeasuresForStemms(stemmTextES1, stemmTextES2, true);
		System.out.println("- similarity Measures for Stemms: ");
		System.out.println(similarityMeasures_forStemms.toString());
	}

}
