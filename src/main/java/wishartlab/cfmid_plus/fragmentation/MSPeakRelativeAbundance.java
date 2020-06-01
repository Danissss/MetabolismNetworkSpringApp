package wishartlab.cfmid_plus.fragmentation;

import java.util.LinkedHashMap;

import wishartlab.cfmid_plus.fragmentation.StructuralClass.ClassName;

public class MSPeakRelativeAbundance {

	public ClassName structuralClass;
	public FragmentationCondition fragCondition;
	public LinkedHashMap<String, Double> relativeAbundances;
	
	public MSPeakRelativeAbundance(ClassName structuralClass, FragmentationCondition fragCondition,
			LinkedHashMap<String, Double> relativeAbundances) {
		
		this.structuralClass 	= structuralClass;
		this.fragCondition 		= fragCondition;
		this.relativeAbundances	= relativeAbundances;
	}
	
	public ClassName getClassName(){
		return this.structuralClass;
	}
	
	public FragmentationCondition getFragCondition(){
		return this.fragCondition;
	}
	
	public LinkedHashMap<String, Double> getRelativeAbundances(){
		return this.relativeAbundances;
	}
	
	public void print(){
		System.out.println("Class Name: " + this.getClassName());
		this.fragCondition.print();
		System.out.println("Relative abundances " + this.getRelativeAbundances());
	}
}
