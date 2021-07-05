package com.ok.Members.ManageServiceRequests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ok.Home.R;
import java.util.ArrayList;

public class ServiceRequestsRecyclerAdapter extends RecyclerView.Adapter<ServiceRequestsRecyclerAdapter.ServiceViewHolder> {

    private ArrayList<ServiceRequestsItem> mServicesList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onApproveClick(int position);

        void onRejectClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView mServiceID, mServiceName;
        public TextView mApplicantID, mApplicantName;
        public ImageView mApproveRequest, mRejectRequest;

        public ServiceViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mServiceID = itemView.findViewById(R.id.id_member_serviceRequests_tv_serviceID);
            mServiceName = itemView.findViewById(R.id.id_member_serviceRequests_tv_serviceName);
            mApplicantID = itemView.findViewById(R.id.id_member_serviceRequests_tv_applicantID);
            mApplicantName = itemView.findViewById(R.id.id_member_serviceRequests_tv_applicantName);
            mApproveRequest = itemView.findViewById(R.id.id_member_serviceRequests_iv_approveRequest);
            mRejectRequest = itemView.findViewById(R.id.id_member_serviceRequests_iv_rejectRequest);

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

            mApproveRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onApproveClick(position);
                        }
                    }
                }
            });

            mRejectRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRejectClick(position);
                        }
                    }
                }
            });

        }
    }

    public ServiceRequestsRecyclerAdapter(ArrayList<ServiceRequestsItem> serviceItemsList) {

        mServicesList = serviceItemsList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_member_service_requests_item, parent, false);
        ServiceViewHolder serviceViewHolder = new ServiceViewHolder(v, mListener);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        ServiceRequestsItem currentItem = mServicesList.get(position);
        holder.mServiceID.setText("Service ID: " + currentItem.getServiceID());
        holder.mServiceName.setText("Service: " + currentItem.getServiceName());
        holder.mApplicantID.setText("Applicant ID: " + currentItem.getApplicantID());
        holder.mApplicantName.setText("Applicant Name: " + currentItem.getApplicantName());
    }

    @Override
    public int getItemCount() {
        return mServicesList.size();
    }
}
