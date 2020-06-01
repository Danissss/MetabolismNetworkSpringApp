/**
 * This class implements the class of metabolic enzymes.
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */
package wishartlab.biotransformer.btransformers;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openscience.cdk.exception.CDKException;

import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;

/**
 * @author Yannick Djoumbou Feunang
 *
 */
public class Deglycolyzer extends Biotransformer {

	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws CDKException 
	 * 
	 */
	public Deglycolyzer(BioSystemName bioSName) throws IOException, ParseException, CDKException {
		super(bioSName);
		// TODO Auto-generated constructor stub
	}

}
