package com.trusthub.cobranca.application.service.usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.repository.UsuariosRepository;
import com.trusthub.cobranca.application.service.perfil.PerfilService;
import com.trusthub.cobranca.domain.dto.PerfilDTO;
import com.trusthub.cobranca.domain.entity.UsuarioEntity;
import com.trusthub.cobranca.domain.enums.Perfil;
import com.trusthub.cobranca.security.UserSS;

/**
 * 
 * @author alan.franco
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuariosRepository usuarioRepository;
	@Autowired
	private PerfilService perfilService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioEntity usuario = usuarioRepository.consultarUsuarioPorEmail(email);
		List<PerfilDTO> listaPerfilDTO = perfilService.consultarPerfil(usuario.getId());
		Set<Perfil> perfis = new HashSet<>();
		listaPerfilDTO.forEach(perfilDTO -> {
			perfis.add(Perfil.toEnum(perfilDTO.getId()));
		});
		
		if(usuario.getId() == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getIdEmpresa(), perfis);
	}

}
