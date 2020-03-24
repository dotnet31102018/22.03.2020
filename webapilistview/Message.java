package hello.itay.com.webapilistview;

import com.google.gson.Gson;

/**
 * Created by Itay kan on 3/22/2020.
 */

public class Message {

    private static Gson gson = new Gson();
    public int Id;
    public String Sender ;
    public String Body ;

    @Override
    public String toString() {
        //return "ID: " + Id + " Sender: " + Sender + " Body: " + Body;
        return gson.toJson(this);
    }
}
