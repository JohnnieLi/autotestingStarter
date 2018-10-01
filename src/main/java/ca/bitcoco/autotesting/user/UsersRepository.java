package ca.bitcoco.autotesting.user;


import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface  UsersRepository extends MongoRepository<User,String> {

    User findByUsername(@Param("username") String username);

    @Query("{authType:'google' })")
    User findByUsernameGoogle(@Param("username") String username);

    @Query("{authType:'facebook' })")
    User findByUsernameFacebook(@Param("username") String username);

}
