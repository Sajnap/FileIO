package com.imrokraft.fileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startCopyFile(View v) {
		new CopyFileAsyncTask().execute();
	}
	public void startSaveFile(View v) {
		EditText edit=(EditText)findViewById(R.id.editText1);
		new SaveFileAsyncTask().execute(edit.getText().toString());
	}

	class SaveFileAsyncTask extends AsyncTask <String, Integer, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
			pb.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(),"Async Task started",Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(String... params) {
			File f1=new File(Environment.getExternalStorageDirectory(), "source.txt");
			String s=params[0];
			try{
				if(!f1.exists()){
					f1.createNewFile();
				}
				FileOutputStream fos=new FileOutputStream(f1);
				fos.write(s.getBytes());
				fos.flush();
				fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			return "Completed new File save in Background";
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
			pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(),"Save File Task finished",Toast.LENGTH_LONG).show();
		}
	}

	class CopyFileAsyncTask extends AsyncTask <String, Integer, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
			pb.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(),"Copy File Async Task started",Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				File f1=new File(Environment.getExternalStorageDirectory(), "source.txt");
				File f2=new File(Environment.getExternalStorageDirectory(), "target.txt");

				if(!f2.exists()){
					f2.createNewFile();
				}
				if(!f1.exists()){
					return "Copy file failed doInBackground()";
				}
				InputStream in=new FileInputStream(f1);
				OutputStream out=new FileOutputStream(f2);
				byte[] buf=new byte[1024];
				int len;
				while ((len=in.read(buf))>0){
					out.write(buf,0,len);

				}
				in.close();
				out.close();
				System.out.println("File copied");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Copy file Completed doInBackground()";
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
			pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(),s+"\nCopy file Async Task finished",Toast.LENGTH_LONG).show();
		}
	}





}
