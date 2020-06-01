package wishartlab.biotransformer.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;

import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.Cyp450BTransformer;
import wishartlab.biotransformer.btransformers.ECBasedBTransformer;
import wishartlab.biotransformer.btransformers.EnvMicroBTransformer;
import wishartlab.biotransformer.btransformers.HGutBTransformer;
import wishartlab.biotransformer.btransformers.Phase2BTransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import predicition.P2Filter;

public class UniversalBioTransformer {
	protected ECBasedBTransformer ecb		 		= new ECBasedBTransformer(BioSystemName.HUMAN);
	protected Cyp450BTransformer cyb 				= new Cyp450BTransformer(BioSystemName.HUMAN);
	protected HGutBTransformer hgb 					= new HGutBTransformer();
	protected Phase2BTransformer p2b 				= new Phase2BTransformer(BioSystemName.HUMAN);
	protected EnvMicroBTransformer emb 				= new EnvMicroBTransformer();
	protected LinkedHashMap<String, LinkedHashMap<String, String>> compoundDictionary		
													= new LinkedHashMap<String, LinkedHashMap<String, String>>();
	
	protected P2Filter p2filter 					= new P2Filter();
	protected LinkedHashMap<String, MetabolicReaction> combinedReactionsHash							
													= new LinkedHashMap<String, MetabolicReaction>();
	
	public SmilesParser smiParser					= ecb.getSmiParser();
	public SmilesGenerator smiGen 		= new SmilesGenerator().isomeric();
	
	
	public UniversalBioTransformer() throws IOException, ParseException, CDKException {

		for(Map.Entry<String, MetabolicReaction> m : this.ecb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(m.getKey())){
				this.combinedReactionsHash.put(m.getKey(), m.getValue());
			}
		}
		
		for(Map.Entry<String, MetabolicReaction> n : this.cyb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(n.getKey())){
				this.combinedReactionsHash.put(n.getKey(), n.getValue());
			}
		}

		for(Map.Entry<String, MetabolicReaction> p : this.hgb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(p.getKey())){
				this.combinedReactionsHash.put(p.getKey(), p.getValue());
			}
		}
		
		for(Map.Entry<String, MetabolicReaction> o : this.p2b.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(o.getKey())){
				this.combinedReactionsHash.put(o.getKey(), o.getValue());
			}
		}
		
		for(Map.Entry<String, MetabolicReaction> o : this.emb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(o.getKey())){
				this.combinedReactionsHash.put(o.getKey(), o.getValue());
			}
		}
	}


	public IAtomContainer standardizeMoleculeWithCopy(IAtomContainer target) throws Exception{
		return  ChemStructureManipulator.standardizeMoleculeWithCopy(target);
	}
	
	public IAtomContainerSet extractProductsFromBiotransformations(ArrayList<Biotransformation> biotransformations) throws Exception{
		return this.ecb.extractProductsFromBiotransformations(biotransformations);
	}
	
	public IAtomContainerSet extractProductsFromBiotransformationsWithTransformationData(ArrayList<Biotransformation> biotransformations, boolean annotate) throws Exception{
		return this.ecb.extractProductsFromBiotransformationsWithTransformationData(biotransformations, this.combinedReactionsHash, annotate);
	}

	public void saveBioTransformationProductsToSdf(ArrayList<Biotransformation> biotransformations, String outputFileName, boolean annotate) throws Exception {
		try{
			this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	
	public void saveBioTransformationProductsToCSV(ArrayList<Biotransformation> biotransformations, String outputFileName, boolean annotate) throws Exception {
		try{
			this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
}
