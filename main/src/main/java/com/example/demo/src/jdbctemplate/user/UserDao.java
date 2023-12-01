package com.example.demo.src.jdbctemplate.user;


import com.example.demo.src.jdbctemplate.user.model.GetUserRes;
import com.example.demo.src.jdbctemplate.user.model.PatchUserReq;
import com.example.demo.src.jdbctemplate.user.model.PostUserReq;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from USER";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("name")));
    }

    public List<GetUserRes> getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select * from USER where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("name")), getUsersByEmailParams);
    }

    public GetUserRes getUser(int userId) {
        String getUserQuery = "select * from USER where id = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("name")), getUserParams);
    }


    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into USER (name, email, password) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getEmail(),
                postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select LAST_INSERT_ID()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from USER where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update USER set name = ? where id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getName(), patchUserReq.getId()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    public int deleteUser(int userId) {
        String deleteUserNameQuery = "update USER set status = ? where id = ? ";
        Object[] deleteUserNameParams = new Object[]{"INACTIVE", userId};

        return this.jdbcTemplate.update(deleteUserNameQuery, deleteUserNameParams);
    }


}
