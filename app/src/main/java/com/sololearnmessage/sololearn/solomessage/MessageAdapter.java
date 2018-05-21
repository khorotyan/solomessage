package com.sololearnmessage.sololearn.solomessage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final String TAG = "MessageAdapter";

    private ArrayList<Message> messages = new ArrayList<>();
    private Context context;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.userName.setText(messages.get(position).GetUserName());
        holder.userMessage.setText(messages.get(position).GetUserMessage());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on the user name: " + messages.get(position).GetUserName());

                Toast.makeText(context, messages.get(position).GetUserName(false), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userMessage;
        private ConstraintLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userMessage = itemView.findViewById(R.id.user_message);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
