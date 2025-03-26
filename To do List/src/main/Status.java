package main;

public enum Status {
	
	To_Do("To do"),
	In_Progress("In Progress"),
	Comleted("Completed");
	
	private final String value;
	
	Status(String value){
		this.value= value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return value;
	}
		
}
