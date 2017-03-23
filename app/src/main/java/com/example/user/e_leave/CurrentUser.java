package com.example.user.e_leave;

import android.content.Context;

/***
 * Created by livin on 23/3/17.
 */

public class CurrentUser {

    public static void setFacultyId(Context context, String facultyId){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_ID",facultyId).apply();
    }

    public static String getFacultyID(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_ID","");
    }

    public static void setName(Context context, String name){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_NAME",name).apply();
    }

    public static String getName(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_NAME","");
    }

    public static void setDesignation(Context context, String designation){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_DESIGN",designation).apply();
    }

    public static String getDesignation(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_DESIGN","");
    }

    public static void setEmail(Context context, String email){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_EMAIL",email).apply();
    }

    public static String getEmail(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_EMAIL","");
    }

}
