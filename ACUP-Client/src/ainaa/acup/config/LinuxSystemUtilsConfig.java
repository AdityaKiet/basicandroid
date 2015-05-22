package ainaa.acup.config;


import android.app.Application;

public class LinuxSystemUtilsConfig extends Application{

	
	public String getShutdown() 
	{
		return "poweroff";
	}
	
	public String getRestart() 
	{
		return "reboot";
	}
	
	public String getLock()
	{
		return "gnome-screensaver-command -l";
	}
	
	public String getSleep() 
	{
		return "sudo pm-suspend";
	}
	
	public String getHibernate()
	{
		return "sudo pm-hibernate";
	}
	
	
	
}
