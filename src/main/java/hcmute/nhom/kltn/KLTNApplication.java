package hcmute.nhom.kltn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
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


/**
 * The type KLTN application.
 */
@SpringBootApplication
public class KLTNApplication {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    /**
     * Main.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SpringApplication.run(KLTNApplication.class, args);
    }

    /**
     * Runner command line runner.
     *
     * @param roleRepository  RoleRepository
     * @param passwordEncoder PasswordEncoder
     * @param userRepository  UserRepository
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner runner(RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                             UserRepository userRepository) {
        return args -> {
            // First time run application, create admin account
            Set<Role> roles = new HashSet<>();
            User user = userRepository.findByEmail(adminEmail).orElse(null);
            if (Objects.isNull(user)) {
                // Create user role
                Role userRole = new Role();
                userRole.setRoleName(RoleName.USER.name());
                userRole.setAdminFlag(0);
                userRole = roleRepository.save(userRole);
                // Create admin role
                Role adminRole = new Role();
                adminRole.setRoleName(RoleName.ADMIN.name());
                adminRole.setAdminFlag(1);
                adminRole = roleRepository.save(adminRole);
                // Add tp list role
                List<Role> collection = new ArrayList<>();
                collection.add(userRole);
                collection.add(adminRole);
                roles.addAll(collection);
                user = new User();
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode(adminPassword));
                user.setRoles(roles);
                user.setCreatedBy("admin");
                user.setModifiedBy("admin");
                userRepository.save(user);
            }
        };
    }
}
