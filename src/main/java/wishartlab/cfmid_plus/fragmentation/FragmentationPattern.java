package wishartlab.cfmid_plus.fragmentation;

import java.util.LinkedHashMap;

import wishartlab.cfmid_plus.fragmentation.StructuralClass.ClassName;

public class FragmentationPattern {
	
	
	public ClassName structuralClass;
	public LinkedHashMap<String, String[]> patterns;
	
	public FragmentationPattern(ClassName structuralClass, LinkedHashMap<String, 
			String[]> patterns){
		this.structuralClass 	= structuralClass;
		this.patterns 			= patterns;
		
	}
	
	public ClassName getStructuralClass(){
		return this.structuralClass;
	}
	
	public LinkedHashMap<String, String[]> getFragmentList(){
		return this.patterns;
	}

}
