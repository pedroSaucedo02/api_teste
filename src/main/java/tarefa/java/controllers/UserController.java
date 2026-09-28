package tarefa.java.controllers;

import java.net.URI;

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

import tarefa.java.Services.UserService;
import tarefa.java.models.User;
import tarefa.java.models.User.CreateUser;
import tarefa.java.models.User.UpdateUser;

@RestController // Define a classe como um controlador REST que retorna respostas em JSON
@RequestMapping("/user") // Define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated // Ativa a verificação de validações nos parâmetros recebidos no controller
public class UserController {

    @Autowired
    private UserService userService;

    // Mapeia requisições HTTP GET na rota "/user/{id}"
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) { // Método para buscar usuário por ID capturado da URL
        User obj = this.userService.findById(id); // Invoca a busca do usuário através do ID recebido
        return ResponseEntity.ok().body(obj); // Retorna código HTTP 200 (OK) com o objeto User no corpo da resposta
    }

    // Mapeia requisições HTTP POST na rota base "/user" (criação de novo usuário)
    @PostMapping
    public ResponseEntity<Void> create(@Validated(CreateUser.class) @RequestBody User obj) { // Valida as regras de CreateUser e desserializa o JSON
        this.userService.create(obj); // Chama a camada de serviço para persistir o novo usuário no banco de dados
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest() // Obtém a rota da requisição atual
                .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Adiciona o ID do usuário gerando a URI final
        return ResponseEntity.created(uri).build(); // Retorna código HTTP 201 (Created) contendo a URL no cabeçalho Location
    }

    // Mapeia requisições HTTP PUT na rota base "/user/{id}" (atualização do usuário)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Validated(UpdateUser.class) @RequestBody User obj, @PathVariable Long id) { // Aplica a regra de UpdateUser e recebe ID e JSON
        obj.setId(id); // Garante que o ID do objeto a ser atualizado corresponda ao ID informado na URL
        this.userService.update(obj); // Executa a atualização das informações do usuário no banco de dados
        return ResponseEntity.noContent().build(); // Retorna código HTTP 204 (No Content) indicando sucesso sem corpo de resposta
    }

    // Mapeia requisições HTTP DELETE na rota "/user/{id}" (exclusão do usuário)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { // Captura o ID da URL a ser deletado
        this.userService.delete(id); // Invoca o método de deleção na camada de serviço
        return ResponseEntity.noContent().build(); // Retorna código HTTP 204 (No Content) confirmando a exclusão
    }
}