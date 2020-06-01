/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */


package wishartlab.biotransformer.transformation;

import wishartlab.biotransformer.biosystems.BioSystem;
import wishartlab.biotransformer.biomolecule.Enzyme;

import java.util.ArrayList;
import java.util.LinkedHashMap;



public class MetabolicPathway {
	
	private String  name;
	private ArrayList<Enzyme.EnzymeName> enzymeList;
	private LinkedHashMap<Enzyme.EnzymeName,Enzyme> enzymes;
	private BioSystem bSystem;
	
	
	public enum MPathwayName {
			BIOSYNTHESIS_OF_UNSATURATED_FATTY_ACIDS,BIOSYNTHESIS_OF_AMINO_ACIDS, 
			PRIMARY_BILE_ACID_BIOSYNTHESIS, SECONDARY_BILE_ACID_BIOSYNTHESIS,
			FATTY_ACID_DEGRADATION, FATTY_ACID_ELONGATION, ETHER_LIPID_METABOLISM,
			GLYCEROLIPID_METABOLISM, GLYCEROPHOSPHOLIPID_METABOLISM, SPHINGOLIPID_METABOLISM,			
			UNSATURATED_FATTY_ACID_BIOSYNTHESIS, INOSITOL_PHOSPHATE_METABOLISM,
			MICROBIAL_POLYPHENOL_METABOLISM, BILE_ACID_METABOLISM
	}

}
