package tarefa.java.controllers;

import java.net.URI;
import java.util.List;

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
@RequestMapping("/task")
@Validated 
public class TaskController {

    @Autowired 
    private TaskService taskService;

    // 1. Busca uma tarefa específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // 2. Busca todas as tarefas associadas a um ID de usuário (corrigido {userId})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findByUserId(@PathVariable Long userId) {
        List<Task> obj = this.taskService.findByUserId(userId); // Corrigido 'findByUserId'
        return ResponseEntity.ok().body(obj);
    }

    // 3. Criação de nova tarefa (corrigido para @PostMapping)
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) {
        this.taskService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    // 4. Atualização de tarefa (corrigido para @PutMapping)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }
    
    // 5. Exclusão de tarefa (corrigido para "/{id}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}