package com.giantrabbit.nagare;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import android.net.Uri;


public class PlsFile implements PlayListFile
{
	public Uri m_uri;
	public String m_errors = "";
	public long m_number_of_entries;
	public PlayList m_play_list;
	
	public PlsFile(Uri uri)
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
			String[] split_result;
			String var_name;
			String var_value;
			boolean header_found = false;
			while ((line = line_number_reader.readLine()) != null)
			{
				if (line.trim().equals(""))
				{
					continue;
				}
				if (!header_found)
				{
					if (line.trim().equals("[playlist]"))
					{
						header_found = true;
					}
					continue;
				}
				split_result = line.split("=", 2);
				if (split_result.length != 2)
				{
					continue;
				}
				var_name = split_result[0].trim().toLowerCase();
				var_value = split_result[1].trim();
				
				if (var_name.equals("numberofentries"))
				{
					m_number_of_entries = Long.parseLong(var_value);
					continue;
				}
				
				if (var_name.startsWith("file"))
				{
					m_play_list.add(var_value);
				}
			}
		} 
		catch (Exception e)
		{
			m_errors += "Error parsing pls file '" + file.getAbsolutePath() + "': " + e.toString() + "\n";
		}
		if (m_play_list.isEmpty())
		{
			m_errors += "The pls file '" + file.getAbsolutePath() + "' doesn't seem to define any files.\n";
		}
	}
	
	public PlayList play_list()
	{
		return m_play_list;
	}
}
