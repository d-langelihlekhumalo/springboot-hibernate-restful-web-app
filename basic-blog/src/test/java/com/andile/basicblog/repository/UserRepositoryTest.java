package com.andile.basicblog.repository;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // JUnit test for saving a user
    @DisplayName("JUnit test for saving a user")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {
        // Given object setup
        User user = User.builder()
                .name("Erling Haaland")
                .email("erling.haaland@email.com")
                .password(passwordEncoder.encode("haaland"))
                .build();

        // When saving an object
        User savedUser = userRepository.save(user);

        // Then verify saved object
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    // Junit test to get all users
    @DisplayName("Junit test to get all users")
    @Test
    public void givenUserList_whenFindAll_thenUsersList() {
        // Given object setup
        User objUser1 = User.builder()
                .name("Erling Haaland")
                .email("erling.haaland@email.com")
                .password(passwordEncoder.encode("haaland"))
                .build();

        User objUser2 = User.builder()
                .name("Kevin De Bruyne")
                .email("kevin@email.com")
                .password(passwordEncoder.encode("bruyne"))
                .build();

        User objUser3 = User.builder()
                .name("Rodrigo Hernandez")
                .email("hernandez@email.com")
                .password(passwordEncoder.encode("rodri"))
                .build();

        User objUser4 = User.builder()
                .name("Bernardo Silva")
                .email("bernardo@email.com")
                .password(passwordEncoder.encode("silva"))
                .build();

        int numExistingUsers = userRepository.findAll().size();

        userRepository.save(objUser1);
        userRepository.save(objUser2);
        userRepository.save(objUser3);
        userRepository.save(objUser4);

        // When saving an object
        List<User> userList = userRepository.findAll();

        // Then verify saved object
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(numExistingUsers + 4);
    }

    // JUnit test to get user by id
    @DisplayName("JUnit test to get user by id")
    @Test
    public void givenUserObject_whenFindById_thenReturnUserObject() {
        // Given object setup
        User objUser = User.builder()
                .name("Kevin De Bruyne")
                .email("kevin@email.com")
                .password(passwordEncoder.encode("bruyne"))
                .build();

        userRepository.save(objUser);

        // When saving an object
        User userDB = userRepository.findById(objUser.getId()).get();

        // Then verify saved object
        assertThat(userDB).isNotNull();

    }

    // Junit test to get user by email
    @DisplayName("Junit test to get user by email")
    @Test
    public void givenUserEmail_whenExistsByEmail_thenReturnUser() {
        // Given object setup
        User objUser = User.builder()
                .name("Bernardo Silva")
                .email("bernardo@email.com")
                .password(passwordEncoder.encode("silva"))
                .build();

        userRepository.save(objUser);

        // When saving an object
        Boolean userExistsByEmail = userRepository.existsByEmail(objUser.getEmail());

        // Then verify saved object
        assertThat(userExistsByEmail).isNotNull();
        assertThat(userExistsByEmail).isTrue();
    }

    // Junit test to check if user exists by email
    @DisplayName("Junit test to check if user exists by email")
    @Test
    public void givenUserEmail_whenFindByEmail_thenReturnUser() {
        // Given object setup
        User objUser = User.builder()
                .name("Rodrigo Hernandez")
                .email("hernandez@email.com")
                .password(passwordEncoder.encode("rodri"))
                .build();

        userRepository.save(objUser);

        // When saving an object
        User userDB = userRepository.findByEmail(objUser.getEmail()).get();

        // Then verify saved object
        assertThat(userDB).isNotNull();
    }

    // JUnit test to update user
    @DisplayName("JUnit test to update user")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUserObject() {
        // Given object setup
        User objUser = User.builder()
                .name("Erling Haaland")
                .email("erling.haaland@email.com")
                .password(passwordEncoder.encode("haaland"))
                .build();

        userRepository.save(objUser);

        // When saving an object
        User userDB = userRepository.findById(objUser.getId()).get();
        userDB.setName("Julian Alvarez");
        userDB.setEmail("julian@email.com");
        userDB.setPassword(passwordEncoder.encode("alvarez"));
        User updatedUser = userRepository.save(userDB);

        // Then verify saved object
        assertThat(userDB).isNotNull();
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Julian Alvarez");

    }

    // JUnit test to delete user
    @DisplayName("JUnit test to delete user")
    @Test
    public void givenUserObject_whenDeleteUser_thenReturnRemoveUser() {
        // Given object setup
        User objUser = User.builder()
                .name("Erling Haaland")
                .email("erling.haaland@email.com")
                .password(passwordEncoder.encode("haaland"))
                .build();

        userRepository.save(objUser);

        // When saving an object
        userRepository.delete(objUser);
        Optional<User> user = userRepository.findById(objUser.getId());

        // Then verify saved object
        assertThat(user).isEmpty();

    }
}
