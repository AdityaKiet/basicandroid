package ainaa.acup.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UbuntuReader {

	Properties props;
	File file;
	InputStream inputStream;
	
	
	public String getShutdown() throws IOException
	{
		return "poweroff";
	}
	
	public String getRestart() throws IOException
	{
		return "reboot";
	}
	
	public String getLock() throws IOException
	{
		return "gnome-screensaver-command -l";
	}
	
	public String getSleep() throws IOException
	{
		return "sudo pm-suspend";
	}
	
	public String getHibernate() throws IOException
	{
		return "sudo pm-hibernate";
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(inputStream != null)
		{
			inputStream.close();
		}
	}
}
