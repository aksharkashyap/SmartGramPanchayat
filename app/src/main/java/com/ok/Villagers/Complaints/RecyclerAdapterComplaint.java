package com.ok.Villagers.Complaints;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ok.Home.R;


import java.util.ArrayList;

public class RecyclerAdapterComplaint extends RecyclerView.Adapter<RecyclerAdapterComplaint.ComplaintViewHolder> {

    private ArrayList<RecyclerComplaintItem> mComplaintList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        public TextView mComplaintID,mDepartment, mComplaintMsg;
        public TextView mComplaintStatus, mRemarksByHead;
        public TextView mComplaintDate, mLastStatusDate;


        public ComplaintViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mComplaintID = itemView.findViewById(R.id.id_villager_complaint_tv_complaintID);
            mDepartment = itemView.findViewById(R.id.id_villager_complaint_tv_department);
            mComplaintMsg = itemView.findViewById(R.id.id_villager_complaint_tv_complaintMSG);
            mComplaintStatus = itemView.findViewById(R.id.id_villager_complaint_tv_complaintStatus);
            mRemarksByHead = itemView.findViewById(R.id.id_villager_complaint_tv_headRemarks);
            mComplaintDate = itemView.findViewById(R.id.id_villager_complaint_tv_complaintDate);
            mLastStatusDate = itemView.findViewById(R.id.id_villager_complaint_tv_complaintStatusDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapterComplaint(ArrayList<RecyclerComplaintItem> complaintList) {

        mComplaintList = complaintList;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_villager_complaint_item, parent, false);
        ComplaintViewHolder evh = new ComplaintViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        RecyclerComplaintItem currentItem = mComplaintList.get(position);
        holder.mComplaintID.setText("Complaint ID: "+ currentItem.getComplaintID());
        holder.mDepartment.setText("Department: "+ currentItem.getDepartment());
        holder.mComplaintMsg.setText("Complaint: "+ currentItem.getComplaintMSG());
        holder.mComplaintDate.setText("Complaint Date: "+ currentItem.getComplaintDate());
        holder.mRemarksByHead.setText("Remarks by Head: "+ currentItem.getRemarksByHead());
        holder.mComplaintStatus.setText("Status: "+ currentItem.getComplaintStatus());
        holder.mLastStatusDate.setText("Last Status Date: "+ currentItem.getLastStatusDate());
    }

    @Override
    public int getItemCount() {
        return mComplaintList.size();
    }
}
