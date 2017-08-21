package com.example.karshima.mappifyapp.utility;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;



/**
 * Created by Karishma on 10/03/17.
 */
public class HelperClass  {


    public static void showToast(Context context,String msg){


        Toast t=Toast.makeText(context,msg,Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();


    }

    public static String getRating(String r){

        String rate="";

        if(r.equals("0")){

            rate="0";
        }

        if(r.equals("1")){

            rate="1.0";
        }

        if(r.equals("2")){

            rate="1.5";
        }

        if(r.equals("3")){

            rate="2.0";
        }

        if(r.equals("4")){

            rate="2.5";
        }

        if(r.equals("5")){

            rate="3.0";
        }

        if(r.equals("6")){

            rate="3.5";
        }

        if(r.equals("7")){

            rate="4.0";
        }

        if(r.equals("8")){

            rate="4.5";
        }
        if(r.equals("9")){

            rate="5.0";
        }

        if(r.equals("10")){

            rate="5.5";
        }

        if(r.equals("11")){

            rate="6.0";
        }

        if(r.equals("12")){

            rate="6.5";
        }

        if(r.equals("13")){

            rate="7.0";
        }


         return rate;
    }


    public static String makeUppperLetter(String name){

        String title = name.substring(0, 1).toUpperCase() + name.substring(1);

        return title;


    }


}
