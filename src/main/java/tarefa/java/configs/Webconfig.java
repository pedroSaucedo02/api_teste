package tarefa.java.configs;

import org.springframework.context.annotation.Configuration;// importa a anotação de configuracao do spring container
import org.springframework.web.servlet.config.annotation.CorsRegistry;//importa a classe responsavel por registrar as regras do CORS
import org.springframework.web.servlet.config.annotation.EnableWebMvc;//importa a anotacao que habilita os recursos do spring web MVC
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;//importa a interface de customização do Spring MVC

@Configuration //indica que essa classe possui configurações do Beans que devem ser inicializada com o spring Ioc
@EnableWebMvc // importa a ativa o suporte básico as requisições e controladores Web MVC do spring
public class Webconfig implements WebMvcConfigurer{// classe de configuração que implementa  o contrato de  customização
    
    @Override //sobreescreve o método padrao de mapeamento CORS padrao da interface WebMvcConfigures
    public void addCorsMappings(CorsRegistry registry){//método incdicado pelo spring para registrar as regras do Cors
        registry.addMapping("/**");//libera qualquer rota da API(Coringa /**) para aceitar chamada externas 
    }
}
