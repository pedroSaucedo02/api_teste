package tarefa.java.controllers;

import java.net.URI; //importa a classe URI para construir e manipular http de novos recursos
import java.util.List; //importa a interface List para manipular onde a classe controller está localizada 

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import tarefa.java.models.Task;
import tarefa.java.Services.TaskService;

@RestController 
@RequestMapping ("/task")
@Validated 

public class TaskController{

    @Autowired 
    private TaskService taskService;

@GetMapping("/{id}")
public ResponseEntity<Task> findByuserId(@PathVariable Long Id){
    Task obj = this.taskService.findById(Id);
    return ResponseEntity.ok().body(obj);
}
    @GetMapping ("/user/{userid}")
    public ResponseEntity<List<Task>> findByUserId(@PathVariable Long userId){
        List<Task> obj = this.taskService.finByUserId(userId);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{Id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
