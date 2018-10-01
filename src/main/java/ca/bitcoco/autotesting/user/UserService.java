package ca.bitcoco.autotesting.user;

interface UserService {

    void login(User user);

    User findByUsername(String username);

    User findByGoogle(String username);

    User findByFacebook(String username);


}
