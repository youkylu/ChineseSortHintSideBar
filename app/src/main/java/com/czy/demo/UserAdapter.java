package com.czy.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<User> userList;

    private LayoutInflater inflater;


    public UserAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        userList = new ArrayList<>();
    }


    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

        int section = getSectionForPosition(position);
        if(position == getPositionForSection(section)){
            holder.hear_linearLayout.setVisibility(View.VISIBLE);
            holder.hear_textview.setText(String.valueOf(userList.get(position).getHeadLetter()));
        }else{
            holder.hear_linearLayout.setVisibility(View.GONE);
        }

        holder.tv_userName.setText(userList.get(position).getUserName());

    }

    public void setData(List<User> userList) {
        this.userList.clear();
        this.userList = userList;
    }

    public int getFirstPositionByChar(char sign) {
        if (sign == '↑' || sign == '☆') {
            return 0;
        }
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getHeadLetter() == sign) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return userList.get(position).getHeadLetter();
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = String.valueOf(userList.get(i).getHeadLetter());
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class UserHolder extends RecyclerView.ViewHolder {

        public TextView tv_userName;

        private LinearLayout hear_linearLayout;

        private TextView hear_textview;


        public UserHolder(View itemView) {
            super(itemView);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            hear_linearLayout = (LinearLayout) itemView.findViewById(R.id.hear_linearLayout);
            hear_textview = (TextView) itemView.findViewById(R.id.hear_textview);
        }
    }

}
