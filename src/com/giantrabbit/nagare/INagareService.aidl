package com.giantrabbit.nagare;

interface INagareService
{
	void download(String url);
	String errors();
	String file_name();
	long position();
	void stop();
	int state();
}