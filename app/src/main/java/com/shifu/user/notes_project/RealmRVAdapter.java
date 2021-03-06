package com.shifu.user.notes_project;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shifu.user.notes_project.realm.Messages;
import com.shifu.user.notes_project.realm.MessagesAuthor;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RealmRVAdapter extends RealmRecyclerViewAdapter<Messages, RealmRVAdapter.ViewHolder> {

    private final static String date_format = "HH:mm:ss dd.MM.yyyy";
    private String username;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView text, date, author;
        public Messages data;

        ViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.msg_text);
            date = v.findViewById(R.id.msg_date);
            author = v.findViewById(R.id.msg_author);
        }
    }


    RealmRVAdapter(OrderedRealmCollection<Messages> data) {
        super(data, true);
        setHasStableIds(true);
    }

    RealmRVAdapter(OrderedRealmCollection<Messages> data, String username) {
        super(data, true);
        setHasStableIds(true);
        this.username = username;
        Log.d("RA", "Set username:"+username);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fr_msg, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, int position) {
        final Messages obj = getItem(position);
        viewHolder.data = obj;
        viewHolder.text.setText(obj.getText());
        viewHolder.date.setText(new SimpleDateFormat(date_format, Locale.US).format(new Date(obj.getDate())));
        viewHolder.author.setText(ActivityMain.getRC().getItem(MessagesAuthor.class, null, null).getUsername());

        }

    @Override
    public long getItemId(int index) {
        //Log.d("RA.getItemId", getItem(index).toString());
        //TODO "заплатка" (return должен быть long, a uuid string. По утверждению автора работает норм примерно до 1*10^6 записей)
        return java.nio.ByteBuffer.wrap(getItem(index).getID().getBytes()).asLongBuffer().get();
    }

}
