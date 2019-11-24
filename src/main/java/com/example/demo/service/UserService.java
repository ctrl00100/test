package com.example.demo.service;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.Result;
import com.example.demo.entity.StatusCode;
import com.example.demo.pojo.User;
import com.example.demo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder encoder;
//login
	public User login(User user) {
		User username = userDao.findByUsername(user.getUsername());
		if (username != null && encoder.matches(user.getPassword(), username.getPassword())) {
			return username;
		}else {
			return null;
		}


	}




	//findAll
	public List<User> findAll() {
		return userDao.findAll();
	}
//findAll+分页
public Page<User> findAllByPage( int page, int size) {

	PageRequest pageRequest =  PageRequest.of(page-1, size);
	return userDao.findAll(pageRequest);
}
	// 条件查询+分页

	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}


	//条件查询

	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	//findById
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	//add
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
     //密码加密
		String newpassword = encoder.encode(user.getPassword());//加密后的密码
		user.setPassword(newpassword);
		user.setCreate_time(new Date());//注册日期
		user.setUpdate_time(new Date());//更新日期
		userDao.save(user);
	}

	//update
	public void update(User user) {


		String newpassword = encoder.encode(user.getPassword());//加密后的密码
		user.setPassword(newpassword);
		user.setUpdate_time(new Date());//更新日期

		userDao.save(user);
	}
	//updateBo
	public void updateBo(String id,boolean bo ) {

//
//		String newpassword = encoder.encode(user.getPassword());//加密后的密码
//		user.setPassword(newpassword);
//		user.setUpdate_time(new Date());//更新日期


		userDao.updateBo(id ,bo);
	}

	//delete
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	//动态条件构建

	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                //
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                //
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                //
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                //
                if (searchMap.get("username")!=null && !"".equals(searchMap.get("username"))) {
                	predicateList.add(cb.like(root.get("username").as(String.class), "%"+(String)searchMap.get("username")+"%"));
                }

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public void updateUser(User user) {
		String id = user.getId();
		String email = user.getEmail();
		String nicknaem = user.getNickname();
		String username = user.getUsername();
		Date a = new Date();
		userDao.updateuser(id,email,nicknaem,username,a);
	}
}
