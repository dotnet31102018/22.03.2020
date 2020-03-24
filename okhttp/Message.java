package hello.itay.com.demo6;

import com.google.gson.Gson;

/**
 * Created by Itay kan on 3/24/2020.
 */

public class Message {
    private static final Gson gson = new Gson();
    public int Id;
    public String Sender ;
    public String Body ;

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
