package com.sololearnmessage.sololearn.solomessage;

import android.content.ClipboardManager;
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
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final String TAG = "MessageAdapter";

    public ArrayList<Message> messages = new ArrayList<>();
    private Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void AddMessage(Message message){
        messages.add(message);
        notifyItemInserted(messages.size()-1);
    }

    public void RemoveMessages(){
        messages.clear();
        notifyDataSetChanged();
    }

    public void ScrollToPosition(RecyclerView recyclerView){
        recyclerView.scrollToPosition(messages.size()-1);
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

        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                // Copy content message
                Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text",  messages.get(position).GetUserMessage());
                clipboard.setPrimaryClip(clip);
                return true;
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
