package com.sololearnmessage.sololearn.solomessage;

import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener;
import com.smartarmenia.dotnetcoresignalrclientjava.HubEventListener;
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage;
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String username = "Vahagn";
    private String groupName = "Default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView
        //messages.add(new Message("Jacob", "Hello world, this is a simple message from me"));
        //messages.add(new Message("Tarry", "This is a slightly longer message than Jacob wrote, that is why this second part was added"));

        final MessageAdapter adapter = new MessageAdapter(this);
        final RecyclerView recyclerView = findViewById(R.id.messages_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // SignalR
        final HubConnection connection = new WebSocketHubConnectionP2("http://192.168.88.55:8000/hubs/chat", null);//"Bearer your_token");
        connection.addListener(new HubConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onMessage(HubMessage message) {

            }

            @Override
            public void onError(Exception exception) {

            }
        });

        connection.subscribeToEvent("SendMessage", new HubEventListener() {
            @Override
            public void onEventMessage(final HubMessage message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.AddMessage(new Message(message.getArguments()[0].getAsString(), message.getArguments()[1].getAsString()));
                        adapter.ScrollToPosition(recyclerView);
                    }
                });

            }
        });

        connection.connect();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connection.invoke("JoinRoom", groupName);
            }
        }, 100);

        // Buttons
        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newMessageEditText = findViewById(R.id.message_editText);
                String newMessage = newMessageEditText.getText().toString();
                newMessage = newMessage.trim();

                if (newMessage.length() != 0){

                    if (newMessage.charAt(0) == '#' && newMessage.charAt(1) == '#'){
                        if (newMessage.length() > 11 && newMessage.substring(2, 10).equals("USERNAME")){
                            username = newMessage.substring(11);
                            Toast.makeText(getApplication().getBaseContext(), "Username changed to " + username, Toast.LENGTH_SHORT).show();
                            newMessageEditText.setText("");
                            return;
                        }

                        if (newMessage.length() > 8 && newMessage.substring(2, 7).equals("GROUP")){
                            String newGroupName = newMessage.substring(8);

                            if (!newGroupName.equals(groupName)){
                                connection.invoke("LeaveRoom", groupName);
                                connection.invoke("JoinRoom", newGroupName);

                                groupName = newGroupName;
                                adapter.RemoveMessages();

                                Toast.makeText(getApplication().getBaseContext(), "Joined " + groupName + " group", Toast.LENGTH_SHORT).show();
                                newMessageEditText.setText("");
                            }
                            else{
                                Toast.makeText(getApplication().getBaseContext(), "Already in the group ", Toast.LENGTH_SHORT).show();
                                newMessageEditText.setText("##GROUP=");
                            }
                            return;
                        }
                    }

                    connection.invoke("Send", username, newMessage, groupName);
                    adapter.AddMessage(new Message(username, newMessage));
                    adapter.ScrollToPosition(recyclerView);
                    newMessageEditText.setText("");
                }
            }
        });

        // EditText autocomplete
        AutoCompleteTextView messageEditText = findViewById(R.id.message_editText);
        ArrayList<String> specialCommands = new ArrayList<>(Arrays.asList("##USERNAME=", "##GROUP="));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, specialCommands);
        messageEditText.setAdapter(arrayAdapter);
    }
}
