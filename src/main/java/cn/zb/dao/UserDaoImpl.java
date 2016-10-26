package cn.zb.dao;

import cn.zb.commons.db.jdbc.JdbcTemplate;
import cn.zb.commons.db.util.DaoUtil;
import cn.zb.model.User;
import cn.zb.commons.util.ApiLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * user dao implementation.
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{

    private static final String INSERT_USER = "insert into user (uid, nickname) values (?,?)";
    private static final String UPDATE_USER = "update user set nickname=? where uid=? ";
    private static final String SELECT_USER = "select uid, nickname from user where uid=?";
    private static final String SELECT_MULTI_USERS = "select uid, nickname from user where uid in ";

    @Resource(name="userJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(User user) {
        return jdbcTemplate.update(INSERT_USER, new Object[]{user.getUid(), user.getNickname()}) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(long uid, User user) {
        if(user == null ){
            ApiLogger.info(String.format("update user with zero changed fiedls, uid=%s", uid));
            return true;
        }
        return jdbcTemplate.update(UPDATE_USER, new Object[]{ user.getNickname(),user.getUid()}) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User select(long uid) {
        return (User)jdbcTemplate.query(SELECT_USER, new Long[]{uid}, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return extractUser(rs);
                }
                System.out.println("getUser false:" + uid);
                return null;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, User> select(Long[] uids) {
        if(uids == null || uids.length == 0){
            return Collections.EMPTY_MAP;
        }
        final Map<Long, User> users = new HashMap<Long, User>();
        String sql = DaoUtil.createMutiGetSql(SELECT_MULTI_USERS, uids.length);
        jdbcTemplate.query(sql, uids, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User u = extractUser(rs);
                if(u != null){
                    users.put(u.getUid(), u);
                }
                return u;
            }
        });
        return users;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private User extractUser(ResultSet rs){
        User u = new User();
        try {
            u.setUid(rs.getLong("uid"));
            u.setNickname(rs.getString("nickname"));
            return u;
        } catch (SQLException e) {
            ApiLogger.error("Error: when extract user from db", e);
        }
        return null;
    }
}
