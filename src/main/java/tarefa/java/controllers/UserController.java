package tarefa.java.controllers;


import java.net.URI; //importa a classe URI para construir e manipular http de novos recursos
import java.security.Provider.Service;

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

import tarefa.java.Services.UserService;
import tarefa.java.models.User;
import tarefa.java.models.User.CreateUser;
import tarefa.java.models.User.UpdateUser;
import tarefa.java.Services.UserService;
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
        return  ResponseEntity.ok().body(obj); //retorna código HTTP 200(ok) com o obj User no corpo da resposta
    } // fim do método findBuId

    @PostMapping //Mapeiai requisições HTTP POST na rota base "/user"(criação de novo usuario)
    public  ResponseEntity<Void> create(@Validated  (CreateUser.class) @RequestBody  User obj){//valida regra de createUser e desserializa o codigo
        this.UserService.create(obj);//chama a camada de serviço para persistir o novo usuario no banco de dados
            URI url = ServletUriComponentsBuilder.fromCurrentRequest() //obtem a rota da requisição atual
                    .path("/{id}").buildAndExpand(obj.getId()).toUri(); //adiciona o Id do usuario gerando o final do caminho da URI
                    return ResponseEntity.created(url).build(); //retorna código HTTPP 201(created) contendo a URL na cabeçalho location
      }

      @PutMapping("/{id}")//mapeia requisiçoes HTTP PUT na rota base "/user/{Id}"(atualização do usuario)
      public ResponseEntity<Void>Update(@Validated (UpdateUser.class)@RequestBody User obj, @PathVariable Long Id){//aplica a regra de updateUser e recebe Id e JSON
            obj.setId(Id);//garante que o Id do objeto a ser atualizado corrensponda ao ID informado no parametro da URL
        this.UserService.update(obj);//executa a atualizacao da senha do usuario no banco de dados
        return ResponseEntity.noContent().build();// retorna o código HTTP 204(no content) indicando sucesso sem corpo de resposta
      }

      @DeleteMapping ("/{id}")//mapeiai requisicao HTTP Delete na rota "/user/{Id}"(exclusao do usario)
      public ResponseEntity<Void> delete(@PathVariable Long Id){//captura o Id da URL a ser deletado
      this.UserService.delete(Id); // invoca o método de deleção ao serviço
      return ResponseEntity.noContent().build();//retorna código HTTP 204(no content) confirmando a exclusão
    }

 }









