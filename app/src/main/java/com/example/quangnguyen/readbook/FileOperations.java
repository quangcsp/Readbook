package com.example.quangnguyen.readbook;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by quangnguyen on 21/03/2017.
 */

public class FileOperations {
    public FileOperations() {

    }

    public String read(String fname){

        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/"+fname+".txt";

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;

    }

}
