//url base da API spring boot para buscar as tarefas do usuario de ID 1
const url = "http://localhost:8080/task/user/1";

//função responsavel por ocultar o icone de carregamento
function hideLoader(){
    //busca o elemento HTML com o id 'loading' e altera seu estilo de exibição para oculta-lo
    document.getElementById("loading").style.display ="nome";
}

//função responsavel por construir o html da tabela e preencher-lo com as tarefas
function show(task){
    //cria uma string contendo cabeçãrio da tabela utilizando template literais
    let tab =`
    <thead>
        <tr>
        <th scope="col">#</th>
        <th scope="col">Descrição</th>
        <th scope="col">Usuario</th>
        <th scope="col">User ID</th>
        </tr> 
    </thead>
    `;
}