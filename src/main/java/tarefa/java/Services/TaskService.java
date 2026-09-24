//pacote onde está a classe de serviço no projeto
package tarefa.java.Services;

//importa List da biblioteca padrao do java para manipular colecoes de objetos
import java.util.List;
//importa optional, usado para tratar valores que podem nao estar presentes (evite nullexeceptionpointer)
import java.util.Optional;

//importa a anotacao do spring para a injeção automatica de dependencias
import org.springframework.beans.factory.annotation.Autowired;
//importa a anotacao que define essa classe como um componente de servico gerenciado pelo spring
import org.springframework.stereotype.Service;
//importa a anotacao para gerenciar transacoes no banco de dados (garante atomicidade na operacao)
import org.springframework.transaction.annotation.Transactional;

//importa o models.task
import tarefa.java.models.Task;
//importa o models.User
import tarefa.java.models.User;
//importa a interface do repositorio responsavel pelas operacoes no banco de dados
import tarefa.java.repositories.TaskRepository;

//anotacao que indica para o spring que essa classe contem as regras de negocios
@Service 
public class TaskService  {
    //injeta automaticamente a instancia do taskRepository gerenciado pelo spring
    @Autowired 
    private TaskRepository taskRepository;

    //injeta autoaticamente a instancia do Userservice para validar o usuario
    @Autowired 
    private UserService userservice;

    //metodo para buscar task apartir do ID
    public Task findById(Long Id){
        //executa a busca do banco, retorna um optional contendo (ou nao) a task
        Optional<Task> task = this.taskRepository.findById(Id);
    
        //se a tarefa existir, retorna o objeto, se estiver vazia, lança i, RunTimeException
        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada! Id:" + Id + ",Tipo:" + Task.class.getName()
        ));
    }

    public List<Task> findByUserId(Long userId){
        List<Task> task = this.taskRepository.findByUser_Id(userId);
        return task;
    }


    //metodo para buscar todas as tarefas vinculadar a um determinado usuario
    public List<Task> finByUserId(Long UserId){

        //chama o UserService para garantir que o usuario existe no banco(lanca execoes se nao existir)
        this.userservice.findById(UserId);
    
        //Executa a busca customizada no repositorio filtrando pelo id do usuario
        List<Task> task = this.taskRepository.findByUser_Id(UserId);

        //retorna a lista de tarefas
        return task;
    }
        //garante que a criacao ocorra dentro de uma transação de banco de dados (rolback automatico se falhas)
        @Transactional   
        public Task create (Task obj){
            //valida se o usuario informado no objeto realmente existe no banco e recupera seus dados
            User user = this.userservice.findById(obj.getUser().getId());
        
            //define o ID como null para garantir que o JPA realize um inserção(INSERT) e não uma atualização
            obj.setId(null);
        
            //associa a entida User completa e validada a tarefa
            obj.setUser(user);

            //salva a nova tarefa no banco de dados e atualiza 'obj' com o ID gerado
            obj = this.taskRepository.save(obj);

            return obj;
        }
        //Garante que a atualização ocorra dentro de transação isolada no banco
        public Task update(Task obj){
            //Reaproveita o findByID para verificar se atarefa a ser atualizada existe realmente
            Task newObj = findById(obj.getId());

            //Atualiza apenas o campo descricao do objeto persistido com o novo valo
            newObj.setDescription(obj.getDescription());

            //Salva a alteração no banco de dados e retorna o objeto atualizado
            return  this.taskRepository.save(newObj);
        }

    //metodo para deletar uma tarefa pelo ID
    public void delete(long Id){
        //verifica se a tarefa existe antes de tentar deletar
        findById(Id);
        
        try{    
            //solicitar a remoção da tarefa no banco de dados pelo ID
            this.taskRepository.deleteById(Id);
        } catch (Exception e){
            //captura execções (como violações de chave estrangeira e lanca uma mensagem amigavel)
            throw new RuntimeException("Não é possivel excluir pois não há tarefas relacionadas");
        }
    }
}
