package ru.kata.spring.boot_security.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.bootstrap.models.Role;
import ru.kata.spring.boot_security.bootstrap.models.User;
import ru.kata.spring.boot_security.bootstrap.repositories.RoleRepository;
import ru.kata.spring.boot_security.bootstrap.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        addDefaultUsers();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void edit(int id, User user) {
        User userToUpdated = getById(id);
        userToUpdated.setUsername(user.getUsername());
        userToUpdated.setSurname(user.getSurname());
        userToUpdated.setPassword(user.getPassword());
        userToUpdated.setAge(user.getAge());
        userToUpdated.setEmail(user.getEmail());
        userRepository.saveAndFlush(passwordCoder(userToUpdated));
    }

    public void save(User user) {
        userRepository.save(passwordCoder(user));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username %s not found", email));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToGrantedAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToGrantedAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRolename()))
                .collect(Collectors.toList());
    }

    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public void addDefaultUsers(){
        roleRepository.save(new Role(1, "ROLE_USER"));
        roleRepository.save(new Role(2, "ROLE_ADMIN"));

        Set<Role> role1 = new HashSet<>();
        role1.add(roleRepository.findById(1).orElse(null));

        Set<Role> role2 = new HashSet<>();
        role2.add(roleRepository.findById(1).orElse(null));
        role2.add(roleRepository.findById(2).orElse(null));

        User user1 = new User("murat", "samatov", "password",
                26, "user@bk.ru", role1);
        User user2 = new User( "karim", "samatov", "password",
                55, "admin@bk.ru", role2);
        save(user1);
        save(user2);
    }
}
