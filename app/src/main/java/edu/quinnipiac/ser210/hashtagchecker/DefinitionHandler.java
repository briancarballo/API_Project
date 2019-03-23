package edu.quinnipiac.ser210.hashtagchecker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Definition Handler Class
 * Author: Brian Carballo
 * SER 210
 *
 * Class serves as the backend. It parses the JSON and grabs the relevant definition
 */

public class DefinitionHandler {

    public String getDefinition(String hashtagJSONString) throws JSONException {

        //Creates JSON object from string
        JSONObject hashtagJSON = new JSONObject(hashtagJSONString);

        //Returns definition of hashtag from API
        return hashtagJSON.getJSONObject("defs").getJSONObject("def").getString("text");
    }
}
