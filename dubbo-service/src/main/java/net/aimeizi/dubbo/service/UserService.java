package net.aimeizi.dubbo.service;

import net.aimeizi.dubbo.entity.User;

public interface UserService {

	/**
	 * ����User,���������
	 * @param user
	 * @return
	 */
	public User save(User user);
}
