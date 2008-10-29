package org.zaach.android.exec;

import java.io.IOException;

import org.zaach.android.exec.Proc;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ExecBin extends Activity {
	
	public void doExec(){
        // Perform action on click
 	   Proc proc = new Proc();
 	   final EditText outbox = (EditText) findViewById(R.id.result);
 	   
 	   final AutoCompleteTextView cmd = (AutoCompleteTextView) findViewById(R.id.prompt);
 	   
 	   String command_text = cmd.getText().toString();
 	   
 	   if(command_text.length() == 0)
 		   return;
 	   
 	   Log.d("ExecBin","command_text was: "+command_text);
 	   
 	   String outp = null;
		outbox.setText(R.string.executing);
 	   
 	   try {
			outp = proc.exec(command_text); // try /data/hi
			outbox.setText(outp);
		} catch (IOException e) {
			Log.e("ExecBin", e.toString());
			outbox.setText(R.string.broke);
		}
	}

	   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.main);
	   
	   final AutoCompleteTextView prompt = (AutoCompleteTextView) findViewById(R.id.prompt);

	   final EditText result = (EditText) findViewById(R.id.result);
	   
	   prompt.setOnKeyListener(new View.OnKeyListener(){
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_ENTER){
				doExec();
				return true;
			}
			return false;
		}
	   });

       registerForContextMenu(result);
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
       menu.add(R.string.copy);
   }
   
   @Override
   public boolean onContextItemSelected(MenuItem item) {
       //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	   
	   final TextView outbox = (TextView) findViewById(R.id.result);
	   String text = outbox.getText().toString();
	   
	   Log.d("ExecBin","result copied");
	  
	   ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	   clipboard.setText(text);
	   
       Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
       return true;
   }
}