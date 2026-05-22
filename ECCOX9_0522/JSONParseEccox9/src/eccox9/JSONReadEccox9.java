package eccox9;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadEccox9 {

    public static void main(String[] args){

        String fileName = "orarendEccox9.json";

        try(FileReader reader = new FileReader(fileName)){

            //Parse
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);

            //Root, majd ora lista lekérése JSONArray
            JSONObject root = (JSONObject) jsonObject.get("Eccox9_orarend");
            JSONArray orarend = (JSONArray) root.get("ora");

            System.out.println("ECCOX9 Órarend 2026 tavasz\n");

            //Óraadatok
            for(int i = 0; i < orarend.size(); i++){
                JSONObject ora = (JSONObject) orarend.get(i);
                JSONObject time = (JSONObject) ora.get("idopont");
                System.out.println("Tárgy: "+ora.get("targy"));
                System.out.println("Időpont: "+time.get("nap") + " " + time.get("tol") + " " + "-" + " " + time.get("ig"));
                System.out.println("Helyszín: "+ora.get("helyszín"));
                System.out.println("Oktato: " + ora.get("oktato"));
                System.out.println("Szak: " +ora.get("szak") + "\n");

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }








}
