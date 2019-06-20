package Cheminformatics.Utilities;


import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
 
import java.io.File;
 
public class ConvertTOArff {
  /**
   * takes 2 arguments:
   * - CSV input file
   * - ARFF output file
   */
	public static void CSVToArff(String filePath) throws Exception {
		
		
		String arffFilePath = "/Users/xuan/Desktop/Output.arff";
		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(filePath));
		Instances data = loader.getDataSet();
 
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffFilePath));
		//saver.setDestination(new File(arffFilePath));
		saver.writeBatch();
	}
	
}