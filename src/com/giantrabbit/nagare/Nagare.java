package com.giantrabbit.nagare;

import java.net.URL;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Nagare extends Activity implements OnClickListener 
{
	public TextView m_output; 
	public EditText m_url_editor;
	public TextView m_alert; 
	public Context m_context;
	public URL m_url;
	public ImageButton m_play_button;
	public INagareService m_nagare_service = null;
	
	private ServiceConnection m_nagare_service_connection = new ServiceConnection()
	{
		public void onServiceConnected(ComponentName classname, IBinder service)
		{
			m_nagare_service = INagareService.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName name)
		{
			m_nagare_service = null;
		}	
	};
	
	private final Runnable m_run_refresh = new Runnable()
	{
		public void run()
		{
			refresh();
			m_handler.postDelayed(this, 1000);
		}
	};
	
	private final Handler m_handler = new Handler();
	
	public void onClick(View v)
	{
		if (m_nagare_service == null)
		{

			return;
		}
		try
		{
			int state = m_nagare_service.state();
			if (state == NagareService.STOPPED)
			{
				m_nagare_service.download(m_url_editor.getText().toString());
			}
			else
			{
				m_nagare_service.stop();
			}
		}
		catch (RemoteException e)
		{
			m_output.setText("Error connecting to NagareService: " + e.toString() + "\n");
		}
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        m_output = (TextView) findViewById(R.id.output);
        m_url_editor = (EditText) findViewById(R.id.url_editor);
        m_alert = (TextView) findViewById(R.id.alert);
        m_output = (TextView) findViewById(R.id.output);
        m_url_editor.setText("http://kqedsc01.streamguys.us/");
        m_play_button = (ImageButton) findViewById(R.id.play);        
        m_play_button.setOnClickListener(this);
        m_play_button.setImageResource(android.R.drawable.ic_media_play);
        m_context = getApplicationContext();
        bindService(new Intent(m_context, NagareService.class), m_nagare_service_connection, Context.BIND_AUTO_CREATE);
        m_run_refresh.run();
    }
    
    public void refresh()
    {
    	if (m_nagare_service == null)
    	{
    		return;
    	}
    	try
		{
    		int state = m_nagare_service.state();
    		if (state == NagareService.STOPPED)
    		{
    			m_play_button.setImageResource(android.R.drawable.ic_media_play);
    		}
    		else
    		{
    			m_play_button.setImageResource(android.R.drawable.ic_media_pause);
    		}
			m_output.setText(String.valueOf(m_nagare_service.position()));
			String errors = m_nagare_service.errors();
			if (errors != "")
			{
				m_output.setText(String.valueOf(m_nagare_service.position()) + "\n" + errors);
			}
		} 
    	catch (RemoteException e)
		{
    		m_output.setText("Error connecting to NagareService: " + e.toString() + "\n");
		}
    }
}