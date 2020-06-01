/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.biosystems;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class EnvMicro extends BioSystem {

	public EnvMicro(ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException{
		super(BioSystemName.ENVMICRO, mapper);
	}

}
