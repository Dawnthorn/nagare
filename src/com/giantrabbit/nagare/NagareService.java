package com.giantrabbit.nagare;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class NagareService extends Service implements OnCompletionListener
{
	public URL m_url = null;
	public DownloadThread m_download_thread = null;
	public MediaPlayer m_media_player = null;
	public Context m_context;
	public int m_current_position = 0;
	public String m_errors = "";
	public int m_state;
	public boolean m_scanned = false;
	public static final int STOPPED = 0;
	public static final int PLAYING = 1;
	public static final int BUFFERING = 2;
	public MediaScannerConnection m_scanner = null;
	public static final int BUFFER_BEFORE_PLAY = 16384;
	
	private final Runnable m_run_buffer = new Runnable()
	{
		public void run()
		{
			int delay = buffer();
			if (delay > 0)
			{
				m_handler.postDelayed(this, delay);
			}
		}
	};
	
	private final Handler m_handler = new Handler();
	
	MediaScannerConnectionClient m_scanner_connection_client = new MediaScannerConnectionClient()
	{

		@Override
		public void onMediaScannerConnected()
		{
			scan();
		}

		@Override
		public void onScanCompleted(String path, Uri uri)
		{

		}
		
	};
	
	public NagareService()
	{
		m_state = STOPPED;
	}
	
	public int buffer()
	{
		if (m_download_thread == null || m_download_thread.m_shoutcast_file == null)
		{
			return 1000;
		}
		if (m_download_thread.m_shoutcast_file.m_current_write_pos - m_current_position > BUFFER_BEFORE_PLAY)
		{
			try
			{
				m_media_player.reset();
				m_media_player.setDataSource(m_download_thread.m_shoutcast_file.file_path());
				m_media_player.prepare();
			}
			catch (Exception e)
			{
				m_errors += "Error starting media player on '" + m_download_thread.m_shoutcast_file.file_path() + "': " + e.toString() + "\n";
			}
			m_media_player.seekTo(m_current_position);
			m_media_player.start();
			m_state = PLAYING;
			scan();
			return 0;
		}
		else
		{
			return 1000;
		}
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
			if (m_scanner == null)
			{
				m_scanner = new MediaScannerConnection(m_context, m_scanner_connection_client);
				m_scanner.connect();
			}
			m_download_thread = new DownloadThread(m_context, m_url);
			m_download_thread.start();
			m_current_position = 0;
			m_state = BUFFERING;
			m_scanned = false;
			if (m_media_player == null)
			{
				m_media_player = new MediaPlayer();
				m_media_player.setOnCompletionListener(this);
			}
			m_run_buffer.run();
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
	
	public void onCompletion(MediaPlayer mp)
	{
		m_current_position = mp.getCurrentPosition();
		m_state = BUFFERING;
		m_run_buffer.run();
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
	
	public void scan()
	{
		if (m_scanned)
		{
			return;
		}
		if (m_state != PLAYING)
		{
			return;
		}
		if (!m_scanner.isConnected())
		{
			return;
		}
		m_scanned = true;
		m_scanner.scanFile(m_download_thread.m_shoutcast_file.file_path(), "audio/mpeg");
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
		m_media_player.stop();
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
