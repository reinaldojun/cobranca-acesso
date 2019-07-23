package com.trusthub.cobranca.application.util;


/**
 * Inteface que centraliza as as mensagens utilizadas no cobranca acesso
 * @author alan.franco
 */
public interface Mensagens {
	
	//BUSINESS
	public static final String BUSINESS_USUARIO_JA_CADASTRADO = "Email já cadastrado";
	public static final String BUSINESS_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	public static final String BUSINESS_ID_BASE_ID_TOKEN = "Erro ao validar id da base com id do token no método (UsuariosBusiness.validaIdBaseIdToken): ";
	public static final String BUSINESS_EMPRESA_JA_CADASTRADA = "Empresa já cadastrada";
	public static final String BUSINESS_NAO_HA_EMPRESA_CADASTRADA = "Não existe empresas Cadastradas";
	public static final String BUSINESS_CAPTURAR_PERFIS_USUARIO = "Erro ao capturar perfis do usuario no método (PerfilBusiness.getPerfisDoUsuario): ";
	public static final String BUSINESS_PERFIL_ESPERADO_PELA_URL = "Erro ao recuperar perfil pela url no método (PerfilBusiness.perfilEsperadoPelaUrl): ";
	public static final String BUSINESS_MAP_ROLES = "Erro ao buscar e converter para map as roles no método (RoleBusiness.getMapRoles): ";
	public static final String BUSINESS_TOKEN_AUTORIZADO = "Erro ao verificar se o token está autorizado (TokenBusiness.tokenAutorizado): ";
	public static final String BUSINESS_CADASTRAR_USUARIO = "Erro ao cadastrar usuario no método (UsuariosBusiness.cadastrarUsuario): ";
	public static final String BUSINESS_ALTERAR_DADOS_USUARIO = "Erro ao alterar dados do usuário no método (UsuariosBusiness.alterarDadosUsuario): ";
	public static final String BUSINESS_LISTAR_USUARIOS = "Erro ao listar usuários no método (UsuariosBusiness.listarUsuarios): ";
	public static final String BUSINESS_CONSULTAR_USUARIO_EMAIL = "Erro ao consultar usuario por email no método (UsuariosBusiness.consultarUsuarioPorEmail): ";
	public static final String BUSINESS_CONSULTAR_USUARIO_ID = "Erro ao consultar usuario por id no método (UsuariosBusiness.consultarUsuarioPorId): ";
	public static final String BUSINESS_ALTERAR_SENHA = "Erro ao alterar senha no método (UsuariosBusiness.alterarSenha): ";
	public static final String BUSINESS_RECUPERAR_SENHA = "Erro ao recuperar senha no método (UsuariosBusiness.recuperarSenha): ";
	public static final String BUSINESS_CONSULTAR_PERFILS_USUARIO = "Erro ao consultar perfis do usuario no método (UsuariosBusiness.getPerfisDoUsuario): ";
	public static final String BUSINESS_APAGAR_USUARIO_POR_ID = "Erro ao apagar usuário por id no método (UsuariosBusiness.apagarUsuarioPorId): ";
	public static final String BUSINESS_USUARIO_LOGADO = "Erro ao buscar usuário logado no método (UsuariosBusiness.usuarioLogado): ";
	
	//SERVICE
	public static final String SERVICE_LISTAR_USUARIOS = "Erro ao listar usuarios no método (UsuarioService.listarUsuarios): ";
	public static final String SERVICE_CONSULTAR_USUARIO_EMAIL = "Erro ao consultar usuario por email no método (UsuarioService.consultarUsuarioPorEmail): ";
	public static final String SERVICE_CONSULTAR_USUARIO_ID = "Erro ao consultar usuario por id no método (UsuarioService.consultarUsuarioPorId): ";
	public static final String SERVICE_APAGAR_USUARIO_ID = "Erro ao apagar usuario por id no método (UsuarioService.apagarUsuarioPorId): ";
	public static final String SERVICE_CADASTRAR_USUARIO = "Erro ao cadastrar usuario no método (UsuarioService.cadastrarUsuario): ";
	public static final String SERVICE_ALTERAR_USUARIO = "Erro ao alterar dados do usuário no método (UsuarioService.alterarDadosUsuarios): ";
	public static final String SERVICE_CADASTRAR_PERFIL = "Erro ao cadastrar perfil no método (PerfilService.cadastrarPerfil): ";
	public static final String SERVICE_APAGAR_PERFIL = "Erro ao apagar perfil no método (PerfilService.apagarPefil): ";
	public static final String SERVICE_CONSULTAR_PERFIL = "Erro ao consultar perfil no método (PerfilService.consultarPerfil): ";
	public static final String SERVICE_LISTAR_EMPRESAS = "Erro listar empresas no método (EmpresaService.listarEmpresas): ";
	public static final String SERVICE_CONSULTAR_EMPRESA_POR_ID = "Erro consultar empresa por id no método (EmpresaService.consultarEmpresaPorId): ";
	public static final String SERVICE_CONSULTAR_EMPRESA_POR_CNPJ = "Erro consultar empresa por cnpj no método (EmpresaService.consultarEmpresaPorCnpj): ";
	public static final String SERVICE_CADASTRAR_EMPRESA = "Erro ao cadastrar empresa no método (EmpresaService.cadastrarEmpresa): ";
	public static final String SERVICE_APAGAR_EMPRESA_ID = "Erro ao apagar empresa no método (EmpresaService.apagarEmpresaPorId): ";
	public static final String SERVICE_CONSULTAR_ROLE = "Erro ao consultar role no método (RoleService.consultarRoles): ";
	public static final String SERVICE_CONSULTAR_ROLE_URL = "Erro ao consultar role url no método (RoleUrlService.consultarRoleUrl): ";
	public static final String SERVICE_ENVIAR_EMAIL = "Erro ao enviar email no método (EmailService.enviarEmail): ";
	public static final String SERVICE_VALIDAR_RESPOSTA_ENVIAR_EMAIL = "Erro ao validar resposta do enviar email no método (EmailService.validarRespostaEnviarEmail): ";
	
