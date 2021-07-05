package com.ok.Members.Complaints;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ok.Home.R;


import java.util.ArrayList;

public class RecyclerAdapterActiveComplaints extends RecyclerView.Adapter<RecyclerAdapterActiveComplaints.MemberComplaintViewHolder> {

    private ArrayList<MemberComplaintItem> mComplaintList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onCloseClick(int position,EditText mHeadRemarks);
        void onSubmitRemarksClick(int position, EditText mHeadRemarks, TextView mLastRemarksByHead);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class MemberComplaintViewHolder extends RecyclerView.ViewHolder {
        public TextView mComplaintID,mDepartment, mComplaintMsg;
        public TextView mComplaintDate, mLastStatusDate;
        public TextView mApplicantID, mApplicantName;
        public TextView mLastRemarksByHead;
        public EditText mHeadRemarks;
        Button sendRemarks;
        ImageView closeComplaint;

        public MemberComplaintViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mComplaintID = itemView.findViewById(R.id.id_member_complaint_tv_complaintID);
            mDepartment = itemView.findViewById(R.id.id_member_complaint_tv_department);
            mComplaintMsg = itemView.findViewById(R.id.id_member_complaint_tv_complaintMSG);
            mComplaintDate = itemView.findViewById(R.id.id_member_complaint_tv_complaintDate);
            mLastStatusDate = itemView.findViewById(R.id.id_member_complaint_tv_complaintStatusDate);
            mLastRemarksByHead = itemView.findViewById(R.id.id_member_complaint_tv_previousRemarks);
            mHeadRemarks = itemView.findViewById(R.id.id_member_complaint_et_headRemarks);
            mApplicantID = itemView.findViewById(R.id.id_member_complaint_tv_applicantID);
            mApplicantName = itemView.findViewById(R.id.id_member_complaint_tv_applicantName);
            sendRemarks = itemView.findViewById(R.id.id_member_complaint_btn_submitRemarks);
            closeComplaint = itemView.findViewById(R.id.id_member_complaint_iv_close_issue_img);


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

            sendRemarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSubmitRemarksClick(position,mHeadRemarks,mLastRemarksByHead);
                        }
                    }
                }
            });

            closeComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCloseClick(position,mHeadRemarks);
                        }
                    }
                }
            });

        }
    }

    public RecyclerAdapterActiveComplaints(ArrayList<MemberComplaintItem> complaintList) {

        mComplaintList = complaintList;
    }

    @Override
    public MemberComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_member_active_complaints_item, parent, false);
        MemberComplaintViewHolder evh = new MemberComplaintViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MemberComplaintViewHolder holder, int position) {
        MemberComplaintItem currentItem = mComplaintList.get(position);
        holder.mComplaintID.setText("Complaint ID: "+ currentItem.getComplaintID());
        holder.mDepartment.setText("Department: "+ currentItem.getDepartment());
        holder.mComplaintMsg.setText("Complaint: "+ currentItem.getComplaintMSG());
        holder.mComplaintDate.setText("Complaint Date: "+ currentItem.getComplaintDate());
        holder.mLastStatusDate.setText("Last Status Date: "+ currentItem.getLastStatusDate());
        holder.mLastRemarksByHead.setText("Remarks: "+ currentItem.getRemarksByHead());
        holder.mApplicantID.setText("Applicant ID: "+ currentItem.getApplicantID());
        holder.mApplicantName.setText("Applicant Name "+ currentItem.getApplicantName());
    }

    @Override
    public int getItemCount() {
        return mComplaintList.size();
    }
}
