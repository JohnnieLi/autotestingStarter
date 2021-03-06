package ca.bitcoco.autotesting.user;

import ca.bitcoco.autotesting.core.CoreRepositoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends CoreRepositoryServiceImpl<UsersRepository, User, Long> implements UserService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        super(usersRepository);
        this.passwordEncoder = passwordEncoder;
    }


    public User addTest() {
        User user = new User();
        user.setUsername("tester2");
        String pass = "testpass";
        String encodedPass = this.passwordEncoder.encode(pass);
        user.setPassword(encodedPass);
        return this.create(user);
    }


    @Override
    public void login(User user) {

    }

    @Override
    public User findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public User findByGoogle(String username) {
        return this.repository.findByUsernameGoogle(username);
    }


    @Override
    public User findByFacebook(String username) {
        return this.repository.findByUsernameFacebook(username);
    }




}
