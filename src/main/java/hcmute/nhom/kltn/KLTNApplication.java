package hcmute.nhom.kltn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import hcmute.nhom.kltn.enums.RoleName;
import hcmute.nhom.kltn.model.Role;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.RoleRepository;
import hcmute.nhom.kltn.repository.UserRepository;

@SpringBootApplication
public class KLTNApplication {

    public static void main(String[] args) {
        SpringApplication.run(KLTNApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                             UserRepository userRepository){
        return args -> {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleName.USER.name()).orElse(null);
            Role adminRole = roleRepository.findByName(RoleName.ADMIN.name()).orElse(null);
            User user = userRepository.findByEmail("admin@gmail.com").orElse(null);
            if(Objects.isNull(userRole)){
                userRole = new Role();
                userRole.setRoleName(RoleName.USER.name());
                userRole.setAdminFlag(0);
                userRole.setRemovalFlag(false);
                userRole = roleRepository.save(userRole);
            }
            if (Objects.isNull(adminRole)){
                adminRole = new Role();
                adminRole.setRoleName(RoleName.ADMIN.name());
                adminRole.setAdminFlag(1);
                adminRole.setRemovalFlag(false);
                adminRole = roleRepository.save(adminRole);
            }
            Collection collection = new ArrayList();
            collection.add(userRole);
            collection.add(adminRole);
            roles.addAll(collection);
            if(Objects.isNull(user)){
                user = new User();
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("123456@@admin"));
                user.setRemovalFlag(false);
                user.setRoles(roles);
                user = userRepository.save(user);
            }
        };
    }
}
