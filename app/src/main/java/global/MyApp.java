package global;

import android.app.Application;

import data.Data;

/**
 * Created by DevelopX on 2016-03-14.
 */
public class MyApp extends Application {


    public Data mdata;


    public void Initialize(){

        if(mdata==null){
           mdata =new Data(getApplicationContext());
        }
    }


    public Data getData(){
        Initialize(); //Make sure the it exists

        return mdata; //Return the data
    }



}
