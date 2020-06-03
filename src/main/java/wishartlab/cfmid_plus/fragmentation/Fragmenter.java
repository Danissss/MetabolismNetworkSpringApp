/**
 * 
 */
package wishartlab.cfmid_plus.fragmentation;

/**
 * @author Yannick Djoumbou Feunang
 *
 */

import wishartlab.cfmid_plus.fragmentation.StructureExplorer;
import wishartlab.cfmid_plus.fragmentation.StructuralClass;
import wishartlab.cfmid_plus.fragmentation.StructuralClass.ClassName;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import ambit2.smarts.query.SMARTSException;




public class Fragmenter {

	
	IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
	private SMIRKSManager smrkMan = new SMIRKSManager(bldr);
	private SmilesParser sParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
	private SmilesGenerator sGen = SmilesGenerator.isomeric();

	StructureExplorer sExplorer = new StructureExplorer();

	public Fragmenter(){
		this.smrkMan.setFlagFilterEquivalentMappings(true);
	}
	
	public LinkedHashMap<String, IAtomContainer>  fragmentMolecule(IAtomContainer molecule, StructuralClass.ClassName type) throws Exception{
		return fragmentMolecule(molecule, type, "[M+H]+");
	}
	
	public boolean isConditionValidForChemClass(StructuralClass.ClassName type,
			String adductType){
		boolean valid = false;
		
		if(FPLists.classSpecificFragmentationPatterns.get(type).containsKey(adductType)){
			valid = true;
		}
		
		return valid;
	}
	
	
	/**
	 * Key function of fragmenter, that break down molecule into small molecule based on various rules
	 * @param molecule
	 * @param type
	 * @param adductType
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<String, IAtomContainer> fragmentMolecule(IAtomContainer molecule, StructuralClass.ClassName type,
			String adductType) throws Exception{	
		LinkedHashMap<String, IAtomContainer> fragments = new LinkedHashMap<String, IAtomContainer>();

		if(type != StructuralClass.ClassName.NIL && FPLists.classSpecificFragmentationPatterns.get(type).containsKey(adductType)){
			new SmilesGenerator();
			SmilesGenerator sg = SmilesGenerator.isomeric();
			
			for(Map.Entry<String, String[]> pattern : FPLists.classSpecificFragmentationPatterns.get(type).get(adductType)
					.patterns.entrySet()){
				SMIRKSReaction sReaction = this.smrkMan.parse(pattern.getValue()[1]);
				IAtomContainerSet current_set = this.smrkMan.applyTransformationWithSingleCopyForEachPos(
						molecule, null, sReaction);
				
				if(current_set != null){
					
					 IAtomContainerSet current_set_unique = StructureExplorer.uniquefy(current_set);
					 
					 for(IAtomContainer atc : current_set_unique.atomContainers()){
						 IAtomContainerSet partitions = StructureExplorer.partition(atc);

						 	for(IAtomContainer partition : partitions.atomContainers()){
						 		if(StructureExplorer.containsSmartsPattern(partition, pattern.getValue()[0])){
									if(pattern.getValue().length==2){
										fragments.put(pattern.getKey(), AtomContainerManipulator.removeHydrogens(partition));
										
									}else if (pattern.getValue().length==3){
										// take the SMILES of the fragment an add the adduct, whiich would be the 
										// third element in the array.
										IAtomContainer  adjustedPartition = this.sParser.parseSmiles(sg.create(AtomContainerManipulator.removeHydrogens(partition))
												+ "." + pattern.getValue()[2]);	
										fragments.put(pattern.getKey(), adjustedPartition);
									}

									break;	
								} else{
								}			
						 	}
						}
					} else{
						// TO DO
					}				
				}

				return fragments;
			} else
				System.err.println("WHAT IS GOING ON?");
				return null;
		
		}
	
	public LinkedHashMap<String, Double> computeFragmentMasses(LinkedHashMap<String, IAtomContainer> fragments){
		LinkedHashMap<String, Double> fmasses = new LinkedHashMap<String, Double>();
		
		if(fragments != null){
			for(Map.Entry<String, IAtomContainer> fragment :  fragments.entrySet()){
				fmasses.put(fragment.getKey(), 
						Math.floor(StructureExplorer.getMajorIsotopeMass(fragment.getValue()) * 100000)/100000);		
			}
		}else{
			fmasses = null;
		}
		
		return fmasses;		
	}
	public LinkedHashMap<String, Double> computeFragmentMassChargeRatios(LinkedHashMap<String, IAtomContainer> fragments) throws CDKException{
		LinkedHashMap<String, Double> fmassToChargeRatios = new LinkedHashMap<String, Double>();
		
		if(fragments != null){
			for(Map.Entry<String, IAtomContainer> fragment :  fragments.entrySet()){
				int z = (int) Math.abs(AtomContainerManipulator.getTotalFormalCharge(fragment.getValue()));
				if(z==0){
					fmassToChargeRatios.put(fragment.getKey(), 
							Math.floor(StructureExplorer.getMajorIsotopeMass(fragment.getValue()) * 100000)/100000);						
				} else{
					fmassToChargeRatios.put(fragment.getKey(), 
							Math.floor(StructureExplorer.getMajorIsotopeMass(fragment.getValue()) * 100000)/ (z * 100000));	
				}
	
			}
		}else{
			fmassToChargeRatios = null;
		}
		
		return fmassToChargeRatios;		
	}	
	
	public LinkedHashMap<String, ArrayList<String>>  annotatePeakList(LinkedHashMap<String, IAtomContainer> fragments ,ClassName type, FragmentationCondition fragCondition) throws CDKException{
		
		if(fragments == null || type == null || fragCondition == null){
			return null;
		} else{
			LinkedHashMap<String, ArrayList<String>> results = new LinkedHashMap<String, ArrayList<String>>();
			ArrayList<String> frag_smiles_mass = new ArrayList<String>();
			ArrayList<String> peaks = new ArrayList<String>();								
			LinkedHashMap<String, Double> fragmentMassChargeRatios = computeFragmentMassChargeRatios(fragments);
			LinkedHashMap<Double, ArrayList<String>> massesToLabel= new LinkedHashMap<Double, ArrayList<String>>();
			ArrayList<String> labels = new ArrayList<String>(fragmentMassChargeRatios.keySet());
			MSPeakRelativeAbundance mra = MSPRelativeAbundanceList.
					classSpecificRelativeAbundances.get(type).get(fragCondition.adductName + "_" + fragCondition.collisionEnergy);
			
			for(Map.Entry<String, Double> fm : fragmentMassChargeRatios.entrySet()){
				if(mra.getRelativeAbundances().containsKey(fm.getKey())){
					if(massesToLabel.containsKey(fm.getValue())){
						massesToLabel.get(fm.getValue()).add(fm.getKey());
					}else{
						massesToLabel.put(fm.getValue(), new ArrayList<String>());
						massesToLabel.get(fm.getValue()).add(fm.getKey());
					}
				}
			}			
			for(Double mass : massesToLabel.keySet()){
					if( massesToLabel.get(mass).size() == 1 && mra.getRelativeAbundances().containsKey(massesToLabel.get(mass).get(0))){
						peaks.add(String.format("%.5f %.1f %3d (1)",mass, mra.getRelativeAbundances().get(massesToLabel.get(mass).get(0)), labels.indexOf(massesToLabel.get(mass).get(0))));
					} else{
	
						ArrayList<String> indexes = new ArrayList<String>();
						ArrayList<String> scores = new ArrayList<String>();
						
						for(String x : massesToLabel.get(mass)){
							if(mra.getRelativeAbundances().containsKey(x)){
								indexes.add(String.valueOf(labels.indexOf(x)));
								scores.add("1");
							}
						}
						peaks.add(String.format("%.5f %.1f %8s",mass, mra.getRelativeAbundances().get(massesToLabel.get(mass).get(0)), 
						StringUtils.join(indexes," ")) + " (" + StringUtils.join(" ", scores) + ")");				
					}
			}
				/*
				 * Add Fragment masses and structures
				 */					
				for(Map.Entry<String, IAtomContainer> frag : fragments.entrySet()){
					frag_smiles_mass.add(String.format("%.5f", fragmentMassChargeRatios.get(frag.getKey())) + " " + this.sGen.create(fragments.get(frag.getKey())));
				}									