	//REPOSITORY
	public static final String REPOSITORY_LOGIN = "Erro ao buscar dados de login no método (UsuarioRepository.login): ";
	public static final String REPOSITORY_LISTAR_USUARIOS = "Erro ao listar usuarios no método (UsuarioRepository.listarUsuarios): ";
	public static final String REPOSITORY_CADASTRAR_USUARIO = "Erro ao cadastrar usuario no método (UsuarioRepository.cadastrarUsuario): ";
	public static final String REPOSITORY_ALTERAR_USUARIO = "Erro ao alterar dados do usuario no método (UsuarioRepository.alterarDadosUsuarios): ";
	public static final String REPOSITORY_ALTERAR_SENHA = "Erro ao alterar senha do usuario no método (UsuarioRepository.alterarSenha): ";
	public static final String REPOSITORY_CONSULTAR_USUARIO_EMAIL = "Erro ao consultar usuario por email no método (UsuarioRepository.consultarUsuarioPorEmail): ";
	public static final String REPOSITORY_CONSULTAR_USUARIO_ID = "Erro ao consultar usuario por id no método (UsuarioRepository.consultarUsuarioPorEmail): ";
	public static final String REPOSITORY_APAGAR_USUARIO_ID = "Erro ao apagar usuario por id no método (UsuarioRepository.apagarUsuarioPorId): ";
	public static final String REPOSITORY_ERRO_CADASTRAR_USUARIO = "Erro ao cadastrar usuario ";
	public static final String REPOSITORY_CADASTRAR_PERFIL = "Erro ao cadastrar perfil no método (PerfilRepository.cadastrarPerfil): ";
	public static final String REPOSITORY_APAGAR_PERFIL = "Erro ao apagar perfil no método (PerfilRepository.apagarPefil): ";
	public static final String REPOSITORY_CONSULTAR_PERFIL = "Erro ao consultar perfil no método (PerfilRepository.consultarPerfil): ";
	public static final String REPOSITORY_LISTAR_EMPRESAS = "Erro ao listar empresas no método (EmpresaRepository.listarEmpresas): ";
	public static final String REPOSITORY__CONSULTAR_EMPRESA_POR_ID = "Erro ao consultar empresa por id no método (EmpresaRepository.consultarEmpresaPorId): ";
	public static final String REPOSITORY__CONSULTAR_EMPRESA_POR_CNPJ = "Erro ao consultar empresa por cnpj no método (EmpresaRepository.consultarEmpresaPorCnpj): ";
	public static final String REPOSITORY_ERRO_CADASTRAR_EMPRESA = "Erro ao cadastrar empresa ";
	public static final String REPOSITORY_CADASTRAR_EMPRESA = "Erro ao cadastrar empresa no método (EmpresaRepository.cadastrarEmpresa): ";
	public static final String REPOSITORY_APAGAR_EMPRESA_ID = "Erro ao apagar empresa no método (EmpresaRepository.apagarEmpresaPorId): ";
	public static final String REPOSITORY_CONSULTAR_ROLES = "Erro ao consultar roles no método (RoleRepository.consultarRoles): ";
	public static final String REPOSITORY_CONSULTAR_ROLES_URL = "Erro ao consultar roles_url no método (RoleUrlRepository.consultarRoleUrl): ";
	
	//SECURITY
	public static final String  SECURITY_NO_AUTHORIZATION = "Authorization nulo ou vazio.";
	public static final String  SECURITY_TOKEN_INVALID = "Token Invalido.";
	public static final String  SECURITY_PERFIL_NO_AUTHORIZATION = "Perfil não autorizado.";
	
	
}
