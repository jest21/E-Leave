package com.example.user.e_leave;


import java.util.Calendar;
import java.util.HashMap;

class Months {
    private HashMap<String,Integer> NO_OF_DAYS_IN_MONTH;
    private String[] MONTHS = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
    };
    Months(){
        NO_OF_DAYS_IN_MONTH = new HashMap<>();
        NO_OF_DAYS_IN_MONTH.put("January",31);
        NO_OF_DAYS_IN_MONTH.put("February",29);
        NO_OF_DAYS_IN_MONTH.put("March",31);
        NO_OF_DAYS_IN_MONTH.put("April",30);
        NO_OF_DAYS_IN_MONTH.put("May",31);
        NO_OF_DAYS_IN_MONTH.put("June",30);
        NO_OF_DAYS_IN_MONTH.put("July",31);
        NO_OF_DAYS_IN_MONTH.put("August",31);
        NO_OF_DAYS_IN_MONTH.put("September",30);
        NO_OF_DAYS_IN_MONTH.put("October",31);
        NO_OF_DAYS_IN_MONTH.put("November",30);
        NO_OF_DAYS_IN_MONTH.put("December",31);
    }

    int thisMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    String getNameOfThisMonth(){
        return MONTHS[thisMonth()];
    }
}
