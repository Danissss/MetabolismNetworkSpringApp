package Xuan.DrugExporter;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class RunClassification {
	
	private static String currend_dir = System.getProperty("user.dir");
	
	/**
	 * 
	 * @param num_of_attribute
	 * @return
	 */
	public ArrayList<Attribute> GenerateAttributeName(int num_of_attribute){
		ArrayList<Attribute> output = new ArrayList<Attribute>();
		for(int i = 0; i < num_of_attribute; i++) {
			Attribute tmp = new Attribute(String.format("Attribute_%d", i));
			output.add(tmp);
		}
		
		return output;
	}
		
	
	/**
	 * 
	 * @param option
	 * @param input
	 * @return
	 */
	public ArrayList<Double> GetRawInput(String option, String input){

		ArrayList<Double> output = new ArrayList<Double>();
		String s = null;
		Boolean error = null;
		try {
			String directoryName = String.format("%s/Script/GenerateSingleDataPoint", currend_dir);
			ProcessBuilder pb = new ProcessBuilder(directoryName, option, input);
//			pb.directory(new File(directoryName));
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new 
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new 
					InputStreamReader(p.getErrorStream()));
			// can't tell the type of error;
			// have to ensure user that the input has to be standard
			if (error = stdError.readLine() != null) {
				if(error == true) {
					return null;
					}
				}
			
			while ((s = stdInput.readLine()) != null) {
				String[] descriptorValue = s.split(",");
//				System.out.println(Arrays.toString(descriptorValue));
//				System.out.println(Arrays.toString(descriptorValue));
				for(int i = 0; i < descriptorValue.length; i++) {
					//TODO: use try catch to temporary fix
					try {
						if(descriptorValue[i].equals("inf") || descriptorValue[i].equals("nan")) {
							Double val = new Double(0.0);
							output.add(val);
						}else {
							// System.out.println(descriptorValue[i]);
							Double val = Double.valueOf(descriptorValue[i].replace("[", "").replace("]", ""));
							output.add(val);
						}
					}
					catch (Exception e) {
						output.add(new Double(0.0));
					}
				}
				
			}
			
			return output;
			
		}catch (IOException e) {
			System.out.println(e);
		}
		
		
		return output;
	}
	
	
	/**
	 * create instance from python script
	 * @param input
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Instances CreateTestingInstance(String option, String input, String model_type) {
		
		
		ArrayList<Double> descriptorValue = GetRawInput(option,input);
		int descriptorValueLength = descriptorValue.size();
		ArrayList<Attribute> attribute_al = GenerateAttributeName(descriptorValueLength);
		
		
		// Class attribute
		FastVector<String> association = new FastVector<String>();
		
		
		if (model_type.equals("substrate")) {
			
			association.addElement("substrate");
			association.addElement("non-substrate");
		}
		else if(model_type.equals("inhibitor")) {
			association.addElement("inhibitor");
			association.addElement("non-inhibitor");
		}
		
		
		Attribute class_attribute = new Attribute("Class",association);
		attribute_al.add(class_attribute);
		 
		Instances test_instance = new Instances("Rel",attribute_al,1);
		test_instance.setClassIndex(class_attribute.index());
		
		int length = attribute_al.size();
		
		Instance sample = new DenseInstance(length); 
		for(int vidx = 0; vidx < descriptorValue.size(); vidx++){
			Attribute att = attribute_al.get(vidx);
			Double vle_string = descriptorValue.get(vidx);
			sample.setValue(att, vle_string);			
		}
		// it doesn't matter if set the class_attribute here; gonna change later
		if(model_type.equals("substrate")) {
			sample.setValue(class_attribute, "non-substrate");
		}else if(model_type.equals("inhibitor")) {
			sample.setValue(class_attribute, "non-inhibitor");
		}
		
		test_instance.add(sample);

		
		return test_instance;
		
	}
	
	
	/**
	 * 
	 * @param model_type {substrate, inhibitor}
	 * @param ProteinName {BCRP, MDR1, MRP2, MRP1}
	 * @return
	 * @throws Exception 
	 */
	public String ClassifyInstance(String model_type, String ProteinName, Instances testInstance) throws Exception {
		
		
		String modelPath = new String();
		if(ProteinName.equals("MDR1")) {
			if(model_type.equals("substrate")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			else if(model_type.equals("inhibitor")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			
			
		}
		else if (ProteinName.equals("BCRP")) {
			if(model_type.equals("substrate")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			else if(model_type.equals("inhibitor")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			
			
			
			
		}
		else if (ProteinName.equals("MRP1")) {
			if(model_type.equals("substrate")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			else if(model_type.equals("inhibitor")) {
				modelPath = null;
			}
			
			
			
		}
		else if(ProteinName.equals("MRP2")) {
			if(model_type.equals("substrate")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			else if(model_type.equals("inhibitor")) {
				modelPath = String.format("%s/Model/%s%s.model", currend_dir, ProteinName,model_type.toUpperCase());
			}
			
			
		}else {
			modelPath = null;
		}
		
		if (modelPath == null) {
			return null;
		}else {
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(modelPath);
//			double result = cls.classifyInstance(testInstance.firstInstance());
			String output_instring = testInstance.classAttribute().value((int) cls.classifyInstance(testInstance.instance(0)));
			

			return output_instring;
			
		}
	}
		
		
		

	
	/**
	 * args[0] = model_type
	 * args[1] = ProteinName
	 * args[2] = smiles
	 * @param args
	 */
//	public static void main(String[] args) {
//		String input = "[H]OC(=O)[C@@]([H])(N([H])[H])C([H])([H])C1=C([H])N(C([H])=N1)C([H])([H])[H]";
//		// System.out.println(String.format("%s;%s;%s", args[0],args[1],args[2]));
//		RunClassification testset = new RunClassification();
////		Instances testInstance = testset.CreateTestingInstance("-s",args[2], args[0]);
//		Instances testInstance = testset.CreateTestingInstance("-s", input, "substrate");
////		System.out.println(testInstance.instance(0).numValues());
//		
//		try {
//			// String model_type, String ProteinName, Instances testInstance
////			String classified_output = testset.ClassifyInstance(args[0], args[1], testInstance);
//			String classified_output = testset.ClassifyInstance("substrate", "MDR1", testInstance);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println(e);
//		}
//		
//		
//	}
}
