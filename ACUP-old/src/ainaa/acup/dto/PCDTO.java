package ainaa.acup.dto;

public class PCDTO {
	private String pc_name;
	private String pc_id;
	private String pc_ip;
	private String pc_last_connected_time;
	private String pin;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPc_name() {
		return pc_name;
	}

	public void setPc_name(String pc_name) {
		this.pc_name = pc_name;
	}

	public String getPc_id() {
		return pc_id;
	}

	public void setPc_id(String pc_id) {
		this.pc_id = pc_id;
	}

	public String getPc_ip() {
		return pc_ip;
	}

	public void setPc_ip(String pc_ip) {
		this.pc_ip = pc_ip;
	}

	public String getPc_last_connected_time() {
		return pc_last_connected_time;
	}

	public void setPc_last_connected_time(String pc_last_connected_time) {
		this.pc_last_connected_time = pc_last_connected_time;
	}

	@Override
	public String toString() {
		return "PCDTO [pc_name=" + pc_name + ", pc_id=" + pc_id + ", pc_ip="
				+ pc_ip + ", pc_last_connected_time=" + pc_last_connected_time
				+ "]";
	}

}
