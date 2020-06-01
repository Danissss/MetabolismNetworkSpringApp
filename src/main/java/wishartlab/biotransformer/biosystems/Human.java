/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.biosystems;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class Human extends BioSystem {

	public Human(ObjectMapper mapper) throws IOException {
		super(BioSystemName.HUMAN, mapper);
	}

}
