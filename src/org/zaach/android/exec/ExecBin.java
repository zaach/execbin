package org.zaach.android.exec;

import java.io.IOException;

import org.zaach.android.exec.Proc;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ExecBin extends Activity {
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.main);
	   
	   
	   final Button execBtn = (Button) findViewById(R.id.exec);	   
	   final Button copyBtn = (Button) findViewById(R.id.copy);	 

	   //final TextView result = (TextView) findViewById(R.id.result);
	   
       execBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Perform action on click
        	   Proc proc = new Proc();
        	   final TextView outbox = (TextView) findViewById(R.id.result);
        	   
        	   final EditText cmd = (EditText) findViewById(R.id.command);
        	   
        	   String command_text = cmd.getText().toString();
        	   
        	   if(command_text.length() == 0)
        		   return;
        	   
        	   Log.d("ExecBin","command_text was: "+command_text);
        	   
        	   String outp = null;
        	   
        	   try {
				outp = proc.exec(command_text); // try /data/hi
				outbox.setText(outp);
			} catch (IOException e) {
				Log.e("ExecBin", e.toString());
			}
        	   
           }
       });
       
       copyBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Perform action on click
        	   final TextView outbox = (TextView) findViewById(R.id.result);
        	   String text = outbox.getText().toString();
        	   
        	   Log.d("ExecBin","result copied");
        	  
        	   ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			   clipboard.setText(text);
        	   
           }
       });

   }
}