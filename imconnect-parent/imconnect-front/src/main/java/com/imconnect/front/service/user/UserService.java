package com.imconnect.front.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import com.imconnect.core.model.user.User;
import com.imconnect.core.repositories.user.UserRepository;
import com.imconnect.front.vo.UserListVO;


@Service
@Transactional
public class UserService{

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserListVO findAll(int page, int maxResults) {
		
		Page<User> result = executeQueryFindAll(page, maxResults);

		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindAll(lastPage, maxResults);
		}

		return buildResult(result);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	@Secured("ROLE_ADMIN")
	public void delete(Long id) {
		userRepository.delete(id);
	}

	@Transactional(readOnly = true)
	public UserListVO findByNameLike(int page, int maxResults, String name) {
		Page<User> result = executeQueryFindByName(page, maxResults, name);

		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindByName(lastPage, maxResults, name);
		}

		return buildResult(result);
	}

	private boolean shouldExecuteSameQueryInLastPage(int page, Page<User> result) {
		return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
	}

	private Page<User> executeQueryFindAll(int page, int maxResults) {
		final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

		return userRepository.findAll(pageRequest);
	}

	private Sort sortByNameASC() {
		return new Sort(Sort.Direction.ASC, "name");
	}

	private UserListVO buildResult(Page<User> result) {
		return new UserListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
	}

	private Page<User> executeQueryFindByName(int page, int maxResults, String name) {
		final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

		//TODO
		return null;//userRepository.findByPseudo(pageRequest, "%" + name + "%");
	}

	private boolean isUserAfterOrOnLastPage(int page, Page<User> result) {
		return page >= result.getTotalPages() - 1;
	}

	private boolean hasDataInDataBase(Page<User> result) {
		return result.getTotalElements() > 0;
	}
}
