package eccox9;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWriteEccox9 {

    public static void main(String[] args){

    String fileName = "orarendEccox9.json";
    try(FileReader reader = new FileReader(fileName)){

        //Parse
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);

        //Root majd oralista lekérése JSONArray






    }catch(Exception e){
        e.printStackTrace();
    }






    }
}
