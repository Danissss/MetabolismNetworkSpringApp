package wishartlab.cfmid_plus.fragmentation;

public class FragmentationCondition {
	
	public String adductName;
	public int collisionEnergy;
	
	public FragmentationCondition(String adductName, int collisionEnergy) {
		this.adductName = adductName;
		this.collisionEnergy = collisionEnergy;	
	}
	
	public String getAdductName(){
		return this.adductName;
	}
	
	public int getCollisionEnergy(){
		return this.collisionEnergy;
	}
	
	public void print(){
		System.out.println("Adduct Type: " + this.adductName);
		System.out.println("Collision Energy: " + this.collisionEnergy);
	}
	
}
