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

import constantsfilesfolders.Constants;
import measures.stsmmodels.PivotSimilarityModel;
import measures.stsmmodels.PivotsSimilarityModelWrapper;
import measures.stsmmodels.STSMModel;
import data.csv.ReadWriteCSV;

/**
 * This class is responsible for reading and writing the information contained in the PivotsSimilarityModelWrapper object (i.e. all the information related with the pivots) into a and from a CSV file. 
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class LoadStoreSTSModel
{
	private ReadWriteCSV rwCSV = null;

	/**
	 * This method writes to a file only the averages of the pivots {@link PivotsSimilarityModels}. Be careful!!! This method should only be
	 * used if the Pivots scores are loaded from the CSV file!
	 * 
	 * @param pathToCSVFile
	 *           - file path
	 * @param pivotsSMWrapper
	 *           - PivotsSimilarityModelWrapper object
	 * @param append
	 *           - true if you want to append, false otherwise
	 * @param colSeparator
	 *           - usually, a comma (';')
	 */
	public void writePivotsSTSMAveragesToCSV(String pathToCSVFile, PivotsSimilarityModelWrapper pivotsSMWrapper, boolean append, char colSeparator)
	{
		rwCSV = new ReadWriteCSV();

		boolean flag = true;
		for (PivotSimilarityModel pivotSM : pivotsSMWrapper.getPivotsModels())
		{
			STSMModel stsmModelAverages = pivotSM.getSTSPivotAverage();

			if (flag == true)
			{
				
				rwCSV.write(pathToCSVFile, "SEP=;");
				rwCSV.write(pathToCSVFile, stsmModelAverages.getSTSMHeader(), true, colSeparator);
				flag = false;
			}
			rwCSV.write(pathToCSVFile, stsmModelAverages.getStringArraySTSMValues(), true, colSeparator);
		}
	}

	/**
	 * This method writes to a file all the pivot values {@link PivotsSimilarityModels}.
	 * 
	 * @param pathToCSVFile
	 *           - file path
	 * @param pivotsSMWrapper
	 *           - PivotsSimilarityModelWrapper object
	 * @param append
	 *           - true if you want to append, false otherwise
	 * @param colSeparator
	 *           - usually, a commas (';')
	 * @param writeHeader
	 *           - true if you want to write the header
	 */
	public void writePivotsSTSMToCSV(String pathToCSVFile, PivotsSimilarityModelWrapper pivotsSMWrapper, boolean append, char colSeparator,
			boolean writeHeader)
	{
		rwCSV = new ReadWriteCSV();

		for (PivotSimilarityModel pivotSM : pivotsSMWrapper.getPivotsModels())
		{
			for (STSMModel stsmModel : pivotSM.getPivotSTSMModels())
			{
				if (writeHeader == true)
				{
					rwCSV.write(pathToCSVFile, stsmModel.getSTSMHeader(), true, colSeparator);
					writeHeader = false;
				}
				rwCSV.write(pathToCSVFile, stsmModel.getStringArraySTSMValues(), true, colSeparator);
			}
		}
	}

	/**
	 * This method writes to a file all the pivot values {@link PivotsSimilarityModels}.
	 * 
	 * @param pathToCSVFile
	 *           - file path
	 * @param pivotSimilarityModel
	 *           - PivotsSimilarityModelWrapper object
	 * @param append
	 *           - true if you want to append, false otherwise
	 * @param colSeparator
	 *           - usually, a commas (';')
	 * @param writeHeader
	 *           - true if you want to write the header
	 */
	public void writePivotSTSMToCSV(String pathToCSVFile, PivotSimilarityModel pivotSimilarityModel, boolean append, char colSeparator,
			boolean writeHeader)
	{
		rwCSV = new ReadWriteCSV();

		for (STSMModel stsmModel : pivotSimilarityModel.getPivotSTSMModels())
		{
			if (writeHeader == true)
			{
				rwCSV.write(pathToCSVFile, stsmModel.getSTSMHeader(), true, colSeparator);
				writeHeader = false;
			}
			rwCSV.write(pathToCSVFile, stsmModel.getStringArraySTSMValues(), true, colSeparator);
		}

	}

	/**
	 * This method is responsible for reading a 'semantic textual similarity' csv file and recreate the PivotsSimilarityModelWrapper object.
	 * 
	 * @param pathToCSVFile
	 *           - path to the file
	 * @param columnSeparator
	 *           - usually, a comma (',')
	 * @param nColumns
	 *           - total number of columns to be read
	 * @param startingLine
	 *           - starting line
	 * @param startingColumn
	 *           - starting column
	 * @return {@link PivotsSimilarityModelWrapper}
	 */
	public PivotsSimilarityModelWrapper readSTSMFromCSV(String pathToCSVFile, char columnSeparator, int nColumns, int startingLine, int startingColumn)
	{
		// stores all the STSMeasures
		PivotsSimilarityModelWrapper pivotsSMWrapper = new PivotsSimilarityModelWrapper(pathToCSVFile);

		// stores all the temporary STSMModels
		HashMap<String, STSMModel> tempSTSMModelsList = new HashMap<String, STSMModel>();

		rwCSV = new ReadWriteCSV();

		List<String[]> data = rwCSV.read(pathToCSVFile, columnSeparator, nColumns, startingLine, startingColumn);

		if (data == null)
		{
			System.err.println("Error reading the data from the file " + pathToCSVFile);
			return null;
		} else
		{
			System.err.println("Loading STSMeasures from: " + pathToCSVFile);
		}

		String indexFileName = data.get(0)[0];

		STSMModel stsmModel = null;
		STSMModel temp_stsmModel = null;
		PivotSimilarityModel pivotSimilarityModel = new PivotSimilarityModel(indexFileName);

		String docName = "";
		for (String[] line : data)
		{
			// System.out.println(Arrays.toString(line));
			docName = line[0];

			stsmModel = new STSMModel(docName, line[1]);
			//System.out.println("pivot: " + docName + " node: " + line[1]);

			stsmModel.setNcommontokens(retrievingDouble(line[2]));
			stsmModel.setTokenScc(retrievingDouble(line[3]));
			stsmModel.setTokenChisquare(retrievingDouble(line[4]));

			stsmModel.setNcommonsteems(retrievingDouble(line[5]));
			stsmModel.setStemmScc(retrievingDouble(line[6]));
			stsmModel.setStemmChisquare(retrievingDouble(line[7]));

			stsmModel.setNcommonlemmas(retrievingDouble(line[8]));
			stsmModel.setLemmaScc(retrievingDouble(line[9]));
			stsmModel.setLemmaChisquare(retrievingDouble(line[10]));

			stsmModel.setKlDivergence(retrievingDouble(line[11]));
			stsmModel.setJensenShannon(retrievingDouble(line[12]));
			stsmModel.setCosine(retrievingDouble(line[13]));
			stsmModel.setJaccard(retrievingDouble(line[14]));
			stsmModel.setWeightedOverlap(retrievingDouble(line[15]));

			stsmModel.set_dis_klDivergence(retrievingDouble(line[16]));
			stsmModel.set_dis_jensenShannon(retrievingDouble(line[17]));
			stsmModel.set_dis_cosine(retrievingDouble(line[18]));
			stsmModel.set_dis_jaccard(retrievingDouble(line[19]));
			stsmModel.set_dis_weightedOverlap(retrievingDouble(line[20]));

			/**
			 * ------------- creating the inverse STSMModel ----------
			 */
			temp_stsmModel = new STSMModel(line[1], docName);

			temp_stsmModel.setNcommontokens(retrievingDouble(line[2]));
			temp_stsmModel.setTokenScc(retrievingDouble(line[3]));
			temp_stsmModel.setTokenChisquare(retrievingDouble(line[4]));

			temp_stsmModel.setNcommonsteems(retrievingDouble(line[5]));
			temp_stsmModel.setStemmScc(retrievingDouble(line[6]));
			temp_stsmModel.setStemmChisquare(retrievingDouble(line[7]));

			temp_stsmModel.setNcommonlemmas(retrievingDouble(line[8]));
			temp_stsmModel.setLemmaScc(retrievingDouble(line[9]));
			temp_stsmModel.setLemmaChisquare(retrievingDouble(line[10]));

			temp_stsmModel.setKlDivergence(retrievingDouble(line[11]));
			temp_stsmModel.setJensenShannon(retrievingDouble(line[12]));
			temp_stsmModel.setCosine(retrievingDouble(line[13]));
			temp_stsmModel.setJaccard(retrievingDouble(line[14]));
			temp_stsmModel.setWeightedOverlap(retrievingDouble(line[15]));

			temp_stsmModel.set_dis_klDivergence(retrievingDouble(line[16]));
			temp_stsmModel.set_dis_jensenShannon(retrievingDouble(line[17]));
			temp_stsmModel.set_dis_cosine(retrievingDouble(line[18]));
			temp_stsmModel.set_dis_jaccard(retrievingDouble(line[19]));
			temp_stsmModel.set_dis_weightedOverlap(retrievingDouble(line[20]));
			tempSTSMModelsList.put(line[1] + "#" + docName, temp_stsmModel);
			/**
			 * -----------------------------------------
			 */

			if (!docName.equalsIgnoreCase(indexFileName))
			{
				// adding this PivotSimilarityModel to the PivotsSimilarityModelWrapper
				pivotsSMWrapper.addPivotSimilarityModel(pivotSimilarityModel);
				// creates a new PivotSimilarityModel
				pivotSimilarityModel = new PivotSimilarityModel(docName);
				// refreshes the indexFileName
				indexFileName = docName;

				/**
				 * ------------- if inverse STSMModel exists lets add it -------
				 */
				HashMap<String, STSMModel> _temp = new HashMap<String, STSMModel>(tempSTSMModelsList);
				for (String s : _temp.keySet())
				{
					String id = s.split("#")[0];
					if (id.equalsIgnoreCase(indexFileName))
					{
						pivotSimilarityModel.addPairwiseSimilarityModel(_temp.get(s));
						// remove from the temp list
						tempSTSMModelsList.remove(s);
					}
				}
				/**
				 * ------------------------------------------------------------------------
				 */

			}
			// adds the STMModel to the PivotSimilarityModel
			pivotSimilarityModel.addPairwiseSimilarityModel(stsmModel);

		}

		// adds the last PivotSimilarityModel to the PivotsSimilarityMeasuresModel
		pivotsSMWrapper.addPivotSimilarityModel(pivotSimilarityModel);

		/**
		 * ------------- now lets add the last pivot from the inverse STSMModel -------
		 */
		boolean flag = true;
		for (String s : tempSTSMModelsList.keySet())
		{
			String pivot = s.split("#")[0];
			if (flag)
			{
				pivotSimilarityModel = new PivotSimilarityModel(pivot);
				flag = false;
			}
			pivotSimilarityModel.addPairwiseSimilarityModel(tempSTSMModelsList.get(s));

		}
		// adds the last PivotSimilarityModel from the inverse STSMModel to the PivotsSimilarityMeasuresModel
		pivotsSMWrapper.addPivotSimilarityModel(pivotSimilarityModel);
		/**
		 * ------------------------------------------------------------------------
		 */

		return pivotsSMWrapper;
	}

	/**
	 * retieves double number from strings
	 * 
	 * @param str
	 *           - string with the number
	 * @return double
	 */
	private double retrievingDouble(String str)
	{
		if (str.contains("\""))
		{
			return Double.parseDouble(str.split("\"")[1]);
		} else
		{
			return Double.parseDouble(str);
		}
	}

	public static void main(String args[])
	{
		String pathToSourceDatasets = "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/";
		String pathToTargetDatasets = "/Users/hpcosta/Docs/Corpus/ITELITERM/article_ISA/results/averages/";
		String fileComplete = "av_";
		List<String> datasets = new ArrayList<String>();
		
		//English
		datasets.add("en_to.csv");
		datasets.add("en_td.csv");
		datasets.add("en_to_td10.csv");
		datasets.add("en_to_td20.csv");
		datasets.add("en_to_td30.csv");
		datasets.add("en_to_td100.csv");
		datasets.add("en_bc.csv");
		datasets.add("en_to_bc10.csv");
		datasets.add("en_to_bc20.csv");
		datasets.add("en_to_bc30.csv");
		datasets.add("en_to_bc40.csv");
		datasets.add("en_to_bc50.csv");
		datasets.add("en_to_bc60.csv");
		datasets.add("en_to_bc70.csv");
		datasets.add("en_to_bc80.csv");
		datasets.add("en_to_bc90.csv");
		datasets.add("en_to_bc100.csv");		
		//Spanish
		datasets.add("es_to.csv");
		datasets.add("es_td.csv");
		datasets.add("es_to_td10.csv");
		datasets.add("es_to_td20.csv");
		datasets.add("es_to_td30.csv");
		datasets.add("es_to_td100.csv");
		datasets.add("es_bc.csv");
		datasets.add("es_to_bc10.csv");
		datasets.add("es_to_bc20.csv");
		datasets.add("es_to_bc30.csv");
		datasets.add("es_to_bc40.csv");
		datasets.add("es_to_bc50.csv");
		datasets.add("es_to_bc60.csv");
		datasets.add("es_to_bc70.csv");
		datasets.add("es_to_bc80.csv");
		datasets.add("es_to_bc90.csv");
		datasets.add("es_to_bc100.csv");
		//German
		datasets.add("de_to.csv");
		datasets.add("de_td.csv");
		datasets.add("de_to_td10.csv");
		datasets.add("de_to_td20.csv");
		datasets.add("de_to_td30.csv");
		datasets.add("de_to_td100.csv");
		datasets.add("de_bc.csv");
		datasets.add("de_to_bc10.csv");
		datasets.add("de_to_bc20.csv");
		datasets.add("de_to_bc30.csv");
		datasets.add("de_to_bc40.csv");
		datasets.add("de_to_bc50.csv");
		datasets.add("de_to_bc60.csv");
		datasets.add("de_to_bc70.csv");
		datasets.add("de_to_bc80.csv");
		datasets.add("de_to_bc90.csv");
		datasets.add("de_to_bc100.csv");

		//Italian
		datasets.add("it_to.csv");
		datasets.add("it_bc.csv");
		datasets.add("it_to_bc10.csv");
		datasets.add("it_to_bc20.csv");
		datasets.add("it_to_bc30.csv");
		datasets.add("it_to_bc40.csv");
		datasets.add("it_to_bc50.csv");
		datasets.add("it_to_bc60.csv");
		datasets.add("it_to_bc70.csv");
		datasets.add("it_to_bc80.csv");
		datasets.add("it_to_bc90.csv");
		datasets.add("it_to_bc100.csv");
		
		LoadStoreSTSModel saveModel = null;

		for (String file : datasets)
		{
			System.err.println(" Reading: " + file);
			saveModel = new LoadStoreSTSModel();
			PivotsSimilarityModelWrapper smWrapper = saveModel.readSTSMFromCSV(pathToSourceDatasets + file, Constants.CSV_COLUMN_SEP_READER, 21, 1, 0);

			// iterating over the pivots
			// for (PivotSimilarityModel pivotSM : smWrapper.getPivotsModels())
			// {
			// System.out.println("pivotFileName: " + pivotSM.getPivotFileName());
			// // iterating over the STSMModels
			// for (STSMModel stsmModel : pivotSM.getPivotSTSMModels())
			// {
			// System.out.println(stsmModel.getListSTSMValues().toString());
			// }
			// }
			
			// write example
			//saveModel.writePivotsSTSMToCSV(pathToTargetDatasets + fileComplete + file, smWrapper, true, Constants.CSV_COLUMN_SEP_READER, true);
			saveModel.writePivotsSTSMAveragesToCSV(pathToTargetDatasets + fileComplete + file, smWrapper, true, Constants.CSV_COLUMN_SEP_READER);
			System.err.println(" Done ");
		}
	}
}
