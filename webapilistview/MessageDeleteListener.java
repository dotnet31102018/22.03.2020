package hello.itay.com.webapilistview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Itay kan on 3/22/2020.
 */

public class MessageDeleteListener implements View.OnClickListener {

    private MessageCustomAdapter _adapter;
    private Context _context;
    private int _position;
    private ArrayList<Message> _messages;

    MessageDeleteListener(MessageCustomAdapter adapter, int position, Context context, ArrayList<Message> flights)
    {
        _adapter = adapter;
        _position = position;
        _context = context;
        _messages = flights;
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder b = new AlertDialog.Builder(_context);

        b.setTitle("Delete '" + _messages.get(_position) + "'");

        b.setMessage("Are you sure you want to delete?");

        b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();


                String url = "http://10.0.2.2:9003/api/messages/" +  _messages.get(_position).Id;

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();



                Request request = new Request.Builder()
                        .url(url)
                        .delete()
                        .addHeader("Authorization", "header value") //Notice this request has header if you don't need to send a header just erase this part
                        .build();
                // enqueue - add item to the request queue
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    // SUCCESS!!!
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            //final String myResponse = response.body().string();
                            MainActivity.me.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _adapter.remove(_messages.get(_position));
                                    Toast.makeText(MainActivity.me, "Deleted on server", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else
                        {
                            Log.e("=====", response.body().string());
                        }
                    }
                });


            }
        });
        b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(_context, "Ok, forget about it", Toast.LENGTH_LONG).show();

            }
        });

        //_adapter.remove(_adapter.getItem(_position));

        AlertDialog alert = b.create();
        alert.show();
    }
}
