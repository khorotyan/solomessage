package com.sololearnmessage.sololearn.solomessage;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String username = "Vahagn";
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messages.add(new Message("Jacob", "Hello world, this is a simple message from me"));
        messages.add(new Message("Tarry", "This is a slightly longer message than Jacob wrote, that is why this second part was added"));

        final MessageAdapter adapter = new MessageAdapter(this, messages);
        RecyclerView recyclerView = findViewById(R.id.messages_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newMessageEditText = findViewById(R.id.message_editText);
                String newMessage = newMessageEditText.getText().toString();
                newMessage = newMessage.trim();
                Log.d("SomeTag","Message: " + newMessage);
                if (newMessage.length() != 0){
                    messages.add(new Message(username, newMessage));
                    newMessageEditText.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
