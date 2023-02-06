import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args)  {
        try {
            createJsonFile();
        } catch (FileNotFoundException e){e.printStackTrace();}

    }

    public static void createJsonFile() throws FileNotFoundException {
        JSONObject jo = new JSONObject();

        // putting data to JSONObject
        jo.put("site", "https://efi.com");
        PrintWriter pw = new PrintWriter("site.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
    }
}
