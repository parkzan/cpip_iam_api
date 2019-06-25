package co.prior.iam.module.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.model.PageableRequest;
import co.prior.iam.model.SortedModel;
import co.prior.iam.repository.IamMsUserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private final IamMsUserRepository iamMsUserRepository;
	
	public UserService(IamMsUserRepository iamMsUserRepository) {
		this.iamMsUserRepository = iamMsUserRepository;
	}
	
	public Page<IamMsUser> getUsers(PageableRequest request) {
		log.info("Service getUsers page: {}, size: {}", request.getPage(), request.getSize());
		
		int page = request.getPage() - 1;
		int size = request.getSize();
		Sort sort = Sort.unsorted();
		
		List<SortedModel> sortedList = request.getSortedList();
		if (sortedList != null) {
			for (SortedModel sortedModel : sortedList) {
				sort.and(Sort.by(sortedModel.getDirection(), sortedModel.getField()));
			}
		}
		Pageable records = PageRequest.of(page, size, sort);
		
		return this.iamMsUserRepository.findByIsDeleted("N", records);
	}
	
	public IamMsUser getUser(long userId) throws Exception {
		log.info("Service getUser userId: {}", userId);
		
		return this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, "N")
				.orElseThrow(() -> new Exception("user not found"));
	}
	
}
