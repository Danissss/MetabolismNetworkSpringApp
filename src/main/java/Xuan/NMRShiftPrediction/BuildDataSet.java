package Xuan.NMRShiftPrediction;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3d;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.geometry.GeometryUtil;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.qsar.DescriptorEngine;
import org.openscience.cdk.qsar.IAtomicDescriptor;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.IPAtomicLearningDescriptor;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.qsar.result.IntegerArrayResult;
import org.openscience.cdk.qsar.result.IntegerResult;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class BuildDataSet {
	static List<String> AtomicClassNames = DescriptorEngine.getDescriptorClassNameByPackage("org.openscience.cdk.qsar.descriptors.atomic",
            null);
	static DescriptorEngine ENGINE = new DescriptorEngine(AtomicClassNames, null);
	
	/**
	 * 
	 * @param SdfFile
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<String[]> BuildAsList(String SdfFile) throws IOException{
		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(SdfFile),bldr);
		
		while (sdfr.hasNext()) {
			
			IAtomContainer mole = sdfr.next();
			if (GeometryUtil.has3DCoordinates(mole)== false) {
				continue;
			}else {
			
				Map<Object,Object> object = mole.getProperties();
				String solvent = (String) object.get("Solvent");
				ArrayList<ArrayList<Double>> ChloroformData = new ArrayList<ArrayList<Double>>();

				HashMap<Integer,String> solventmap =  BuildHoseCodeLib.BuildSolventMap(solvent);
				for (Object key : object.keySet()) {
					String keyValue = (String) key;
					
					// determine if the current property is spectrum and contain 13C
					if(keyValue.contains("Spectrum") && keyValue.contains("13C")) {
						// System.out.println(keyValue);
						//
						String SpectrumValue = (String) object.get(key); // contain spectrum value
						String[] solventKey = keyValue.split(" ");
						String solventIndex = solventKey[solventKey.length-1];
						
						// get solvent index
						int sIndex = Integer.valueOf(solventIndex);
						String actualSolvent = solventmap.get(sIndex);
						System.out.println(actualSolvent);
						// if the solvent is selected
						if(actualSolvent.equals("Chloroform-D1 (CDCl3)")) {
							
							String[] spectrum_list = SpectrumValue.split("\\|");
							// System.out.println(Arrays.toString(spectrum_list));
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							// iterate each spectrum
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}
						}else if(actualSolvent.equals("Methanol-D4 (CD3OD)")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}	
						}else if(actualSolvent.equals("Dimethylsulphoxide-D6 (DMSO-D6, C2D6SO))")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}
							
						}else if(actualSolvent.equals("Acetone-D6 ((CD3)2CO)")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}	
						}
						
					}else if(keyValue.contains("Spectrum") && keyValue.contains("1H")) {
						
						String SpectrumValue = (String) object.get(key); // contain spectrum value
						String[] solventKey = keyValue.split(" ");
						String solventIndex = solventKey[solventKey.length-1];
						int sIndex = Integer.valueOf(solventIndex);
						String actualSolvent = solventmap.get(sIndex);
						System.out.println(actualSolvent);
						// if the solvent is selected
						if(actualSolvent.equals("Chloroform-D1 (CDCl3)")) {
							
							String[] spectrum_list = SpectrumValue.split("\\|");
							// System.out.println(Arrays.toString(spectrum_list));
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							// iterate each spectrum
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}
						}else if(actualSolvent.equals("Methanol-D4 (CD3OD)")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}	
						}else if(actualSolvent.equals("Dimethylsulphoxide-D6 (DMSO-D6, C2D6SO))")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}
							
						}else if(actualSolvent.equals("Acetone-D6 ((CD3)2CO)")) {
							String[] spectrum_list = SpectrumValue.split("\\|");
							ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
							for(int i = 0; i < spectrum_list.length; i++) {
								ArrayList<Double> single_instance = GetSingleInstanceAsList(spectrum_list[i],nearestAtomList,mole);
							}	
						}
					}
			    }
				
				
			}
			// end of each iteration
		}
		
		return null;
	}
	
	
	
	/**
	 * copy the nmr_pred function to generate data for single molecule
	 * just a simple function to generate the test data
	 * build testing data for all the instance from molecule
	 * @param SdfFile
	 * @param ModelType either 13C or 1H
	 * @return
	 * @throws IOException 
	 */
	public Instances BuildAsListTesting(String SdfFile, String ModelType) throws IOException{
		
		
		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(SdfFile),bldr);
		if(sdfr.hasNext()) {
			IAtomContainer mole = sdfr.next();
			if (GeometryUtil.has3DCoordinates(mole)== false) {
				return null;
			}else {
				// if model type == C
				ArrayList<ArrayList<String>> nearestAtomList = getNearestAtoms(mole,3);
				ArrayList<Attribute> attribute_al = GetAttribute(29*4);
				ArrayList<ArrayList<Double>> all_instance = new ArrayList<ArrayList<Double>>();
				for(int i = 0; i< mole.getAtomCount();i++) {
					if(mole.getAtom(i).getSymbol().equals("C")) {
						all_instance.add(GetTestInstanceAsList(nearestAtomList,mole,i));
					}
				}
				
				Attribute class_attribute = new Attribute("Shift");
				Instances test_instance = new Instances("Rel",attribute_al,1);
				test_instance.setClassIndex(class_attribute.index());
				
				
				int length = attribute_al.size();
				
				
				// add the calculated value and add to instances object
				for(int vidx = 0; vidx < all_instance.size(); vidx++){
					Instance sample = new DenseInstance(length); 
					for(int vk = 0; vk < all_instance.get(vidx).size(); vk++) {
						Attribute att = attribute_al.get(vk);

						sample.setValue(att, all_instance.get(vidx).get(vk));		
					}
					sample.setValue(class_attribute, "?");
					test_instance.add(sample);
					
				  }
				return test_instance;
			}
			
		}
		
		return null;

	}
	
	
	/**
	 * 
	 * @param numAttr
	 * @return
	 */
	public ArrayList<Attribute> GetAttribute(Integer numAttr){
		ArrayList<Attribute> attr = new ArrayList<Attribute>();
		for(int i = 0; i< numAttr; i++) {
			Attribute tmp = new Attribute(String.format("Attribute_%d", i));
			attr.add(tmp);
		}
		
		
		return attr;
	}
	
	/**
	 * 
	 * 
	 * @param nearestAtomList
	 * @param mole
	 * @param atom_index
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Double> GetTestInstanceAsList(ArrayList<ArrayList<String>> nearestAtomList, IAtomContainer mole, Integer atom_index) throws IOException{
		

		ArrayList<String> nearestAtom = nearestAtomList.get(atom_index);
		List<IAtom> atomList = new ArrayList<IAtom>();
		atomList.add(mole.getAtom(atom_index));
		for(String atomm : nearestAtom) {
			int atommindex = Integer.valueOf(atomm);
			atomList.add(mole.getAtom(atommindex));
		}
		// extract the feature here 
		// get nearest atom of current atom
		// extract the feature based on the current atom and other nearby atoms
		// return single descriptor value 
		ArrayList<Double[]> descriptorValue = getAtomicDescriptor(mole,atomList);
		ArrayList<Double> finalList = new ArrayList<Double>();
		for(int dm = 0; dm < descriptorValue.get(0).length; dm++) {
			for (int dz = 0; dz < descriptorValue.size(); dz++) {
				finalList.add(descriptorValue.get(dz)[dm]);
			}
		}
		return finalList;
		
	}
	
	
	/**
	 * 
	 * @param spectrum
	 * @param nearestAtomList
	 * @param mole
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Double> GetSingleInstanceAsList(String spectrum, ArrayList<ArrayList<String>> nearestAtomList, IAtomContainer mole) throws IOException{
		
		String[] single_spectrum = spectrum.split(";");
		String shift = single_spectrum[0];
		// System.out.println(Arrays.toString(single_spectrum));
		int atom_index = Integer.valueOf(single_spectrum[2]);
		ArrayList<String> nearestAtom = nearestAtomList.get(atom_index);
		List<IAtom> atomList = new ArrayList<IAtom>();
		atomList.add(mole.getAtom(atom_index));
		for(String atomm : nearestAtom) {
			int atommindex = Integer.valueOf(atomm);
			atomList.add(mole.getAtom(atommindex));
		}
		
		// extract the feature here 
		// get nearest atom of current atom
		// extract the feature based on the current atom and other nearby atoms
		// return single descriptor value 
		ArrayList<Double[]> descriptorValue = getAtomicDescriptor(mole,atomList);
		ArrayList<Double> finalList = new ArrayList<Double>();
		for(int dm = 0; dm < descriptorValue.get(0).length; dm++) {
			for (int dz = 0; dz < descriptorValue.size(); dz++) {
				finalList.add(descriptorValue.get(dz)[dm]);
			}
		}
		finalList.add(Double.valueOf(shift));
		// add shift value 
		return finalList;
		
	}
	
	/**
	 * 
	 * Get single instance based on atom
	 * @param nearestAtomList
	 * @param mole
	 * @param atom
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<Double> GetSingleInstanceAsList(ArrayList<ArrayList<String>> nearestAtomList, IAtomContainer mole, IAtom atom) throws IOException{
		
		
		int atom_index = atom.getIndex();
		ArrayList<String> nearestAtom = nearestAtomList.get(atom_index);
		List<IAtom> atomList = new ArrayList<IAtom>();
		atomList.add(mole.getAtom(atom_index));
		for(String atomm : nearestAtom) {
			int atommindex = Integer.valueOf(atomm);
			atomList.add(mole.getAtom(atommindex));
		}
		

		ArrayList<Double[]> descriptorValue = getAtomicDescriptor(mole,atomList);
		ArrayList<Double> finalList = new ArrayList<Double>();
		for(int dm = 0; dm < descriptorValue.get(0).length; dm++) {
			for (int dz = 0; dz < descriptorValue.size(); dz++) {
				finalList.add(descriptorValue.get(dz)[dm]);
			}
		}
		finalList.add(Double.valueOf(0.0));
		return finalList;
		
	}
	
	/**
	 * 
	 * @param mole
	 * @param descNamesStr
	 * @return
	 * @throws java.io.IOException
	 */
	
	public static ArrayList<Double[]> getAtomicDescriptor(IAtomContainer mole, List<IAtom> nearestAtom) throws java.io.IOException {
		List<IDescriptor> descriptors = ENGINE.getDescriptorInstances();
//		ArrayList<String> colNames = new ArrayList<String>();
		ArrayList<Double[]> values = new ArrayList<Double[]>();
		
		
		// get each descriptors
		for (IDescriptor desc : descriptors) {
			if (desc instanceof IPAtomicLearningDescriptor)
				continue;
//			String tname = desc.getClass().getName();
//			String[] tnamebits = tname.split("\\.");
//			tname = tnamebits[tnamebits.length - 1];
//			if ((descNamesStr.length() > 0) && (!descNames.contains(tname)))
//				continue;
			
//			String[] colNamesArr = desc.getDescriptorNames();
//			for (int idx = 0; idx < colNamesArr.length; idx++) {
//				colNamesArr[idx] = tname + "-" + colNamesArr[idx];
//			}
//			colNames.addAll(Arrays.asList(colNamesArr));

			// call the computeListsAtomic to get the desired atomic value 
			values.addAll(computeDescriptorsAtomic(mole, nearestAtom, (IAtomicDescriptor) desc));
		}

		return values;
	}
	
	
	
	/**
	 * nearestAtomFactor define the number of nearest atom of given atom
	 * @param IAtomContainer mole
	 * @param int nearestAtomFactor 
	 * @return
	 */
	public static ArrayList<ArrayList<String>> getNearestAtoms(IAtomContainer mole, int nearestAtomFactor) {
		
		ArrayList<ArrayList<String>> atom_distances = new ArrayList<ArrayList<String>>();

		int atomCount = mole.getAtomCount();

		List<IAtom> atoms = new ArrayList<IAtom>();
		for (int i = 0; i < atomCount; i++) {
			atoms.add(mole.getAtom(i));
		}

		for (int i = 0; i < atoms.size(); i++) {
			Double[] distances = new Double[atoms.size()];
			
			for (int j = 0; j < atoms.size(); j++) {
				if (j == i) {
					// Large number so that sorting puts it last
					distances[j] = 99999.12;
					continue;
				}
			
				Point3d firstPoint = atoms.get(i).getPoint3d();
				Point3d secondPoint = atoms.get(j).getPoint3d();
				Double distance = firstPoint.distance(secondPoint);
				distances[j] = distance;

			}
			
			
			// put the nearest atom at front
			ArrayList<String> indices = new ArrayList<String>();
			Double[] d = distances.clone(); // clone the original list (unsorted)
			Arrays.sort(d); // sort;
			List<Double> d_list = Arrays.asList(distances); // put into

			for (int j = 0; j < nearestAtomFactor; j++) {
				String index = String.valueOf(d_list.indexOf(d[j])); // get index of that changed atoms
				indices.add(index);
			}
			
//			it return the nearest atom, not the actual distance
			atom_distances.add(indices);
			
		
		}
		return atom_distances;
	}
	
	
	/**
	 * for computeListAtomic 
	 * Most important function (thank god, finally)
	 * the value for descriptor comes from this function call
	 * input: atomContainer mol; list of atoms; descriptors
	 * calculate each descriptor for each atoms (23 atoms)
	 * List<Double[]> is always zero
	 * 
	 */
	public static List<Double[]> computeDescriptorsAtomic(IAtomContainer mol, List<IAtom> atoms,
			IAtomicDescriptor descriptor) {
		List<Double[]> vv = new ArrayList<Double[]>();

//		// total 23 atoms (same as sdf file shows)

		vv.add(new Double[atoms.size()]);
		
		
		// iterate each atom
		for (int i = 0; i < atoms.size(); i++) {
			if (atoms.get(i) == null) {
				vv.get(0)[i] = null;
			} else {
				try {
					IDescriptorResult res = descriptor.calculate(atoms.get(i), mol).getValue();
					//System.out.println(res.toString());// res contain all the value for each atom
					if (res instanceof IntegerResult) {
						vv.get(0)[i] = (double) ((IntegerResult) res).intValue();
//						 System.out.println("IntegerResult"+vv.get(0)[i]);
					} else if (res instanceof DoubleResult) {
						vv.get(0)[i] = ((DoubleResult) res).doubleValue();
//						 System.out.println("DoubleResult"+vv.get(0)[i]);
					} else if (res instanceof DoubleArrayResult) {
						vv.get(0)[i] = ((DoubleArrayResult) res).get(0);
//						 System.out.println("DoubleArrayResult"+vv.get(0)[i]);
					} else if (res instanceof IntegerArrayResult) {
						vv.get(0)[i] = (double) ((IntegerArrayResult) res).get(0);
//						 System.out.println("IntegerArrayResult"+vv.get(0)[i]);
					} else if (res instanceof BooleanResult) {
						String result_bool = res.toString();
						if (result_bool == "false") {
							vv.get(0)[i] = 0.0;
						} else {
							vv.get(0)[i] = 1.0;
						}
					}else{
						throw new IllegalStateException(
								"Unknown idescriptor result value for '" + descriptor + "' : " + res.getClass());
					}
				}catch (Throwable e) {
					System.err.println("Could not compute cdk feature " + descriptor);
					e.printStackTrace();
					vv.get(0)[i] = 0.0;
				}
			}
			
			if (vv.get(0)[i] != null && (vv.get(0)[i].isNaN() || vv.get(0)[i].isInfinite()))
				vv.get(0)[i] = 0.0;
		}
		
		
		return vv;
	}
	
}
