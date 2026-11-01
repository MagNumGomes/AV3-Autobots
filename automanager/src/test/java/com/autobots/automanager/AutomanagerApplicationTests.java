package com.autobots.automanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AutomanagerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoadsAndListarEndpointsReturnHateoasLinks() throws Exception {
		mockMvc.perform(get("/empresas/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].razaoSocial").value("Car service toyota ltda"))
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/empresas/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.empresas.href").exists());

		mockMvc.perform(get("/usuarios/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/usuarios/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.usuarios.href").exists());

		mockMvc.perform(get("/credenciais/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/credenciais/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.credenciais.href").exists());

		mockMvc.perform(get("/mercadorias/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/mercadorias/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.mercadorias.href").exists());

		mockMvc.perform(get("/servicos/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/servicos/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.servicos.href").exists());

		mockMvc.perform(get("/veiculos/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/veiculos/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.veiculos.href").exists());

		mockMvc.perform(get("/vendas/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].links[0].rel").value("self"));

		mockMvc.perform(get("/vendas/visualizar/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.vendas.href").exists());
	}

	@Test
	void crudCompletoEntidadesEAssociacoes() throws Exception {
		// 1. Empresa CRUD
		String novaEmpresaJson = """
				{
				  "razaoSocial": "Volkswagen Service Brasil Ltda",
				  "nomeFantasia": "VW AutoPeças e Manutenção",
				  "endereco": {
				    "estado": "SP",
				    "cidade": "São Bernardo do Campo",
				    "bairro": "Assunção",
				    "rua": "Via Anchieta",
				    "numero": "100",
				    "codigoPostal": "09823-999"
				  },
				  "telefones": [
				    { "ddd": "011", "numero": "40028922" }
				  ]
				}
				""";
		mockMvc.perform(post("/empresas/cadastrar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(novaEmpresaJson))
				.andExpect(status().isCreated());

		mockMvc.perform(put("/empresas/atualizar/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nomeFantasia\": \"VW Service Atualizado\"}"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/empresas/visualizar/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nomeFantasia").value("VW Service Atualizado"))
				.andExpect(jsonPath("$.razaoSocial").value("Volkswagen Service Brasil Ltda"));

		// 2. Usuario CRUD + associação a empresa
		String novoUsuarioJson = """
				{
				  "nome": "Carlos Cliente",
				  "nomeSocial": "Carlos",
				  "perfis": ["CLIENTE"],
				  "telefones": [{ "ddd": "012", "numero": "999998888" }],
				  "endereco": {
				    "estado": "SP",
				    "cidade": "São José dos Campos",
				    "bairro": "Centro",
				    "rua": "Rua XV de Novembro",
				    "numero": "50",
				    "codigoPostal": "12210-000"
				  },
				  "documentos": [{ "tipo": "CPF", "numero": "99988877766" }],
				  "emails": [{ "endereco": "carlos@cliente.com" }],
				  "credenciais": [{ "nomeUsuario": "carloscliente", "senha": "abc" }]
				}
				""";
		mockMvc.perform(post("/usuarios/cadastrar/empresa/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(novoUsuarioJson))
				.andExpect(status().isCreated());

		mockMvc.perform(put("/usuarios/atualizar/4")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nomeSocial\": \"Carlos Silva\"}"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/usuarios/visualizar/4"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nomeSocial").value("Carlos Silva"));

		// 3. Credencial CRUD (usuariosenha e codigobarra)
		mockMvc.perform(post("/credenciais/cadastrar/4/codigobarra")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"codigo\": 987654321}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/credenciais/usuario/4/listar"))
				.andExpect(status().isOk());

		// 4. Mercadoria CRUD + associação
		mockMvc.perform(post("/mercadorias/cadastrar/empresa/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\": \"Filtro de Óleo\", \"quantidade\": 15, \"valor\": 45.0, \"descricao\": \"Filtro original\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(put("/mercadorias/atualizar/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"valor\": 55.0}"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/mercadorias/visualizar/empresa/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].valor").value(55.0));

		// 5. Servico CRUD + associação
		mockMvc.perform(post("/servicos/cadastrar/empresa/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\": \"Troca de Óleo\", \"valor\": 80.0, \"descricao\": \"Troca completa\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(put("/servicos/atualizar/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"valor\": 90.0}"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/servicos/visualizar/empresa/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].valor").value(90.0));

		// 6. Veiculo CRUD + vinculação ao usuário
		mockMvc.perform(post("/veiculos/cadastrar")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tipo\": \"HATCH\", \"modelo\": \"Polo\", \"placa\": \"XYZ-1234\", \"proprietario\": {\"id\": 4}}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/veiculos/usuario/4/listar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].modelo").value("Polo"));

		// 7. Venda CRUD + vinculação
		String novaVendaJson = """
				{
				  "identificacao": "VENDA-VW-001",
				  "cliente": { "id": 4 },
				  "funcionario": { "id": 1 },
				  "veiculo": { "id": 2 },
				  "mercadorias": [{ "id": 3 }],
				  "servicos": [{ "id": 5 }]
				}
				""";
		mockMvc.perform(post("/vendas/cadastrar/empresa/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(novaVendaJson))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/vendas/visualizar/empresa/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].identificacao").value("VENDA-VW-001"));

		mockMvc.perform(get("/vendas/visualizar/usuario/4"))
				.andExpect(status().isOk());

		// 8. Exclusões
		mockMvc.perform(delete("/vendas/deletar/3")).andExpect(status().isOk());
		mockMvc.perform(delete("/veiculos/deletar/2")).andExpect(status().isOk());
		mockMvc.perform(delete("/servicos/deletar/5")).andExpect(status().isOk());
		mockMvc.perform(delete("/mercadorias/deletar/3")).andExpect(status().isOk());
		mockMvc.perform(delete("/credenciais/deletar/5")).andExpect(status().isOk());
		mockMvc.perform(delete("/usuarios/deletar/4")).andExpect(status().isOk());
		mockMvc.perform(delete("/empresas/deletar/2")).andExpect(status().isOk());
	}
}
