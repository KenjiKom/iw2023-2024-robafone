package iw20232024robafone.security;

import iw20232024robafone.backend.entity.Usuario;
import iw20232024robafone.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtils implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public SecurityUtils(UsuarioRepository userRepository){
        this.usuarioRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario applicationUser = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(applicationUser.getUsername())
                .password(applicationUser.getPassword())
                .roles(applicationUser.getRole())
                .build();
    }

}
