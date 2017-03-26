package com.example.user.e_leave;

import android.content.Context;

class CurrentUser {

    static void setFacultyId(Context context, String facultyId){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_ID",facultyId).apply();
    }

    static String getFacultyID(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_ID","");
    }

    static void setName(Context context, String name){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_NAME",name).apply();
    }

    static String getName(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_NAME","");
    }

    static void setDesignation(Context context, String designation){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_DESIGN",designation).apply();
    }

    static String getDesignation(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_DESIGN","");
    }

    static void setEmail(Context context, String email){
        context.getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("FACULTY_EMAIL",email).apply();
    }

    static String getEmail(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("FACULTY_EMAIL","");
    }

}
