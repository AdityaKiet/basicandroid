package ainaa.acup.dto;

public class DirectoryDto {
	private String Directoryname;
	private Integer size;
	private Integer noOfContents;
	private Boolean isDirectory;
	private String format;

	public String getDirectoryname() {
		return Directoryname;
	}

	public void setDirectoryname(String directoryname) {
		Directoryname = directoryname;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getNoOfContents() {
		return noOfContents;
	}

	public void setNoOfContents(Integer noOfContents) {
		this.noOfContents = noOfContents;
	}

	public Boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
