package com.ok.Home;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    Context context;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public SharedPref(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
    }

    public void write(UserLoginInfo user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", user.getUID());
        editor.putString("email", user.getEMAIL());
        editor.putInt("status", user.getSTATUS());
        editor.putString("village_id", user.getVILL_ID());
        editor.commit();
    }

    public String getUserId(){
        return sharedPreferences.getString("user_id","Error");
    }
    public String getEmail(){
        return sharedPreferences.getString("email","Error");
    }
    public String getStatus(){
        return sharedPreferences.getString("status","Error");
    }
    public String getVillageId(){
        return sharedPreferences.getString("village_id","Error");
    }

}
