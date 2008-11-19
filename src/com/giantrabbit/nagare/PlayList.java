package com.giantrabbit.nagare;

import java.util.LinkedList;

public class PlayList
{
	public class Entry
	{
		public String m_url_string;
		
		public Entry(String url_string)
		{
			m_url_string = url_string;
		}
	}
	
	public LinkedList<Entry> m_entries;
	
	public PlayList()
	{
		m_entries = new LinkedList<Entry>();
	}
	
	public void add(String url_string)
	{
		Entry entry = new Entry(url_string);
		m_entries.addLast(entry);
	}
	
	public boolean isEmpty()
	{
		return m_entries.isEmpty();
	}
}
