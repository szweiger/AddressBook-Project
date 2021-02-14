package application;

public class Person {
	
	private int Id;
	private String name;
	private String street;
	private String city;
	private String zip;
	private String gender;
	
	public Person(int Id,String name,String gender,String street,String city,String zip) {
		super();
		this.Id = Id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.gender = gender;
	}
	
	// getter methods
	public int getId() {
		return Id;
	}
	public String getName() {
		return name;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getGender() {
		return gender;
	}
	
	
	// setter methods
	
	public void setId(int Id) {
		this.Id = Id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String toString() {
		return getId()+ " " + getName() + " " + getStreet() + " " + getCity() + " " + getGender() + " " + getZip();
	}
}
