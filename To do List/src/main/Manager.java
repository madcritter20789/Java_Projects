package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Manager {

	private final Path File_Path = Path.of("tasks.json");
	private List<Task> tasks;
	
	public Manager()
	{
		this.tasks = loadTasks();
	}
	
	private List<Task> loadTasks()
	{
		List<Task> stored_tasks = new ArrayList<>();
		
		if(!Files.exists(File_Path))
		{
			return new ArrayList<>();	
		}
		
		try {
			String jsonContent = Files.readString(File_Path);
			String[] taskList = jsonContent.replace("[", "")
											.replace("]", "")
											.split("},");
			
			for(String taskJson : taskList) {
				if(!taskJson.endsWith("}")) {
					taskJson = taskJson + "}";
					stored_tasks.add(Task.fromJSON(taskJson));
				}
				else
				{
					stored_tasks.add(Task.fromJSON(taskJson));
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return stored_tasks;
	}
	
    public void saveTasks(){
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++){
            sb.append(tasks.get(i).Tojson());
            if (i < tasks.size() - 1){
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        String jsonContent = sb.toString();
        try {
            Files.writeString(File_Path, jsonContent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	public void addTask(String description)
	{
		Task new_task = new Task(description);
		tasks.add(new_task);
		System.out.println("Task added successfully (ID: " + new_task.getId() + ")");
	}
	
	public void updateTask(String id, String new_description)
	{
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.updateDescription(new_description);
	}
	
	public void deleteTask(String id)
	{
		Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        tasks.remove(task);
	}
	
	public void markInProgressTask(String id)
	{
		Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.markInProgress();
	}
	
	public void markDOneTask(String id)
	{
		Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.markDone();
	}
	
    public void listTasks(String type){
        for (Task task : tasks){
            String status = task.getStatus().toString().strip();
            if (type.equals("All") || status.equals(type)){
                System.out.println(task.toString());
            }
        }
    }

    public Optional<Task> findTask(String id) {
        return tasks.stream().filter((task) -> task.getId() == Integer.parseInt(id)).findFirst();
    }
}
