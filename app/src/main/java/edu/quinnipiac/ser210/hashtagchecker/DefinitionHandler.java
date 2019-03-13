package edu.quinnipiac.ser210.hashtagchecker;

import org.json.JSONException;
import org.json.JSONObject;

public class DefinitionHandler {
    public DefinitionHandler(){}
    public String getDefinition(String hashtagJSONString) throws JSONException {
        JSONObject hashtagJSON = new JSONObject(hashtagJSONString);
        return hashtagJSON.getJSONObject("defs").getJSONObject("def").getString("text");
    }
}
