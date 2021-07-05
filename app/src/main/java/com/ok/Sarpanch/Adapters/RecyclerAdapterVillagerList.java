package com.ok.Sarpanch.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ok.Home.R;
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteVillagerList;

import java.util.List;

public class RecyclerAdapterVillagerList extends RecyclerView.Adapter<RecyclerAdapterVillagerList.NoteViewHolder> {

    private List<RecyclerNoteVillagerList> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RecyclerAdapterVillagerList(List<RecyclerNoteVillagerList> noteList) {
        mNoteList = noteList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv, mobile_tv,email_tv;

        public NoteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villagerList_name);
            mobile_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villagerList_mobile);
            email_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villagerList_email);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_sarpanch_villager_list_recycler_items, parent, false);
        NoteViewHolder evh = new NoteViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteHolder, int position) {
        RecyclerNoteVillagerList currentNoteItem = mNoteList.get(position);
        noteHolder.name_tv.setText(currentNoteItem.getUserName());
        noteHolder.mobile_tv.setText("Mobile: "+ currentNoteItem.getUserMobile());
        noteHolder.email_tv.setText("Email: "+ currentNoteItem.getUserEmail());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
