package com.giantrabbit.nagare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.os.Environment;

public class ShoutcastFile
{
	Context m_context;
	String m_shoutcast_name;
	String m_file_name;
	int m_bitrate;
	long m_current_write_pos;
	long m_buffer_mark_pos = 0;
	boolean m_done = false;
	boolean m_notified_buffering_done = false;
	String m_errors = "";
	File m_file;
	File m_nagare_dir;
	
	public ShoutcastFile(Context context, URLConnection connection)
	{
		m_context = context;
		m_shoutcast_name = connection.getHeaderField("icy-name");
		m_bitrate = Integer.parseInt(connection.getHeaderField("icy-br"));
		build_file_name();
	}
	
	public void build_file_name()
	{
		Calendar now = new GregorianCalendar();
		m_file_name = m_shoutcast_name.replaceAll("[\\/:*?\"<>|]", "_");
		m_file_name += "-" + now.get(Calendar.YEAR) + now.get(Calendar.MONTH) + now.get(Calendar.DAY_OF_MONTH) + now.get(Calendar.HOUR_OF_DAY) + now.get(Calendar.MINUTE) + now.get(Calendar.SECOND) + ".mp3";
		m_nagare_dir = new File(Environment.getExternalStorageDirectory() + "/Nagare");
		m_nagare_dir.mkdirs();
		m_file = new File(m_nagare_dir.getAbsolutePath(), m_file_name);
	}
	
	public void done()
	{
		m_done = true;
	}

	public String errors()
	{
		return m_errors;
	}
	
	public String file_path()
	{
		return m_file.getAbsolutePath();
	}
	
	public void download(DownloadThread download_thread, InputStream input)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(m_file);
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = input.read(buffer)) != -1 && !m_done) {
				output.write(buffer, 0, numRead);
				m_current_write_pos += numRead;
			}
		}
		catch (Exception e)
		{
			m_errors += e.toString() + "\n";
		}
		done();
	}
	
	public void rebuffer()
	{
		m_buffer_mark_pos = m_current_write_pos;
		m_notified_buffering_done = false;
	}
}
