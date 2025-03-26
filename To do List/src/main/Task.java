package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
	
    private static int lastId = 0;  // Static variable to keep track of the last ID assigned
    
	public int id;
	public String description;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public LocalDateTime completedAt;
	public Status status;
	
    // DateTimeFormatter for serializing/deserializing dates
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
	public Task(String description)
	{
		this.id = ++lastId;
		this.description = description;
		this.status = status.To_Do;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void markDone()
	{
		this.status = status.Comleted;
		this.updatedAt = LocalDateTime.now();
	}
	
	public void markInProgress()
	{
		this.status = status.In_Progress;
		this.updatedAt = LocalDateTime.now();
	}
	
	public Status getStatus() 
	{
		return status;
	}
	
	public void updateDescription( String description) 
	{
		this.description = description;
		this.updatedAt = LocalDateTime.now();
	}
	
	public String Tojson()
	{
		return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() + "\", \"status\":\"" + status.toString() + "\", \"created at\":\"" + createdAt.format(formatter) + "\", \"updated at\":\"" + updatedAt.format(formatter) + "\"}";
	}
	
	public static Task fromJSON(String json)
	{
		json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] json1 = json.split(",");

        String id = json1[0].split(":")[1].strip();
        String description = json1[1].split(":")[1].strip();
        String statusString = json1[2].split(":")[1].strip();
        String createdAtStr = json1[3].split("[a-z]:")[1].strip();
        String updatedAtStr = json1[4].split("[a-z]:")[1].strip();

        Status status = Status.valueOf(statusString.toUpperCase().replace(" ", "_"));

        Task task = new Task(description);
        task.id = Integer.parseInt(id);
        task.status = status;
        task.createdAt = LocalDateTime.parse(createdAtStr, formatter);
        task.updatedAt = LocalDateTime.parse(updatedAtStr, formatter);

        if (Integer.parseInt(id) > lastId) {
            lastId = Integer.parseInt(id);
        }

        return task;
	}
	@Override
	public String toString()
	{
		return "id: " + id + ", description: " + description.strip() + ", status: " + status.toString() + ", created at: " + createdAt.format(formatter)  + ", updated at: " + updatedAt.format(formatter);
	}
}
