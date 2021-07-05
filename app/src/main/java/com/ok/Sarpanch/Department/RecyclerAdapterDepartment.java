package com.ok.Sarpanch.Department;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ok.Home.R;

import java.util.ArrayList;


public class RecyclerAdapterDepartment extends RecyclerView.Adapter<RecyclerAdapterDepartment.DepartmentViewHolder> {

    private ArrayList<DepartmentItem> mDepartmentList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onUpdateClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        public TextView mDepartmentID,mDepartmentName,mDepartmentHead;
        public ImageView mDeleteImage, mUpdateImage;

        public DepartmentViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mDepartmentID = itemView.findViewById(R.id.id_sarpanch_tv_departmentID);
            mDepartmentName = itemView.findViewById(R.id.id_sarpanch_tv_departmentName);
            mDepartmentHead = itemView.findViewById(R.id.id_sarpanch_tv_departmentHead);
            mDeleteImage = itemView.findViewById(R.id.id_sarpanch_iv_department_delete_img);
            mUpdateImage = itemView.findViewById(R.id.id_sarpanch_iv_department_update_img);

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

    public RecyclerAdapterDepartment(ArrayList<DepartmentItem> departmentList) {

        mDepartmentList = departmentList;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_department_item, parent, false);
        DepartmentViewHolder evh = new DepartmentViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        DepartmentItem currentItem = mDepartmentList.get(position);
        holder.mDepartmentID.setText("Department ID: "+ currentItem.getDepartmentId());
        holder.mDepartmentName.setText("Department: "+ currentItem.getDepartmentName());
        holder.mDepartmentHead.setText("Department Head: "+ currentItem.getDepartmentHead());
    }

    @Override
    public int getItemCount() {
        return mDepartmentList.size();
    }
}