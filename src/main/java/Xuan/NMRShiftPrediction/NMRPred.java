package Xuan.NMRShiftPrediction;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.io.listener.PropertiesListener;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;





public class NMRPred {
	
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
		Attribute shift = new Attribute("Shift");
		output.add(shift);
		return output;
	}
	
	
	/**
	 * read sdf file
	 * @param pathToInputFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws CDKException
	 */
	public static IAtomContainerSet readFile(String pathToInputFile)
			throws FileNotFoundException, CDKException {
		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(pathToInputFile),
				bldr);
		Properties prop = new Properties();
		prop.setProperty("ForceReadAs3DCoordinates", "true");
		PropertiesListener listener = new PropertiesListener(prop);
		sdfr.addChemObjectIOListener(listener);
		sdfr.customizeJob();
		IAtomContainerSet MOLS = DefaultChemObjectBuilder.getInstance().newInstance(
				IAtomContainerSet.class);
		while (sdfr.hasNext())
				MOLS.addAtomContainer(sdfr.next());
		return MOLS;

	}
	
	/**
	 * helper function to predict the 13C shift based on HoseCode
	 * @param mole
	 * @param solvent
	 * @return
	 * @throws FileNotFoundException
	 * @throws CDKException
	 * @throws SQLException
	 */
	public HashMap<Integer, Double> Retrive13CHoseCode(IAtomContainer mole, String solvent) throws FileNotFoundException, CDKException, SQLException{
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		HoseCodeEvaluator HCE = new HoseCodeEvaluator();

		for(int i = 0; i < mole.getAtomCount(); i++) {
			IAtom atom = mole.getAtom(i);
			if(atom.getSymbol() == "C") {
				Double shift = HCE.RunHose13CPrediction(mole,atom,solvent);
				result.put(atom.getIndex(), shift);
			}
		}
		
		return result;
	}
	
	
	/**
	 * helper function to predict the 1H shift based on HoseCode
	 * @param mole
	 * @param solvent
	 * @return
	 * @throws FileNotFoundException
	 * @throws CDKException
	 * @throws SQLException
	 */
	public HashMap<Integer, Double> Retrive1HHoseCode(IAtomContainer mole, String solvent) throws FileNotFoundException, CDKException, SQLException{
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		HoseCodeEvaluator HCE = new HoseCodeEvaluator();

		for(int i = 0; i < mole.getAtomCount(); i++) {
			IAtom atom = mole.getAtom(i);
			if(atom.getSymbol() == "H") {
				Double shift = HCE.RunHose1HPrediction(mole,atom,solvent);
				result.put(atom.getIndex(), shift);
			}
		}
		
		return result;
	}
	
	
	/**
	 * Separated function for generating instances 
	 * @param mole
	 * @param atom
	 * @return
	 */
	public Instances GenerateTestInstnace(IAtomContainer mole, IAtom atom, ArrayList<ArrayList<String>> nearestAtomList, 
			ArrayList<Attribute> attribute) {
		
		// ArrayList<ArrayList<String>> nearestAtomList = BuildDataSet.getNearestAtoms(mole,3);
		 
		int class_attribute_index = attribute.size() - 1;
		Instances test_instance = new Instances("Rel",attribute,1);
		test_instance.setClassIndex(class_attribute_index);
		
		
		try {
			ArrayList<Double> single_instance = BuildDataSet.GetSingleInstanceAsList(nearestAtomList,mole,atom);
			
			Instance sample = new DenseInstance(single_instance.size()+1); 
			for(int vidx = 0; vidx < single_instance.size(); vidx++){
				Attribute att = attribute.get(vidx);
				Double vle_string = single_instance.get(vidx);
				sample.setValue(att, vle_string);			
			}
			// it doesn't matter if set the class_attribute here; gonna change later
			sample.setValue(attribute.get(class_attribute_index), 0.0);
			test_instance.add(sample);
			
			return test_instance;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	
	
	/**
	 * Only need to do single instance prediction; no need to predict all nuclus
	 * TODO: Implement this function based on machine learning methodology
	 * @param mole
	 * @param atom
	 * @param solvent
	 * @return
	 */
	public Double NMRPrediction13C(IAtomContainer mole, IAtom atom, String solvent, ArrayList<ArrayList<String>> nearestAtomList, 
			ArrayList<Attribute> attribute) {
		// select model
		// prepare instance(s)
		// do prediction
		// return value
		String currend_dir = System.getProperty("user.dir");
		String model_path = String.format("%s/Model/NMRPredModel/%s13C.model", currend_dir,solvent);
		
		try {
			
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(model_path);
			Instances test_instance = GenerateTestInstnace(mole,atom,nearestAtomList,attribute);
			Double predicted_value = cls.classifyInstance(test_instance.get(0));
			return predicted_value;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
	/**
	 * TODO: Implement this function based on machine learning methodology
	 * @param mole
	 * @param atom
	 * @param solvent
	 * @return
	 */
	public Double NMRPrediction1H(IAtomContainer mole, IAtom atom, String solvent, ArrayList<ArrayList<String>> nearestAtomList, 
			ArrayList<Attribute> attribute) {
		
		String currend_dir = System.getProperty("user.dir");
		String model_path = String.format("%s/Model/NMRPredModel/%s1H.model", currend_dir,solvent);
		
		try {
			
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(model_path);
			Instances test_instance = GenerateTestInstnace(mole,atom,nearestAtomList,attribute);
			Double predicted_value = cls.classifyInstance(test_instance.get(0));
			return predicted_value;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
	}
	
	
	/**
	 * 1. retrive hose code from sqlitedb
	 * 2. if no matching shift value, do prediction
	 * 
	 * @param mol
	 * @param solvent
	 * @param atom_type
	 * @return HoseResult the hashmap with hose shift or predicted shift
	 * @throws SQLException 
	 * @throws CDKException 
	 * @throws FileNotFoundException 
	 */
	public HashMap<Integer, Double> GetPredictedShift(IAtomContainer mole, String solvent, String atom_type){
		
		
		HashMap<Integer, Double> HoseResult = new HashMap<Integer, Double>();
		ArrayList<Attribute> attribute = GenerateAttributeName(116);
		ArrayList<ArrayList<String>> nearestAtomList = BuildDataSet.getNearestAtoms(mole,3);
		DecimalFormat df = new DecimalFormat("####.##");
		try {
			
			if(atom_type == "C") {
				
				String pure_solvent = FormatSolventName(solvent);
				HoseResult = Retrive13CHoseCode(mole, pure_solvent);
			

				for(Integer key: HoseResult.keySet()) {
					if(HoseResult.get(key) == 0.0) {
						// if HoseResult.get(key) == 0.0 means can't find the particular Hose Code 
						// try to predict
						IAtom atom = mole.getAtom(key);
						Double shift = NMRPrediction13C(mole,atom,solvent,nearestAtomList,attribute); 
						if(shift != 0.0) {
							HoseResult.put(key,Double.valueOf(df.format(shift)));
						}
					}
				}
			}
			else if(atom_type == "H") {
				
				String pure_solvent = FormatSolventName(solvent);
				HoseResult = Retrive1HHoseCode(mole, pure_solvent);

				for(Integer key: HoseResult.keySet()) {
					if(HoseResult.get(key) == 0.0) {
						// if HoseResult.get(key) == 0.0 means can't find the particular Hose Code 
						// try to predict
						IAtom atom = mole.getAtom(key);
						Double shift = NMRPrediction1H(mole,atom,solvent,nearestAtomList,attribute);
						
						if(shift != 0.0) {
							HoseResult.put(key,Double.valueOf(df.format(shift)));
						}
					}
				}
			}
		
		} catch (FileNotFoundException | CDKException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
	
		
		return HoseResult;
		
		
	}
	
	
	/**
	 * make sure that solvent name is matching the exactly solvent name
	 * @param solvent
	 * @return
	 */
	public String FormatSolventName(String solvent) {
		
		String solvent_name = new String();
		
		if(solvent.equals("Acetone")) {
			solvent_name = "Acetone-D6 ((CD3)2CO)";
			
		}else if(solvent.equals("Chloroform")) {
			solvent_name = "Chloroform-D1 (CDCl3)";
			
		}else if(solvent.equals("Dimethylsulphoxide")) {
			solvent_name = "Dimethylether-D6 (CD3OCD3)";
			
		}else if(solvent.equals("Methanol")) {
			solvent_name = "Methanol-D3(CD3OH)";
			
		}else if(solvent.equals("Water")) {
			solvent_name = "Water";
			
		}
		return solvent_name;
		
		
		
	}
//	
//	/**
//	 * Main Function
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception{
//		
//		
//		BuildHoseCodeFromFolder bhcff = new BuildHoseCodeFromFolder();
//		bhcff.Generate1HHoseCode(new File("/Users/xuan/Desktop/Hose1H_sdf_nmr_non_tan_water_solvent"),"Water");
//		BuildDataSet dbs = new BuildDataSet();
//		dbs.BuildAsList("/Users/xuan/Desktop/nmrshiftdb2withsignals3DDone.sdf");
//		
//		
//		String file = "/Users/xuan/Desktop/nmrshiftdb2withsignals.sdf";
//		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
//		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(file),bldr);
//		NMRPred new_pred = new NMRPred();
//		String solvent = "water";
//		while (sdfr.hasNext()) {
//			IAtomContainer mole = sdfr.next();
//			HashMap<Integer, Double> HoseResult = new_pred.Retrive13CHoseCode(mole, solvent);
//			
//			for(Integer key: HoseResult.keySet()) {
//				if(HoseResult.get(key) == 0.0) {
//					// if HoseResult.get(key) == 0.0 means can't find the particular Hose Code 
//					// try to predict
//					IAtom atom = mole.getAtom(key);
//					Double shift = new_pred.NMRPrediction13C(mole,atom,solvent);
//					if(shift != 0.0) {
//						HoseResult.put(key,shift);
//					}
//				}
//			}
//			System.exit(0);
//		}
//		
//		BuildHoseCodeLib build = new BuildHoseCodeLib();
//		build.BuildAsList(file);
//		
//		HashMap<Integer,String> map = BuildHoseCodeLib.BuildSolventMap("0:Unreported");
//		if(map == null) {
//			System.out.println("unreported");
//		}else {
//			for(Integer key: map.keySet()) {
//				System.out.println(key + "=" + map.get(key));
//			}
//		}
//		
//	}
}
