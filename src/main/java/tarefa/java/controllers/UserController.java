package tarefa.java.controllers;


import java.net.URI; //importa a classe URI para construir e manipular http de novos recursos

import org.springframework.beans.factory.annotation.Autowired; //injecao automatica do spring
import org.springframework.http.ResponseEntity; //importa a classe para montar a resposta http completa(status, headers e corpo)
import org.springframework.validation.annotation.Validated;//importa anotacao para habilitar suporte a validacao de controller
import org.springframework.web.bind.annotation.DeleteMapping;//mapeia requisicoes do tipo GET
import org.springframework.web.bind.annotation.GetMapping;//mapeia variaveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; //mapeia requisicoes do tipo POST
import org.springframework.web.bind.annotation.PutMapping;// mapeia requisicao do tipo PUT
import org.springframework.web.bind.annotation.RequestBody;  //converste ibhetis HSIb en ibhetos JAVA
import org.springframework.web.bind.annotation.RequestMapping;//importa anotacoao para definir o caminho/rota das do controllers
import org.springframework.web.bind.annotation.RestController;// importa anotacao que define esta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // importa utilitarios para gerar URI da requisicao atual dinamicamente

import tarefa.java.models.User;
import tarefa.java.models.User.CreaterUser;
import tarefa.java.models.User.updateUser;
import tarefa.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;



@RestController //define a classe como um controlador REST que retorna respostas em JSON
@RequestMapping ("/user") // define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated //ativa a verificaçã de validações nos parametros recebidos no controller


public class UserController {
    @Autowired
    private UserService UserService;
    
    @GetMapping("/{id}")//mapeia a requisicoes HTTP GET na rota "/User/{id}" 
    public  ResponseEntity<User> findById(@PathVariable Long Id){//metodo para buscar usuario por id capturado da URL
        User obj=this.UserService.findById(Id);//invoca a busca do usuario atraves do ID recebido
        return  ResponseEntity.ok().body(obj) //retorna código HTTP 200(ok) com o obj User no corpo da resposta
    } // fim do método findBuId

    @PostMapping 
    public  ResponseEntity<Void> create(@Validated  (CreateUser.class) @RequestBody  User obj){
        this.UserService.creater(obj);
            URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(obj.getId()).toUri();
                    return ResponseEntity.created(url).build();
      }











}
