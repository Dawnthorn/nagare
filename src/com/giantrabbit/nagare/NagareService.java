package com.giantrabbit.nagare;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

public class NagareService extends Service
{
	public URL m_url = null;
	public DownloadThread m_download_thread = null;
	public MediaPlayer m_media_player = null;
	public Context m_context;
	public int m_current_position = 0;
	public final Handler m_handler = new Handler();
	public String m_errors = "";
	public int m_state;
	public static final int STOPPED = 0;
	public static final int DOWNLOADING = 1;
	
	public NagareService()
	{
		m_state = STOPPED;
	}
	
	public void download(String url_string)
	{
		try
		{
			m_url = new URL(url_string);
		} 
		catch (MalformedURLException e)
		{
			m_errors += "Error parsing URL (" + url_string + "): " + e.toString() + "\n";
		}
		
		if (m_errors == "")
		{
			m_context = getApplication().getApplicationContext();
			m_download_thread = new DownloadThread(m_context, m_url);
			m_download_thread.start();
			m_state = DOWNLOADING;
		}
	}
	
	public String errors()
	{
		if (m_download_thread != null)
		{
			return m_errors + m_download_thread.errors();		
		}
		return m_errors;
	}
	
	public String file_name()
	{
		if (m_download_thread == null)
		{
			return null;
		}
		
		if (m_download_thread.m_shoutcast_file == null)
		{
			return null;
		}
		
		return m_download_thread.m_shoutcast_file.m_file_name;
	}
	
	public IBinder onBind(Intent intent)
	{
		return m_binder;
	}
	
	public long position()
	{
		if (m_download_thread == null)
		{
			return -1;
		}
		
		if (m_download_thread.m_shoutcast_file == null)
		{
			return -1;
		}
		
		return m_download_thread.m_shoutcast_file.m_current_write_pos;
	}
	
	public int state()
	{
		return m_state;
	}
	
	public void stop()
	{
		if (m_download_thread == null)
		{
			return;
		}
		m_download_thread.done();
		m_download_thread = null;
		m_state = STOPPED;
	}

	private final INagareService.Stub m_binder = new INagareService.Stub()
	{
		public void download(String url)
		{
			NagareService.this.download(url);
		}
		
		public String errors()
		{
			return NagareService.this.errors();
		}
		
		public String file_name()
		{
			return NagareService.this.file_name();
		}
		
		public long position()
		{
			return NagareService.this.position();
		}
		
		public int state()
		{
			return NagareService.this.state();
		}
		
		public void stop()
		{
			NagareService.this.stop();
		}
	};
}
