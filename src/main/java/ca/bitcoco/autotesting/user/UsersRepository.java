package ca.bitcoco.autotesting.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface  UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(@Param("username") String username);

    User findByUsernameGoogle(@Param("username") String username);

    User findByUsernameFacebook(@Param("username") String username);

}
