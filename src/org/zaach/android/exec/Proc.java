package org.zaach.android.exec;

import java.io.*;

import android.util.Log;

// http://java.sun.com/developer/JDCTechTips/2003/tt0304.html

public class Proc {
    String exec(String cmd) throws IOException {
    	
    	String lines = "";
    	
        try {

        // start the ls command running
    	
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(cmd);

        // put a BufferedReader on the ls output

        InputStream inputstream =
            proc.getInputStream();
        InputStreamReader inputstreamreader =
            new InputStreamReader(inputstream);
        BufferedReader bufferedreader =
            new BufferedReader(inputstreamreader);

        // read the ls output

        String line;
        
        while ((line = bufferedreader.readLine()) 
                  != null) {
            lines += line+"\n";
        }
    
        // check for ls failure

            if (proc.waitFor() != 0) {
                Log.d("Proc.java", "exit value = " +
                    proc.exitValue());
                lines += "EXIT CODE: " + proc.exitValue();
            }
        }
        catch (InterruptedException e) {
        	Log.e("Proc.java", e.toString());
            lines += "EXCEPTION: "+e.toString();
        }catch (IndexOutOfBoundsException e){
        	Log.e("Proc.java", e.toString());
        }
        
        return lines;
    }

}
