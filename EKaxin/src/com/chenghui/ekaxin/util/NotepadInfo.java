package com.chenghui.ekaxin.util;

public class NotepadInfo {
	// Êý¾Ý¿âID
	public int str_id = -1;
	public String title = "";
	public String recordtime;
	public String clock = "";
	public String content = "";
	public String path = null;
	
	public String toString()
	{
		String result = "";
		
		result += this.str_id + "-";
		result += this.title + "-";
		result += this.recordtime + "-";
		result += this.clock + "-";
		result += this.content + "-";
		result += this.path + "-";
		return result;
	}
}
