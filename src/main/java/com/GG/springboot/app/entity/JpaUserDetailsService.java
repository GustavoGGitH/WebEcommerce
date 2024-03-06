package com.GG.springboot.app.entity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GG.springboot.app.dao.UsuarioRepository;

@Service("jpaDetailsService")

//no es necesario implementar ninguna interfaz ya que spring provee una interfaz propia para autenticar 
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	public UsuarioRepository usuarioDao;

	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Override
	// bajo la misma consulta vamos a obtener el usuario y los roles por tanto
	// decoramos con transactional
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioDao.findByUsername(username).orElse(null);

		if (usuario == null) {
			// envío un log a consola con el usuario no existente
			logger.error("Error: User does not exist : '" + username + "'");
			// lanzo una excepción de usuario no encontrado
			throw new UsernameNotFoundException("User " + username + " does not exist");
		}

		// Obtengo la lista de authorities

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : usuario.getRoles()) {
			logger.info(username, "Role:".contains(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			// envío un log a consola con el usuario no existente
			logger.error("Error: User : '" + username + "' does not have a role asigned");
			// lanzo una excepción de usuario no encontrado
			throw new UsernameNotFoundException("User " + username + "  does not have a role asigned");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), authorities);
	}

}
