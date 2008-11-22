package com.giantrabbit.nagare;

import android.net.Uri;

public class PlayListFactory
{
	public static PlayListFile create(String mime_type, Uri uri)
	{
		if (mime_type.equals("audio/x-scpls"))
		{
			return new PlsFile(uri);
		}
		if (mime_type.equals("application/pls"))
		{
			return new PlsFile(uri);
		}
		if (mime_type.equals("audio/x-mpegurl"))
		{
			return new M3UFile(uri);
		}
		return null;
	}
}
