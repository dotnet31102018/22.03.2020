package hello.itay.com.webapilistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static MainActivity me;

    ArrayList<Message> _messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        me = this;

        final ArrayAdapter adapter = new MessageCustomAdapter(this, R.layout.message_list_item, _messages);

        String url = "http://10.0.2.2:9003/api/messages";
        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").build();

        // create client
        OkHttpClient client = new OkHttpClient();

        // enqueue - add item to the request queue
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("====", e.getMessage());
            }

            // SUCCESS!!!
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    //final Message mw = gson.fromJson(myResponse, Message.class);
                    final Message[] webapi_messages = gson.fromJson(myResponse, Message[].class);

                    Log.e("=====", myResponse);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mTextViewResult.setText(mw.toString());
                            for(Message msg : webapi_messages)
                            {
                                adapter.add(msg);
                            }
                        }
                    });
                }
                else
                {
                    Log.e("=====", response.toString());
                }
            }
        });

        ListView lv = findViewById(R.id.messagesLV);
        lv.setAdapter(adapter);
    }
}
