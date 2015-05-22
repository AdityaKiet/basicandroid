package ainaa.acup.dto;

import java.io.Serializable;

public class FileExplorerDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Boolean isDirectory;
	private Integer size;
	private Boolean isHidden;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsDirectory() {
		return isDirectory;
	}
	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	
	
}
