package com.ok.Admin.UserValidation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ok.Home.R;


import java.util.List;

public class RecyclerAdapterValidateUser extends RecyclerView.Adapter<RecyclerAdapterValidateUser.UserNoteViewHolder> {

    private List<RecyclerValidateUserItem> mUserList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onValidateClick(int position);
        void onRejectClick(int position);
    }

    public RecyclerAdapterValidateUser(List<RecyclerValidateUserItem> userList) {
        mUserList = userList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UserNoteViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv, mobile_tv,email_tv, village_tv, city_tv, state_tv, userID_tv, villageID_tv, remarks;
        ImageView mValidateImage, mRejectImage;

        public UserNoteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_userName);
            mobile_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_mobile);
            email_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_email);
            village_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_village);
            villageID_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_villageID);
            city_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_city);
            state_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_state);
            userID_tv = itemView.findViewById(R.id.id_admin_validate_user_tv_userID);
            remarks = itemView.findViewById(R.id.id_admin_validate_user_tv_remarks);

            mValidateImage = itemView.findViewById(R.id.id_admin_validate_user_iv_approve_img);
            mRejectImage = itemView.findViewById(R.id.id_admin_validate_user_iv_reject_img);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            mRejectImage.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onRejectClick(position);
                    }
                }
            });

            mValidateImage.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onValidateClick(position);
                    }
                }
            });
        }
    }

    @Override
    public UserNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_admin_recycler_validate_user_item, parent, false);
        UserNoteViewHolder evh = new UserNoteViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(UserNoteViewHolder noteHolder, int position) {
        RecyclerValidateUserItem currentNoteItem = mUserList.get(position);
        noteHolder.name_tv.setText(currentNoteItem.getUserName());
        noteHolder.mobile_tv.setText("Mobile: "+ currentNoteItem.getMobile());
        noteHolder.email_tv.setText("Email: "+ currentNoteItem.getEmail());
        noteHolder.village_tv.setText("Village: "+ currentNoteItem.getVillage());
        noteHolder.villageID_tv.setText("Village ID: "+ currentNoteItem.getVillID());
        noteHolder.city_tv.setText("City: "+ currentNoteItem.getCity());
        noteHolder.state_tv.setText("State: "+ currentNoteItem.getState());
        noteHolder.userID_tv.setText("User ID: "+ currentNoteItem.getUserID());

        noteHolder.remarks.setText("Remarks: "+
                (currentNoteItem.getVerificationStatus().equals("pending_sarpanch") ? "Sarpanch" : "Villager")
        );
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}