			results.put("peaks_list", peaks);
			results.put("fragments", frag_smiles_mass);
			return results;
		}
	}
	
	public LinkedHashMap<String, ArrayList<String>> generateCfmidLikeMSPeakList(IAtomContainer molecule, FragmentationCondition fragCondition) throws Exception{		
		if(fragCondition.getCollisionEnergy() == 10 || fragCondition.getCollisionEnergy() == 20 || fragCondition.getCollisionEnergy() == 40) {
			IAtomContainer standardized_mol = this.sExplorer.standardizeMolecule(molecule);
			StructuralClass.ClassName type = StructureExplorer.findClassName(standardized_mol);
			LinkedHashMap<String, IAtomContainer> fragments = fragmentMolecule(standardized_mol, type, fragCondition.getAdductName());
			
			return  annotatePeakList(fragments ,type, new FragmentationCondition(fragCondition.getAdductName(), 10));
		} else {
			
			throw new IllegalArgumentException("The collision energy must be either 10 eV, 20 eV, or 40 eV.\nPlease enter a valid collision energy");
		}
	}
	

	
	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> generateCfmidLikeMSPeakList(IAtomContainer molecule, String adductType) throws Exception{
		LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> annotatedPeaks = new LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>();
		
		IAtomContainer standardized_mol = this.sExplorer.standardizeMolecule(molecule);
		StructuralClass.ClassName type = StructureExplorer.findClassName(standardized_mol);
		LinkedHashMap<String, IAtomContainer> fragments = fragmentMolecule(standardized_mol, type, adductType);
		FragmentationCondition fragCondition_10 =  new FragmentationCondition(adductType, 10);
		FragmentationCondition fragCondition_20 =  new FragmentationCondition(adductType, 20);
		FragmentationCondition fragCondition_40 =  new FragmentationCondition(adductType, 40);
		
		annotatedPeaks.put(10, annotatePeakList(fragments ,type, fragCondition_10));
		annotatedPeaks.put(20,	annotatePeakList(fragments ,type, fragCondition_20));
		annotatedPeaks.put(40, annotatePeakList(fragments ,type, fragCondition_40));
		
		return annotatedPeaks;
	}

	
	/**
	 * this is main method to get predicted peaklist;
	 * instead of return int, should return LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>
	 * @param molecule
	 * @param bldr
	 * @param outputname
	 * @param adduct_types
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> saveSingleCfmidLikeMSPeakList(IAtomContainer molecule,  String outputname, ArrayList<String> adduct_types) throws Exception{	
		int status = 0;
		IAtomContainer standardized_mol = this.sExplorer.standardizeMolecule(molecule);
		StructuralClass.ClassName type = StructureExplorer.findClassName(standardized_mol);
		
		LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> result = new LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>();
        if(FPLists.classSpecificFragmentationPatterns.containsKey(type)){
        	
        	result = saveSingleCfmidLikeMSPeakList(standardized_mol, outputname, type, adduct_types);  
            
        } 
        else{
        	
        	if(type == ClassName.GLYCEROLIPIDS || type == ClassName.GLYCEROPHOSPHOLIPIDS || type == ClassName.SPHINGOLIPIDS 
        			|| type == ClassName.CERAMIDE_1_PHOSPHATES || type == ClassName.DIPHOSPHORYLATED_HEXAACYL_LIPID_A ||
        			type == ClassName.SULFATIDES || type == ClassName.FATTY_ACID_ESTERS_OF_HYDROXYL_FATTY_ACIDS ||
        			type == ClassName.ETHER_LIPIDS
        			){ 
        		
        		
        		status = 4;
    			System.out.println("STATUS REPORT = 4\nThe compound belongs to the lipid class of " + type + ", which is not covered "
    					+ "in the current version of the fragmenter.");       		
        	}
        	else{
        		status = 5;
    			System.out.println("STATUS REPORT = 5\nInvalid chemical class. The query compound does not belong to any of the classes covered "
    					+ "in the current version of the fragmenter.");         		
        		}

        }
        
        return result;
	}
	
	/**
	 * 
	 * @param molecule
	 * @param bldr
	 * @param outputname
	 * @param type
	 * @param adduct_types
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> saveSingleCfmidLikeMSPeakList(IAtomContainer molecule, String outputname, StructuralClass.ClassName type, ArrayList<String> adduct_types) throws Exception{
		
		int status = 0;
		LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> annotatedPeaks = new LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>();
		ArrayList<String> adduct_types_valid = new ArrayList<String>();
		ArrayList<String> adduct_types_invalid = new ArrayList<String>();
		
		for(String adduct : adduct_types){
			if(FPLists.classSpecificFragmentationPatterns.get(type).keySet().contains(adduct)){
				adduct_types_valid.add(adduct);
			}else{
				adduct_types_invalid.add(adduct);
			}
		}

		if(adduct_types_valid.size() < adduct_types.size()){
			if(adduct_types.size() == 1){	
				status = 3;
				System.out.println(
					"STATUS REPORT = 3\nThe following adducts are not covered for the class of " + type + ": " +
							Arrays.toString(adduct_types_invalid.toArray()) + "\n"
									+ "Next Step: Predicting MS spectra for covered adduct types: "
									+ Arrays.toString(FPLists.classSpecificFragmentationPatterns.get(type).keySet().toArray())
						);
				saveSingleCfmidLikeMSPeakList(molecule, bldr, type, outputname, false);
			}else{
				status = 2;
				System.out.println(
						"STATUS REPORT = 2\nThe following adducts are not covered for the class of " + type + ": " +
								Arrays.toString(adduct_types_invalid.toArray()) + "\n"
										+ "Next Step: Predicting MS spectra for remaining adduct types: "
										+ Arrays.toString(adduct_types_valid.toArray())
				);
				for(String adt : adduct_types_valid){
					System.out.println("Generating peak list for the adduct type: " + String.valueOf(adt));
					LinkedHashMap<String, IAtomContainer> fragments = fragmentMolecule(molecule, type, adt);
					FragmentationCondition fragCondition_10 =  new FragmentationCondition(adt, 10);
					FragmentationCondition fragCondition_20 =  new FragmentationCondition(adt, 20);
					FragmentationCondition fragCondition_40 =  new FragmentationCondition(adt, 40);
					
					annotatedPeaks.put(10, annotatePeakList(fragments ,type, fragCondition_10));
					annotatedPeaks.put(20,	annotatePeakList(fragments ,type, fragCondition_20));
					annotatedPeaks.put(40, annotatePeakList(fragments ,type, fragCondition_40));
					
				}				
			}
			
		} else{
				status = 1;
				System.out.println("STATUS REPORT = 1\nEach specified adduct is covered for the class of " + type + ". "
						+ "Next Step: Predicting MS spectra for the following adduct types: "
						+ Arrays.toString(adduct_types_valid.toArray())
				);
				for(String adduct : adduct_types){
					System.out.println("Generating peak list for the adduct type: " + String.valueOf(adduct));
					LinkedHashMap<String, IAtomContainer> fragments = fragmentMolecule(molecule, type, adduct);
					FragmentationCondition fragCondition_10 =  new FragmentationCondition(adduct, 10);
					FragmentationCondition fragCondition_20 =  new FragmentationCondition(adduct, 20);
					FragmentationCondition fragCondition_40 =  new FragmentationCondition(adduct, 40);
					
					annotatedPeaks.put(10, annotatePeakList(fragments, type, fragCondition_10));
					annotatedPeaks.put(20,	annotatePeakList(fragments, type, fragCondition_20));
					annotatedPeaks.put(40, annotatePeakList(fragments, type, fragCondition_40));
				}				
		}
		
		
		return annotatedPeaks;
	}
	
	
	/**
	 * 
	 * @param molecule
	 * @param bldr
	 * @param type
	 * @param outputname
	 * @param standardize
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> saveSingleCfmidLikeMSPeakList(IAtomContainer molecule, IChemObjectBuilder bldr, StructuralClass.ClassName type, String outputname, boolean standardize) throws Exception{
		
		
		int status = 0;
		LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> annotatedPeaks = new LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>();
		IAtomContainer standardized_mol = molecule;
		if(standardize){
			standardized_mol = this.sExplorer.standardizeMolecule(molecule);
		}

		if(FPLists.classSpecificFragmentationPatterns.containsKey(type)){
			System.out.println("\nSTATUS REPORT = 0\nPredicting spectra for all covered adduct types.");
			status = 0;
			for(String adduct : FPLists.classSpecificFragmentationPatterns.get(type).keySet()){
				
				System.out.println("Generating peak list for the adduct type: " + String.valueOf(adduct));
				
				LinkedHashMap<String, IAtomContainer> fragments = fragmentMolecule(standardized_mol, type, adduct);
				FragmentationCondition fragCondition_10 =  new FragmentationCondition(adduct, 10);
				FragmentationCondition fragCondition_20 =  new FragmentationCondition(adduct, 20);
				FragmentationCondition fragCondition_40 =  new FragmentationCondition(adduct, 40);
				
				annotatedPeaks.put(10, annotatePeakList(fragments ,type, fragCondition_10));
				annotatedPeaks.put(20,	annotatePeakList(fragments ,type, fragCondition_20));
				annotatedPeaks.put(40, annotatePeakList(fragments ,type, fragCondition_40));
				
				
				
			}			
		}
		else{
        	if(type == ClassName.GLYCEROLIPIDS || type == ClassName.GLYCEROPHOSPHOLIPIDS || type == ClassName.SPHINGOLIPIDS 
       			|| type == ClassName.CERAMIDE_1_PHOSPHATES || type == ClassName.DIPHOSPHORYLATED_HEXAACYL_LIPID_A ||
        			type == ClassName.SULFATIDES || type == ClassName.FATTY_ACID_ESTERS_OF_HYDROXYL_FATTY_ACIDS ||
        			type == ClassName.ETHER_LIPIDS
        			){      
        		status = 4;
    			System.out.println("STATUS REPORT = 4\nThe compound belongs to a lipid class of " + type + ", which is not covered "
    					+ "in the current version of the fragmenter.");       		
        	}
        	else{
        		status = 5;
    			System.out.println("STATUS REPORT = 5\nInvalid chemical class. The query compound does not belong to any of the classes covered "
    					+ "in the current version of the fragmenter.");         		
        	}
        }
		
		
		return annotatedPeaks;
		

	}
	
	
	/**
	 * 
	 * @param annotatedPeaks
	 * @param adductType
	 * @param outputname
	 * @throws IOException
	 */
	public void saveSingleCfmidLikeMSAnnotatedPeakList(LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>>annotatedPeaks, String adductType, String outputname) throws IOException{
		if(annotatedPeaks != null) {
			
			FileWriter fw = new FileWriter(outputname);
			BufferedWriter bw = new BufferedWriter(fw);
			int level = 0;
			int fragIndex = 0;
			
			for(Map.Entry<Integer, LinkedHashMap<String, ArrayList<String>>> peaks : annotatedPeaks.entrySet()){
				bw.write("energy" + level);
				bw.newLine();
				level++;
				for(String s : peaks.getValue().get("peaks_list")){
					bw.write(s);
					bw.newLine();
				}
			}
			
			bw.newLine();
			for( String fg : annotatedPeaks.get(10).get("fragments")){
				bw.write( fragIndex + " " + fg);
				bw.newLine();
				fragIndex++;
			}

			bw.newLine();
			bw.write(adductType);
			bw.newLine();
			
			bw.close();	
			fw.close();
		}		
	}
	
	
	/**
	 * 
	 * @param molecule
	 * @param bldr
	 * @param adductType
	 * @param outputname
	 * @throws Exception
	 */
	public void saveSingleCfmidLikeMSPeakList(IAtomContainer molecule, IChemObjectBuilder bldr, String adductType, String outputname) throws Exception{

			FileWriter fwerr = new FileWriter("data/missing.log");

			LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> peaksResults = generateCfmidLikeMSPeakList(molecule, adductType);

	
			if(peaksResults != null) {
				
				FileWriter fw = new FileWriter(outputname);
				BufferedWriter bw = new BufferedWriter(fw);
				int level = 0;
				int fragIndex = 0;
				
				for(Map.Entry<Integer, LinkedHashMap<String, ArrayList<String>>> peaks : peaksResults.entrySet()){

					bw.write("energy" + level);
					bw.newLine();
					level++;
					for(String s : peaks.getValue().get("peaks_list")){
						bw.write(s);
						bw.newLine();
					}
				}
				
				bw.newLine();
				for( String fg : peaksResults.get(10).get("fragments")){
					bw.write( fragIndex + " " + fg);
					bw.newLine();
					fragIndex++;
				}

				bw.newLine();
				bw.write(adductType);
				bw.newLine();
				
				bw.close();	
				fw.close();
			}

		fwerr.close();
	}
	
	
	/**
	 * 
	 * @param molecule
	 * @param adductType
	 * @return
	 * @throws Exception 
	 */
	public int validateTheInputForCFMID(IAtomContainer molecule, String adduct) throws Exception {
		int status = 0;
		IAtomContainer standardized_mol = this.sExplorer.standardizeMolecule(molecule);
		StructuralClass.ClassName type = StructureExplorer.findClassName(standardized_mol);
		if(FPLists.classSpecificFragmentationPatterns.containsKey(type)) {
			if(FPLists.classSpecificFragmentationPatterns.get(type).keySet().contains(adduct)){
				status = 1;
				// success
			}else {
				status = 2;
				// adduct type is not covered.
			}
			
		}else if(type == ClassName.GLYCEROLIPIDS || type == ClassName.GLYCEROPHOSPHOLIPIDS || type == ClassName.SPHINGOLIPIDS 
       			|| type == ClassName.CERAMIDE_1_PHOSPHATES || type == ClassName.DIPHOSPHORYLATED_HEXAACYL_LIPID_A ||
    			type == ClassName.SULFATIDES || type == ClassName.FATTY_ACID_ESTERS_OF_HYDROXYL_FATTY_ACIDS ||
    			type == ClassName.ETHER_LIPIDS
    			){      
    		status = 4;
			System.out.println("STATUS REPORT = 4\nThe compound belongs to a lipid class of " + type + ", which is not covered "
					+ "in the current version of the fragmenter.");       		
    	}
    	else{
    		status = 5;
			System.out.println("STATUS REPORT = 5\nInvalid chemical class. The query compound does not belong to any of the classes covered "
					+ "in the current version of the fragmenter.");         		
    	}
		
		return status;
	}
}





