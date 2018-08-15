package com.article.service.impl.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.article.dao.manager.ManagerUserMapper;
import com.article.entity.manager.ManagerUser;
import com.article.service.manager.ManagerUserService;

//
@Service
public class ManagerUserServiceImpl implements ManagerUserService {

	@Resource
	private ManagerUserMapper managerUserMapper;
	
	@Override
	public List<ManagerUser> listPageManagerUser(ManagerUser managerUser) {
		return managerUserMapper.listPageManagerUser(managerUser);
	}

	@Override
	public ManagerUser getByManagerUser(ManagerUser managerUser) {
		return managerUserMapper.getByManagerUser(managerUser);
	}

	@Override
	public int insertManagerUser(ManagerUser managerUser) {
		return managerUserMapper.insertManagerUser(managerUser);
	}

	@Override
	public int deleteManagerUser(String id) {
		return managerUserMapper.deleteManagerUser(id);
	}

	@Override
	public int updateManagerUser(ManagerUser managerUser) {
		return managerUserMapper.updateManagerUser(managerUser);
	}

}
