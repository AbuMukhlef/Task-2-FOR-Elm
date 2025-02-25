package presentation.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeModel implements Serializable {

	private static final long serialVersionUID = 1L;
// @JsonProperty(value = Access.READ_ONLY)
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "name")
	private String name;
	@JsonProperty(value = "gender")
	private String gender;
	@JsonProperty(value = "dob")
	private String dob;
	@JsonProperty(value = "phoneNumber")
	private String phoneNumber;
	@JsonProperty(value = "hobbies")
	private List<String> hobbies;

	public EmployeeModel() {
	}

	public EmployeeModel(int id, String name, String gender, String dob, String phoneNumber, List<String> hobbies) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.hobbies = hobbies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
