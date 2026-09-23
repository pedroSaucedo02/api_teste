package tarefa.services;

//importa optional, usado para tratar valores que podem nao estar presentes (evite nullexeceptionpointer)
import java.util.Optional;

import  tarefa.java.models.User;
//importa a anotacao do spring para a injeção automatica de dependencias
import org.springframework.beans.factory.annotation.Autowired;
//importa a anotacao que define essa classe como um componente de servico gerenciado pelo spring
import org.springframework.stereotype.Service;
//importa a anotacao para gerenciar transacoes no banco de dados (garante atomicidade na operacao)
import org.springframework.transaction.annotation.Transactional;


//importa o models.task
import tarefa.java.models.Task;
//importa a interface do repositorio responsavel pelas operacoes no banco de dados
import tarefa.java.repositories.TaskRepository;
//importa a interface do repositorio responsavel pela operações no banco de dados
import tarefa.java.repositories.UserRepository;

//anotacao que indica no spring que essa classe contem as regras e negocios da entidade User

@Service 
public class UserService {

    private final TaskRepository taskRepository;
    @Autowired 
    private UserRepository userRepository;


    UserService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public User findById(Long Id){

     Optional<User> user = this.userRepository.findById(Id);

    
        return user.orElseThrow(()-> new RuntimeException(
            "Usuario não encontrado!" + Id + ", Tipo:" + User.class.getName()
        ));
    }
    @Transactional 
    public User create(User obj){
        
        obj.setId(null);

        obj = this.userRepository.save(obj);

        this.taskRepository.saveAll(null);

        return obj;
    }

    @Transactional 
    public User update(User obj){
        //Reaproveita o findByID para verificar se atarefa a ser atualizada existe realmente
        User newObj = findById(obj.getId());

        //Atualiza apenas o campo descricao do objeto persistido com o novo valo
        newObj.setPassword(obj.getPassword());

        //Salva a alteração no banco de dados e retorna o objeto atualizado
        return  this.userRepository.save(newObj);
    }

    //Método para deletar uma tarefa pelo Id
    public void delete(Long Id){
        //Verifica se a tarefa existe antes de tentar deletar
        findById(Id);

        try{
            //Solicita a remoção da tarefa no banco de dados pelo ID 
            this.userRepository.deleteById(Id);
        } catch (Exception e){
            //Capctura execções (como violações de chave estrangeira e lança uma mensagem amigável)
            throw new RuntimeException("Não é posspivel excuir pois não há tarefas relacionadas");
        }
    }
}

    


