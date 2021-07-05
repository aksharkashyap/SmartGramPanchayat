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

public class RecyclerAdapterClosedComplaints extends RecyclerView.Adapter<RecyclerAdapterClosedComplaints.MemberComplaintViewHolder> {

    private ArrayList<MemberComplaintItem> mClosedComplaintList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class MemberComplaintViewHolder extends RecyclerView.ViewHolder {
        public TextView mComplaintID,mDepartment, mComplaintMsg;
        public TextView mComplaintDate, mClosingDate;
        public TextView mApplicantID, mApplicantName;
        public TextView mLastRemarksByHead;

        public MemberComplaintViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mComplaintID = itemView.findViewById(R.id.id_member_closedComplaint_tv_complaintID);
            mDepartment = itemView.findViewById(R.id.id_member_closedComplaint_tv_department);
            mComplaintMsg = itemView.findViewById(R.id.id_member_closedComplaint_tv_complaintMSG);
            mComplaintDate = itemView.findViewById(R.id.id_member_closedComplaint_tv_complaintDate);
            mClosingDate = itemView.findViewById(R.id.id_member_closedComplaint_tv_complaintClosingDate);
            mLastRemarksByHead = itemView.findViewById(R.id.id_member_closedComplaint_tv_headRemarks);
            mApplicantID = itemView.findViewById(R.id.id_member_closedComplaint_tv_applicantID);
            mApplicantName = itemView.findViewById(R.id.id_member_closedComplaint_tv_applicantName);


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

    public RecyclerAdapterClosedComplaints(ArrayList<MemberComplaintItem> closedComplaintList) {

        mClosedComplaintList = closedComplaintList;
    }

    @Override
    public MemberComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_member_closed_complaints_item, parent, false);
        MemberComplaintViewHolder evh = new MemberComplaintViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MemberComplaintViewHolder holder, int position) {
        MemberComplaintItem currentItem = mClosedComplaintList.get(position);
        holder.mComplaintID.setText("Complaint ID: "+ currentItem.getComplaintID());
        holder.mDepartment.setText("Department: "+ currentItem.getDepartment());
        holder.mComplaintMsg.setText("Complaint: "+ currentItem.getComplaintMSG());
        holder.mComplaintDate.setText("Complaint Date: "+ currentItem.getComplaintDate());
        holder.mClosingDate.setText("Closing Date: "+ currentItem.getLastStatusDate());
        holder.mLastRemarksByHead.setText("Remarks: "+ currentItem.getRemarksByHead());
        holder.mApplicantID.setText("Applicant ID: "+ currentItem.getApplicantID());
        holder.mApplicantName.setText("Applicant Name "+ currentItem.getApplicantName());
    }

    @Override
    public int getItemCount() {
        return mClosedComplaintList.size();
    }
}
