package com.giantrabbit.nagare;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import android.net.Uri;

public class M3UFile implements PlayListFile
{
	public Uri m_uri;
	public String m_errors = "";
	public long m_number_of_entries;
	public PlayList m_play_list;
	
	public M3UFile(Uri uri)
	{
		m_uri = uri;
		m_play_list = new PlayList();
	}
	
	public String errors()
	{
		return m_errors;
	}
	
	public void parse()
	{
		File file = new File(m_uri.getPath());
		FileReader file_reader;
		try
		{
			file_reader = new FileReader(file);
			LineNumberReader line_number_reader = new LineNumberReader(file_reader);
			String line;
			while ((line = line_number_reader.readLine()) != null)
			{
				if (line.trim().equals(""))
				{
					continue;
				}
				if (line.startsWith("#"))
				{
					continue;
				}
				m_play_list.add(line.trim());
			}
		} 
		catch (Exception e)
		{
			m_errors += "Error parsing m3u file '" + file.getAbsolutePath() + "': " + e.toString() + "\n";
		}
		if (m_play_list.isEmpty())
		{
			m_errors += "The m3u file '" + file.getAbsolutePath() + "' doesn't seem to define any files.\n";
		}
	}
	
	public PlayList play_list()
	{
		return m_play_list;
	}
}
