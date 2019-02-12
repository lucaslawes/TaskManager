/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class JsonFileManager {
    
    private static Gson createGsonWithDateFormat() {
        return new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
    }
    
    public static void toJson(String filename, Object data) {
        Gson gson = createGsonWithDateFormat();           
        try(FileWriter fileWriter = new FileWriter(filename)) {
            gson.toJson(data, fileWriter);    
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
    
    public static <T extends Object> T fromJson(String filename, Class<T> type) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            Gson gson = createGsonWithDateFormat();
            return gson.fromJson(bufferedReader, type);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }       
    }
}
