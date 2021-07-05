package com.ok.Sarpanch.ManageServices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ok.Home.R;

import java.util.ArrayList;

public class RecyclerAdapterService extends RecyclerView.Adapter<RecyclerAdapterService.ServiceViewHolder> {

    private ArrayList<ServiceItem> mServicesList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onUpdateClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView mServiceID, mServiceName;
        public ImageView mDeleteImage, mUpdateImage;

        public ServiceViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mServiceID = itemView.findViewById(R.id.id_sarpanch_tv_ServiceID);
            mServiceName = itemView.findViewById(R.id.id_sarpanch_tv_ServiceName);
            mDeleteImage = itemView.findViewById(R.id.id_sarpanch_iv_service_delete_img);
            mUpdateImage = itemView.findViewById(R.id.id_sarpanch_iv_service_update_img);

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

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mUpdateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUpdateClick(position);
                        }
                    }
                }
            });


        }
    }

    public RecyclerAdapterService(ArrayList<ServiceItem> serviceItemsList) {

        mServicesList = serviceItemsList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_service_item, parent, false);
        ServiceViewHolder serviceViewHolder = new ServiceViewHolder(v, mListener);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        ServiceItem currentItem = mServicesList.get(position);
        holder.mServiceID.setText("Service ID: " + currentItem.getServiceID());
        holder.mServiceName.setText("Service: " + currentItem.getServiceName());
    }

    @Override
    public int getItemCount() {
        return mServicesList.size();
    }
}
