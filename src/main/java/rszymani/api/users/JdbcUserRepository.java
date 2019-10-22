package rszymani.api.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jtm;

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    public List<User> findAll(){
        String sql = "Select * FROM users ORDER BY birth_date";
        List<User> users = jtm.query(sql, new BeanPropertyRowMapper(User.class));
        return users;
    }

    public List<User> findByLastName(String lastName){
        String sql = "Select * FROM users WHERE lower(last_name)=lower(?)";
        List<User> users = jtm.query(sql, new Object[] {lastName}, new BeanPropertyRowMapper(User.class));
        return users;
    }
    public int save(User user) {
        String insertSql = "INSERT INTO users (First_name,last_name,birth_date,phone_no) " +
                "VALUES (?, ?, ?, ?)";
        int inserted = 0;
        try {
             inserted = jtm.update(insertSql,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirthDate(),
                    user.getPhoneNo()
            );
        }catch(DataIntegrityViolationException e){
            logger.error(e.getMessage());
        }
        return inserted;
    }
    public int count(){
        String sql = "Select count(*) FROM users";
        int counter = jtm.queryForObject(sql, new Object[] {}, Integer.class);
        return counter;
    }
    public int delete(String userId){
        String deleteSql = "DELETE FROM users WHERE id = ?";
        int deleted = jtm.update(deleteSql,new Object[]{userId});
        return deleted;
    }

    public User findOldestWithNumber(){
        String sql = "Select * FROM users WHERE phone_no IS NOT NULL ORDER BY birth_date FETCH FIRST 1 ROWS ONLY";
        List<User> users = jtm.query(sql, new Object[] {}, new BeanPropertyRowMapper(User.class));
        return users.get(0);
    };
}
