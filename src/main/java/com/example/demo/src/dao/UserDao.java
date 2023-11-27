package com.example.demo.src.dao;


import com.example.demo.src.dto.GetUserRes;
import com.example.demo.src.dto.PatchUserReq;
import com.example.demo.src.dto.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from user";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")
                )
        );
    }

    public List<GetUserRes> getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select * from user where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")
                ),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userId) {
        String getUserQuery = "select * from user where user_id = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")),

                getUserParams);
    }


    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into user (name, email, password) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from user where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update user set name = ? where user_id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getName(), patchUserReq.getId()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    public int deleteUser(int userId) {

        String deleteUserNameQuery = "update user set status = ? where id = ? ";
        Object[] deleteUserNameParams = new Object[]{"INACTIVE", userId};

        return this.jdbcTemplate.update(deleteUserNameQuery, deleteUserNameParams);
    }


}
