package com.ok.Sarpanch.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ok.Home.R;
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteMember;

import java.util.List;

public class RecyclerAdapterAddMember extends RecyclerView.Adapter<RecyclerAdapterAddMember.NoteViewHolder> {

    private List<RecyclerNoteMember> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onCheckBoxSelect(int position);

        void onCheckBoxDeselect(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv, mobile_tv,email_tv;
        public CheckBox addMember_cb;

        public NoteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villager_name);
            mobile_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villager_mobile);
            email_tv = itemView.findViewById(R.id.id_sarpanch_recycler_villager_email);
            addMember_cb = itemView.findViewById(R.id.id_sarpanch_recycler_villager_checkbox);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            addMember_cb.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if(addMember_cb.isChecked()) listener.onCheckBoxSelect(position);
                        else listener.onCheckBoxDeselect(position);
                    }
                }
            });

        }
    }

    public RecyclerAdapterAddMember(List<RecyclerNoteMember> noteList) {

        mNoteList = noteList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sarpanch_addmember_villager_recycler_items, parent, false);
        NoteViewHolder evh = new NoteViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteHolder, int position) {
        RecyclerNoteMember currentNoteItem = mNoteList.get(position);
        noteHolder.name_tv.setText(currentNoteItem.getUserName());
        noteHolder.mobile_tv.setText("Mobile: "+ currentNoteItem.getUserMobile());
        noteHolder.email_tv.setText("Email: " + currentNoteItem.getUserEmail());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}