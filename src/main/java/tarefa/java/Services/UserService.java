package tarefa.java.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tarefa.java.models.Task;
import tarefa.java.models.User;
import tarefa.java.repositories.TaskRepository;
import tarefa.java.repositories.UserRepository;

@Service 
public class UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Injeta repositórios no construtor
    public UserService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public User findById(Long Id) {
        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! ID: " + Id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional 
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);

        if (obj.getTasks() != null && !obj.getTasks().isEmpty()) {
            for (Task task : obj.getTasks()) {
                task.setUser(obj);
            }
            this.taskRepository.saveAll(obj.getTasks());
        }

        return obj;
    }

    @Transactional 
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    @Transactional
    public void delete(Long Id) {
        findById(Id);

        try {
            this.userRepository.deleteById(Id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há tarefas relacionadas");
        }
    }
}