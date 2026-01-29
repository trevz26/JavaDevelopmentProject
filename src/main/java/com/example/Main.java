package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("First changes!");
        Scanner scanner = new Scanner(System.in);
        List<Task> taskList = new ArrayList<>();
        while (true) {
            System.out.println("Which command would you like to do? \n Out of the following options\n"); 
            String output = scanner.nextLine().toLowerCase();
            if(output.substring(0,4).strip().equals("add"))
            {
                String taskValue = output.substring(4);
                String[] inputs = taskValue.split(",");
                String name = inputs[0];
                String priorties = inputs[1];
                String dates = null;
                try
                {
                    dates = inputs[2];
                }
                catch(Exception e)
                {
                    e.getMessage();
                }
                finally{
                    Task newTask = inputs.length>2 ? new Task(UUID.randomUUID(), name, priorties, LocalDate.parse(dates)) : new Task(UUID.randomUUID(), name, priorties) ;  
                    taskList.add(newTask);
                    System.out.println("Added task ["+ newTask.getId() + "]: " + name+ "\n");
                }
                continue;
            }

            if(output.substring(0,4).equals("list"))
            {
                System.out.print("ID | STATUS | PRIORITY | DUE DATE | NAME\n");
                for(Task eachTask: taskList)
                {
                    System.out.print(eachTask.getId()+" |");
                    System.out.print(eachTask.getStatus()+"|");
                    System.out.print(eachTask.getPriorityLevel().toUpperCase()+"|");
                    System.out.print(eachTask.getDueDate()+"|");
                    System.out.print(eachTask.getName()+"|\n");

                }
                
                continue;
            }
            if(output.substring(0,5).strip().equals("done"))
            {
                String idParsed = output.substring(5);
                List<Task> taskFound = taskList.stream().filter(a -> (a.getId().equals(idParsed))).collect(Collectors.toList());
                System.out.println("Returned list size is: " + taskFound.size());

                if(!taskFound.isEmpty())
                {
                    if(!taskFound.get(0).setStatus("DONE"))
                    {
                        throw new IllegalArgumentException("Cannot set this list to DONE");
                    }
                    else
                    {
                        taskFound.get(0).setStatus("DONE");
                        System.out.println("Task " + idParsed + " marked as DONE");
                    }
                }
                else
                {
                    System.out.println("Impossible as there is no task with that id");
                }
                
                continue;
            }
            if(output.substring(0,7).strip().equals("delete"))
            {
                String idParsed = output.substring(7);
                List<Task> taskFound = taskList.stream().filter(a -> (a.getId().equals(idParsed))).collect(Collectors.toList());
                if(!taskFound.isEmpty())
                {
                    taskList.remove(taskFound.get(0));
                }
                else
                {
                    System.out.println("Impossible as there is no task with that id");
                }      
                continue;
            }
            else
            {
                System.out.println("You are in the help menu");
            }
        }
    }
}
class Task{
    private UUID id;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private String priorityLevel;
    private String name;
    public String status;

    public Task(UUID id,String name,  String priority, LocalDate date)
    {
        this.name = name;
        this.id = id;
        this.status = "open";
        this.createdAt = LocalDate.now();
        this.dueDate = date;
        this.priorityLevel = priority;
    }
    
    public Task(UUID id,String name, String priority)
    {
        this.name = name;
        this.id = id;
        this.createdAt = LocalDate.now();
        this.dueDate = null;
        this.status = "open";
        this.priorityLevel = priority;
    }
    public String getId()
    {
        return this.id.toString();
    }
    public String getName()
    {
        return this.name;
    }
    public LocalDate getDate()
    {
        return this.createdAt;
    }
    public LocalDate getDueDate()
    {
        return this.dueDate;
    }
    public String getPriorityLevel()
    {
        return this.priorityLevel;
    }
    public String getStatus()
    {
        return this.status;
    }
    public boolean setStatus(String value)
    {
        if(status.equals("OPEN"))
        {
            this.status = "OPEN";
            return true;
        }
        if(!status.equals("DONE"))
        {
            status = "DONE";
            return true;
        }
        return false;
    }
}