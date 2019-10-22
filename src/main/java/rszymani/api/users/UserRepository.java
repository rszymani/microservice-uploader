package rszymani.api.users;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    List<User> findByLastName(String lastName);
    int save(User user);
    int count();
    int delete(String userId);
    User findOldestWithNumber();
}
