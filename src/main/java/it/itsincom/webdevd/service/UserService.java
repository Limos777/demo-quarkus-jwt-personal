package it.itsincom.webdevd.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.itsincom.webdevd.persistence.UserRepository;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import it.itsincom.webdevd.web.model.CreateUserRequest;
import it.itsincom.webdevd.web.model.UpdateUserRequest;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Request;
import org.wildfly.security.password.interfaces.BCryptPassword;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private Request request;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @jakarta.inject.Inject
    public UserService(UserRepository userRepository, Request request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        String hash = BcryptUtil.bcryptHash(request.getPassword());
        ApplicationUser user = new ApplicationUser(
                request.getUsername(),
                hash,
                request.getRole(),
                request.getNome(),
                request.getCognome(),
                request.getIndirizzo()
        );

        userRepository.persist(user);

        return toUserResponse(user);

    }

    public UserResponse authenticate(String username, String password) {
        ApplicationUser user = userRepository.authenticate(username, password);
        if (user != null) {
            return toUserResponse(user);
        }
        return null;
    }

    public List<UserResponse> findAll() {
        List<ApplicationUser> allUsers = userRepository.findAll(Sort.by("username"))
                .list();
        List<UserResponse> result = new ArrayList<>();
        for (ApplicationUser user : allUsers) {
            result.add(toUserResponse(user));
        }
        return result;
    }

    private static UserResponse toUserResponse(ApplicationUser user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getNome(),
                user.getCognome(),
                user.getIndirizzo()
        );
    }

    public UserResponse getUserByUsername(String username) {
        return toUserResponse(userRepository.findByUsername(username));
    }

    @Transactional
    public boolean update(UpdateUserRequest user, int id) {
        String hash = BcryptUtil.bcryptHash(user.getPassword());
        int update = userRepository.update(
                "UPDATE ApplicationUser au " +
                        "SET au.username = :username," +
                        "au.nome = :nome," +
                        "au.cognome = :cognome," +
                        "au.indirizzo = :indirizzo," +
                        "au.password = :password " +
                        "WHERE au.id = :id",
                Parameters.with(
                        "username", user.getUsername())
                        .and("nome", user.getNome())
                        .and("cognome", user.getCognome())
                        .and("indirizzo", user.getIndirizzo())
                        .and("password", hash)
                        .and("id", id)
                );
        return update > 0;
    }
}
