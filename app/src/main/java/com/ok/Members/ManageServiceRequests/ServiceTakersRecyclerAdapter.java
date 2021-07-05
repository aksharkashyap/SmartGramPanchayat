package com.ok.Members.ManageServiceRequests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ok.Home.R;
import java.util.ArrayList;

public class ServiceTakersRecyclerAdapter extends RecyclerView.Adapter<ServiceTakersRecyclerAdapter.ServiceViewHolder> {

    private ArrayList<ServiceTakersItem> mServiceTakersList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onRevokeClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView mServiceID, mServiceName;
        public TextView mApplicantID, mApplicantName,mMobile;
        public ImageView mRevokeService;

        public ServiceViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mServiceID = itemView.findViewById(R.id.id_member_serviceTakers_tv_serviceID);
            mServiceName = itemView.findViewById(R.id.id_member_serviceTakers_tv_serviceName);
            mApplicantID = itemView.findViewById(R.id.id_member_serviceTakers_tv_applicantID);
            mApplicantName = itemView.findViewById(R.id.id_member_serviceTakers_tv_applicantName);
            mRevokeService = itemView.findViewById(R.id.id_member_serviceTakers_iv_revokeService);
            mMobile = itemView.findViewById(R.id.id_member_serviceTakers_tv_applicantMobile);

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

            mRevokeService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRevokeClick(position);
                        }
                    }
                }
            });
        }
    }

    public ServiceTakersRecyclerAdapter(ArrayList<ServiceTakersItem> serviceTakersList) {

        mServiceTakersList = serviceTakersList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_member_service_takers_item, parent, false);
        ServiceViewHolder serviceViewHolder = new ServiceViewHolder(v, mListener);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        ServiceTakersItem currentItem = mServiceTakersList.get(position);
        holder.mServiceID.setText("Service ID: " + currentItem.getServiceID());
        holder.mServiceName.setText("Service: " + currentItem.getServiceName());
        holder.mApplicantID.setText("Applicant ID: " + currentItem.getApplicantID());
        holder.mApplicantName.setText("Applicant Name: " + currentItem.getApplicantName());
        holder.mMobile.setText("Mobile: " + currentItem.getMobile());
    }

    @Override
    public int getItemCount() {
        return mServiceTakersList.size();
    }
}

