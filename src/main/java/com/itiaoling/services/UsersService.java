package com.itiaoling.services;

import org.springframework.stereotype.Service;

import com.itiaoling.models.User;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UsersService extends BaseService<User>{

	private static final String NAMESPACE = "com.xiaojiuwo.models.mapper.UsersMapper.";

	public List<User> findAllUsers(){
		return myBatisGeneralRepository.getSqlSession().selectList(NAMESPACE + "findAllUsers");
	}
	
	public User findById(long id){
		return myBatisGeneralRepository.getSqlSession().selectOne(NAMESPACE + "findById", id);
	}
	
	public boolean isUserExists(User user){
		int count = myBatisGeneralRepository.getSqlSession().selectOne(NAMESPACE + "isUserExists", user);
		return count > 0 ? true : false;
	}
	
	public int saveUser(User user){
		return myBatisGeneralRepository.getSqlSession().insert(NAMESPACE + "insert", user);
	}
	
	public int updateUser(User user){
		return myBatisGeneralRepository.getSqlSession().update(NAMESPACE + "updateUser", user);
	}
	
	public int deleteUserById(long id){
		return myBatisGeneralRepository.getSqlSession().delete(NAMESPACE + "deleteUserById", id);
	}
	
	
	public int deleteAllUsers(){
		return myBatisGeneralRepository.getSqlSession().delete(NAMESPACE + "deleteAllUsers");
	}
	

    public List<User> findByName(String name){
        User user = new User();
        user.setName(name);
        return myBatisGeneralRepository.getSqlSession().selectList(NAMESPACE + "findUsersByName", user);
    }
}
