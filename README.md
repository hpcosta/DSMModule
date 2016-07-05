DSMModule
===

CONTENTS
=========================

1. [Introduction](#1-introduction)
2. [Technical Information](#2-technical-information)
3. [Installation](#3-installation)
 	- 3.1. [External Libraries](#31-external-libraries)
    - 3.1.1 [NLP libraries](#32-nlp-libraries)
    - 3.1.2 [Semantic Similarity Libraries](#32-semantic-similarity-libraries)
4. [Requirements](#4-requirements)
5. [License](#5-license)



1. INTRODUCTION
=========================

DSMModule (Distributional Similarity Measures Module) aims at offering the user with a simple, yet efficient program capable of measuring and ranking either sentences or documents based on their similarity scores. Decisions at the outset of compiling a comparable corpus are of crucial importance for how the corpus is to be built and analysed later on. The DSMModule brings together methods from different areas of knowledge with the purpose of accessing, measuring and ranking documents based on their shared content, and consequently help researchers decide whether a specific document should be integrated in the corpus or not. 




2. TECHNICAL INFORMATION
=========================

* This program provides several abstraction methods to assess, compare and rank documents acording to their internal degree of similarity. In detail, the DSMModule allows you to compute the similarity between sentences, documents and corpora. The similarity can be computed using tokens, lemmas or stems.
	* The SimilarityMeasuresManager class wraps all the distributional similarity measures offered by the DSMModule. Within the SimilarityMeasuresManager class you will find a demo that demonstrates how you can use them. Please have a closer look at the main method located at *'src/measures/SimilarityMeasuresManager'*
		* SimilarityMeasuresManager myMeasures = new SimilarityMeasuresManager(Constants.EN); // receives *the language*
		* myMeasures.getSimilarityValuesForLemmas(s1, s2, true) // computes the similarity between two sentences
		* to compute the similarity between documents or corpora take a look at the class ProcessingSimilarity located within the package *'src/zarticles/inteliterm/processing/'* 

* Apart from that this program also includes several abstraction methods to perform various NLP tasks, such as: POS Tagging (TreeTagger); Lemmatisation (TreeTagger); Stemming (Snowball); Tokenisation (OpenNLP); Sentence Delimitation (OpenNLP); NER (OpenNLP); and Stopword Checker. Hereafter we describe how these methods can be called.
	* NLPManager nlpManager = new NLPManager(Constants.EN); // receives *the language*
	* The NLPManager class wraps all the NLP methods offered by the PreProcessor (http://github.com/hpcosta/PreProcessor). Within this class you will find a demo() that demonstrates how you can use all these methods for various languages. Please have a closer look at the demo() method located at *'src/nlp/NLPManager'*

* Please read the following publications for more information about the program and how it can be used in real scenarios: "Measuring the Relatedness between Documents in Comparable Corpora" (http://eden.dei.uc.pt/~hpcosta/docs/papers/201511-TIA.pdf); "Assessing Comparable Corpora through Distributional Similarity Measures" (http://eden.dei.uc.pt/~hpcosta/docs/papers/201506-EXPERT_ESR03.pdf); and, Compilação de Corpos Comparáveis Especializados: Devemos sempre confiar nas Ferramentas de Compilação Semi-automáticas? (http://eden.dei.uc.pt/~hpcosta/docs/papers/201606-Linguamatica.pdf).




3. INSTALATION
=========================

1. Import the project to your Java editor.

2. The folder 'config' and 'internalResources' should be at the same level as the src folder.
	* The folder 'internalResources' contains models for the:
		* TreeTagger (English, French, German, Italian, Portuguese and Spanish)
		* OpenNLP (tokeniser, sentence splitter and NER - only for English)
		* Stopword files (German, English, Italian, Portuguese and Spanish)

	* The folder 'config' contains configuration files for the TreeTagger. You will need have the TreeTagger installed in your computer an configure the treetagger.properties file.





## 3.1 External Libraries 

This section is important to let you know what libraries are used in this project, as well as to know how to update the resources or models.

#### 3.1.1 Distributional Similarity Measures
	* Does not require external libraries, all the mathematical methods where manually implemented and tested.

#### 3.1.2 NLP libraries
	* TreeTagger
		* provides a POS Tagger for EN, SP, PT, FR, DE, IT and RU
		* The following java library allows to use TreeTagger in Java.
			* org.annolab.tt4j-1.0.15

	* Stemmer 
		* provides a Stemmer for EN, SP, PT, FR, DE, IT and RU
		* the following java library allows to use Stemmer in Java]
			* org.tartarus.snowball

	* OpenNLP
		* provides a **sentence splitter** and **tokenization** in EN, but can be used for at least EN, PT and SP
		* also provides **NER** for EN and SP, see models available through http://opennlp.sourceforge.net/models-1.5/
		* the following java library allows to use OpenNLP in Java
			* opennlp-maxent-3.0.3;
			* opennlp-tools-1.5.3; 
			* opennlp-uima-1.5.3
	
		* you can find these models inside the project folder, more specificaly in the "/resources/opennlpmodels/..." folder.
			* contains the following models for English:
				* Date name finder model.			
				* Location name finder model.		
				* Money name finder model.		
				* Organization name finder model.	
				* Percentage name finder model.	
				* Person name finder model.		
				* Time name finder model.
			* and the following models for Spanish:
				* Location name finder model, trained on conll02 shared task data.
				* Organization name finder model, trained on conll02 shared task data.	
				* Person name finder model, trained on conll02 shared task data.	
				* Misc name finder model, trained on conll02 shared task data.
			* the English and Spanish models are loaded by the 'NEREnModelsLoader' and 'NEREsModelsLoader' classes, respectively.
	* CSV
		* used to store the similarity socores
			* opencsv-3.3.jar



4. REQUIREMENTS
=========================

- Java 6 (JRE 1.6) or higher

- Several models (already included in the 'internalResources' folder)



5. LICENSE
=========================

Copyright (c) 2013-2016 
Hernani Costa @LEXYTRAD, University of Malaga, Spain. 
All rights reserved.

For more information please contact:

> hercos (at) uma (dot) es



### Follow me on
<!-- Please don't remove this: Grab your social icons from https://github.com/carlsednaoui/gitsocial -->

<!-- display the social media buttons in your README -->

[![alt text][1.1]][1]
[![alt text][2.1]][2]
[![alt text][3.1]][3]
[![alt text][4.1]][4]



<!-- links to social media icons -->
<!-- no need to change these -->

<!-- icons with padding -->

[1.1]: http://i.imgur.com/tXSoThF.png (twitter icon with padding)
[2.1]: http://i.imgur.com/P3YfQoD.png (facebook icon with padding)
[3.1]: http://i.imgur.com/yCsTjba.png (google plus icon with padding)
[4.1]: http://i.imgur.com/0o48UoR.png (github icon with padding)


<!-- links to your social media accounts -->
<!-- update these accordingly -->

[1]: https://twitter.com/#!/hernanimax
[2]: https://www.facebook.com/hernani.costa.161
[3]: https://plus.google.com/+HernaniCosta
[4]: https://github.com/hpcosta

