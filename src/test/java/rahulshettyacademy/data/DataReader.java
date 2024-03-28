package rahulshettyacademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJSONDataToMap() throws IOException 
	{
		// Step 1 : Read JSON to string using readFileToString Method which takes File object as parameter
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		
		// Step 2: Convert String to HashMap with the help of jackson databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		
		// Step 3 - return data i,e This data will take JSON and convert to List of type Hashmap with String type look like {map},{map1}
		return data;
	}

}
