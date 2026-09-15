package tarefa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tarefa.java.models.Task;
import tarefa.java.models.User;
import tarefa.java.repositories.TaskRepository;

@Service 


public class TaskService  {
    
    @Autowired 
    private TaskRepository taskRepository;
    @Autowired 
    private UserService userservice;
    


}
