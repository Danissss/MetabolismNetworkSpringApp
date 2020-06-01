/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.transformation;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import wishartlab.biotransformer.biomolecule.Enzyme.EnzymeName;
import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;

public class Biotransformation {
	private AtomContainerSet substrates =  new AtomContainerSet();
	private ReactionName reactionType;
	private ArrayList<EnzymeName> enzymeNames = new ArrayList<EnzymeName>();
	private AtomContainerSet products =  new AtomContainerSet();
	private BioSystemName bsysName;
	private Double score;
	private String reactionID;
	
	public Biotransformation(IAtomContainerSet substrates, ReactionName reactionType, ArrayList<EnzymeName> enzymeNames, 
			IAtomContainerSet products, BioSystemName bsysName) {
		// TODO Auto-generated constructor stub
		this.substrates.add(substrates);
		this.reactionType 	= reactionType;
		
		if(enzymeNames !=null){
			this.enzymeNames = enzymeNames;
		}
		
		this.products.add(products);
		this.bsysName = bsysName;
		this.score = 1.0;
	}
	
	public Biotransformation(IAtomContainerSet substrates, ReactionName reactionType, ArrayList<EnzymeName> enzymeNames, 
			IAtomContainerSet products, Double score, BioSystemName bsysName) {
		// TODO Auto-generated constructor stub		
		this.substrates.add(substrates);
		this.reactionType 	= reactionType;
		if(enzymeNames !=null){
			this.enzymeNames = enzymeNames;
		}
		this.products.add(products);
		this.bsysName = bsysName;
		this.score = score;
	}

	public Biotransformation(IAtomContainerSet substrates, ReactionName reactionType, EnzymeName enzymeName, 
			IAtomContainerSet products) {
		// TODO Auto-generated constructor stub
		this.substrates.add(substrates);
		this.reactionType 	= reactionType;
		
		if(enzymeName !=null){
			this.enzymeNames.add(enzymeName);
		}
		this.products.add(products);
		this.score = 1.0;
	}	
	
	public AtomContainerSet getSubstrates(){
		return this.substrates;
	}
	
	public AtomContainerSet getProducts(){
		return this.products;
	}
	
	public ReactionName getReactionType(){
		return this.reactionType;
	}
	
	public ArrayList<EnzymeName> getEnzymeNames(){
		return this.enzymeNames;
	}
	
	public BioSystemName getBioSystemName(){
		return this.bsysName;
	}
	
	public Double getScore(){
		return this.score;
	}
	
	public void setEnzymeNames(ArrayList<EnzymeName> elsit){
		this.enzymeNames = elsit;
	}
	
	
	
	public void display(){
		System.out.println("Substrate(s):");
		for(IAtomContainer a : this.substrates.atomContainers()){
			if(a.getProperty("InChI")!=null){
				System.out.println("\t" + a.getProperty("InChI"));
			}
		}
		System.out.println("Product(s):");
		for(IAtomContainer p : this.products.atomContainers()){
			if(p.getProperty("InChI")!=null){
				System.out.println("\t" + p.getProperty("InChI"));
			}
		}
		System.out.println("Reaction: " + this.reactionType.toString());
		System.out.print("Enzyme:   ");
		if(!this.enzymeNames.isEmpty()){
			System.out.println(StringUtils.join(this.enzymeNames, "; "));
		}
		
		System.out.println("Score:    " + this.score);
	}
	
	public boolean equals(Biotransformation biotransformation){
		boolean same = false;
		if(this.getReactionType() == biotransformation.getReactionType() && this.getBioSystemName() == biotransformation.getBioSystemName()){
			if(this.products.getAtomContainerCount() == biotransformation.products.getAtomContainerCount()){
				ArrayList<String> enz_1 = new ArrayList<String>();
				ArrayList<String> enz_2 = new ArrayList<String>();
				for(int i=0; i<this.enzymeNames.size(); i++){
					enz_1.add(this.enzymeNames.get(i).toString());
				}
				for(int j=0; j<biotransformation.enzymeNames.size(); j++){
					enz_2.add(biotransformation.enzymeNames.get(j).toString());
				}
				Collections.sort(enz_1);
				Collections.sort(enz_2);
				if(StringUtils.join(enz_1).contentEquals(StringUtils.join(enz_2))){
					ArrayList<String> subs_1 = new ArrayList<String>();
					ArrayList<String> subs_2 = new ArrayList<String>();
					for(int k=0; k<this.substrates.getAtomContainerCount(); k++){
						subs_1.add((String) this.substrates.getAtomContainer(k).getProperty("InChIKey"));
					}
					for(int l=0; l<biotransformation.substrates.getAtomContainerCount(); l++){
						subs_2.add((String) biotransformation.substrates.getAtomContainer(l).getProperty("InChIKey"));
					}
					Collections.sort(subs_1);
					Collections.sort(subs_2);
					if(StringUtils.join(subs_1).contentEquals(StringUtils.join(subs_2))){
						ArrayList<String> prods_1 = new ArrayList<String>();
						ArrayList<String> prods_2 = new ArrayList<String>();
						for(int k=0; k<this.products.getAtomContainerCount(); k++){
							prods_1.add((String) this.products.getAtomContainer(k).getProperty("InChIKey"));
						}
						for(int l=0; l<biotransformation.products.getAtomContainerCount(); l++){
							prods_2.add((String) biotransformation.products.getAtomContainer(l).getProperty("InChIKey"));
						}
						Collections.sort(prods_1);
						Collections.sort(prods_2);
						if(StringUtils.join(prods_1).contentEquals(StringUtils.join(prods_2))){
							same = true;
						}
					}
				}
			}			
		}

		
		return same;
	}
}
