package com.ok.Home.MemberList;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ok.Home.R;
import com.ok.Sarpanch.RecyclerNotes.RecyclerNoteMember;

import java.util.List;

public class RecyclerAdapterMember extends RecyclerView.Adapter<RecyclerAdapterMember.NoteViewHolder> {

    private List<RecyclerNoteMember> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv, mobile_tv,email_tv;

        public NoteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.id_home_recycler_member_name);
            mobile_tv = itemView.findViewById(R.id.id_home_recycler_member_mobile);
            email_tv = itemView.findViewById(R.id.id_home_recycler_member_email);

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

    public RecyclerAdapterMember(List<RecyclerNoteMember> noteList) {

        mNoteList = noteList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_adapter_member_items, parent, false);
        NoteViewHolder evh = new NoteViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteHolder, int position) {
        RecyclerNoteMember currentNoteItem = mNoteList.get(position);
        noteHolder.name_tv.setText(currentNoteItem.getUserName());
        noteHolder.mobile_tv.setText("Mobile: "+currentNoteItem.getUserMobile());
        noteHolder.email_tv.setText("Email: "+currentNoteItem.getUserEmail());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
