# Autobots — atualização de base (ATVIII)

Microsserviço REST em Java com Spring Boot para gestão de lojas de manutenção veicular e autopeças, contemplando o CRUD completo das entidades da atualização de base (`Empresa`, `Usuario`, `Credencial`, `Mercadoria`, `Servico`, `Veiculo`, `Venda`, `Documento`, `Email`, `Endereco`, `Telefone`), perfis de usuário (`CLIENTE`, `FORNECEDOR`, `FUNCIONARIO`) e hipermídia HATEOAS.

## Requisitos e execução

- Java 17 ou superior
- Maven Wrapper (`automanager/mvnw.cmd`)

PowerShell:

```powershell
cd automanager
./mvnw.cmd spring-boot:run
```

O serviço inicia por padrão na porta `8080` com banco H2 em memória (ou MySQL, caso configurado em `application.properties`) e carrega registros iniciais via `CommandLineRunner` (`AutomanagerApplication`).

## Principais rotas

| Entidade | Método | Rota | Operação |
| --- | --- | --- | --- |
| Empresa | GET | `/empresas/listar` | Lista todas as empresas |
| Empresa | GET | `/empresas/visualizar/{id}` | Visualiza empresa por ID |
| Empresa | POST | `/empresas/cadastrar` | Cadastra nova empresa |
| Empresa | PUT | `/empresas/atualizar/{id}` | Atualiza empresa existente |
| Empresa | DELETE | `/empresas/deletar/{id}` | Remove empresa |
| Usuário | GET | `/usuarios/listar` | Lista todos os usuários |
| Usuário | GET | `/usuarios/visualizar/{id}` | Visualiza usuário por ID |
| Usuário | POST | `/usuarios/cadastrar` | Cadastra novo usuário |
| Usuário | POST | `/usuarios/cadastrar/empresa/{idEmpresa}` | Cadastra usuário vinculado a uma empresa |
| Usuário | PUT | `/usuarios/atualizar/{id}` | Atualiza usuário existente |
| Usuário | PUT | `/usuarios/vincular/{idUsuario}/empresa/{idEmpresa}` | Vincula usuário a uma empresa |
| Usuário | PUT | `/usuarios/desvincular/{idUsuario}/empresa/{idEmpresa}` | Desvincula usuário de uma empresa |
| Usuário | DELETE | `/usuarios/deletar/{id}` | Remove usuário |
| Credencial | GET | `/credenciais/listar` | Lista credenciais ativas |
| Credencial | GET | `/credenciais/visualizar/{id}` | Visualiza credencial ativa por ID |
| Credencial | GET | `/credenciais/usuario/{idUsuario}/listar` | Lista credenciais ativas de um usuário |
| Credencial | POST | `/credenciais/cadastrar/{idUsuario}/{tipoCredencial}` | Cadastra credencial (`usuariosenha` ou `codigobarra`) |
| Credencial | PUT | `/credenciais/atualizar/{id}` | Atualiza credencial |
| Credencial | DELETE | `/credenciais/deletar/{id}` | Inativa credencial |
| Mercadoria | GET | `/mercadorias/listar` | Lista todas as mercadorias |
| Mercadoria | GET | `/mercadorias/visualizar/{id}` | Visualiza mercadoria por ID |
| Mercadoria | GET | `/mercadorias/visualizar/empresa/{idEmpresa}` | Lista mercadorias de uma empresa |
| Mercadoria | GET | `/mercadorias/visualizar/usuario/{idUsuario}` | Lista mercadorias de um usuário |
| Mercadoria | POST | `/mercadorias/cadastrar` | Cadastra mercadoria |
| Mercadoria | POST | `/mercadorias/cadastrar/empresa/{idEmpresa}` | Cadastra mercadoria na empresa |
| Mercadoria | POST | `/mercadorias/cadastrar/usuario/{idUsuario}` | Cadastra mercadoria no usuário |
| Mercadoria | PUT | `/mercadorias/atualizar/{id}` | Atualiza mercadoria |
| Mercadoria | PUT | `/mercadorias/vincular/{idMercadoria}/empresa/{idEmpresa}` | Vincula mercadoria à empresa |
| Mercadoria | PUT | `/mercadorias/vincular/{idMercadoria}/usuario/{idUsuario}` | Vincula mercadoria ao usuário |
| Mercadoria | PUT | `/mercadorias/desvincular/{idMercadoria}/empresa/{idEmpresa}` | Desvincula mercadoria da empresa |
| Mercadoria | PUT | `/mercadorias/desvincular/{idMercadoria}/usuario/{idUsuario}` | Desvincula mercadoria do usuário |
| Mercadoria | DELETE | `/mercadorias/deletar/{id}` | Remove mercadoria |
| Serviço | GET | `/servicos/listar` | Lista todos os serviços |
| Serviço | GET | `/servicos/visualizar/{id}` | Visualiza serviço por ID |
| Serviço | GET | `/servicos/visualizar/empresa/{idEmpresa}` | Lista serviços de uma empresa |
| Serviço | GET | `/servicos/visualizar/venda/{idVenda}` | Lista serviços de uma venda |
| Serviço | POST | `/servicos/cadastrar` | Cadastra serviço |
| Serviço | POST | `/servicos/cadastrar/empresa/{idEmpresa}` | Cadastra serviço na empresa |
| Serviço | POST | `/servicos/cadastrar/venda/{idVenda}` | Cadastra serviço na venda |
| Serviço | PUT | `/servicos/atualizar/{id}` | Atualiza serviço |
| Serviço | PUT | `/servicos/vincular/{idServico}/empresa/{idEmpresa}` | Vincula serviço à empresa |
| Serviço | PUT | `/servicos/vincular/{idServico}/venda/{idVenda}` | Vincula serviço à venda |
| Serviço | PUT | `/servicos/desvincular/{idServico}/empresa/{idEmpresa}` | Desvincula serviço da empresa |
| Serviço | PUT | `/servicos/desvincular/{idServico}/venda/{idVenda}` | Desvincula serviço da venda |
| Serviço | DELETE | `/servicos/deletar/{id}` | Remove serviço |
| Veículo | GET | `/veiculos/listar` | Lista todos os veículos |
| Veículo | GET | `/veiculos/visualizar/{id}` | Visualiza veículo por ID |
| Veículo | GET | `/veiculos/usuario/{idUsuario}/listar` | Lista veículos de um usuário |
| Veículo | POST | `/veiculos/cadastrar` | Cadastra veículo |
| Veículo | PUT | `/veiculos/atualizar/{id}` | Atualiza veículo |
| Veículo | PUT | `/veiculos/vincular/{idVeiculo}/usuario/{idUsuario}` | Vincula veículo ao proprietário |
| Veículo | PUT | `/veiculos/desvincular/{idVeiculo}/usuario/{idUsuario}` | Desvincula veículo do proprietário |
| Veículo | DELETE | `/veiculos/deletar/{id}` | Remove veículo |
| Venda | GET | `/vendas/listar` | Lista todas as vendas |
| Venda | GET | `/vendas/visualizar/{id}` | Visualiza venda por ID |
| Venda | GET | `/vendas/visualizar/empresa/{idEmpresa}` | Lista vendas de uma empresa |
| Venda | GET | `/vendas/visualizar/usuario/{idUsuario}` | Lista vendas de um usuário |
| Venda | POST | `/vendas/cadastrar` | Cadastra venda |
| Venda | POST | `/vendas/cadastrar/empresa/{idEmpresa}` | Cadastra venda na empresa |
| Venda | POST | `/vendas/cadastrar/usuario/{idUsuario}/{tipoUsuario}` | Cadastra venda vinculada a cliente/funcionário |
| Venda | PUT | `/vendas/atualizar/{id}` | Atualiza venda |
| Venda | PUT | `/vendas/vincular/{idVenda}/empresa/{idEmpresa}` | Vincula venda à empresa |
| Venda | PUT | `/vendas/vincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}` | Vincula venda a cliente/funcionário |
| Venda | PUT | `/vendas/desvincular/{idVenda}/empresa/{idEmpresa}` | Desvincula venda da empresa |
| Venda | PUT | `/vendas/desvincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}` | Desvincula venda de cliente/funcionário |
| Venda | DELETE | `/vendas/deletar/{id}` | Remove venda |

## Testes

```powershell
cd automanager
./mvnw.cmd test
```
