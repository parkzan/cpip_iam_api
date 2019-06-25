package co.prior.iam.module.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.model.PageableRequest;
import co.prior.iam.model.SortedModel;
import co.prior.iam.module.user.model.request.GetUsersRequest;
import co.prior.iam.module.user.model.response.GetUserResponse;
import co.prior.iam.module.user.model.response.IamMsUserPage;
import co.prior.iam.repository.IamMsUserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private final IamMsUserRepository iamMsUserRepository;
	
	public UserService(IamMsUserRepository iamMsUserRepository) {
		this.iamMsUserRepository = iamMsUserRepository;
	}
	
	public IamMsUserPage getUsers(GetUsersRequest request) {
		log.info("Service getUsers systemId: {}", request.getSystemId());
		
		PageableRequest pageableRequest = request.getPageable();
		int page = pageableRequest.getPage() - 1;
		int size = pageableRequest.getSize();
		Sort sort = Sort.unsorted();
		
		List<SortedModel> sortedList = pageableRequest.getSortedList();
		if (sortedList != null) {
			for (SortedModel sortedModel : sortedList) {
				sort.and(Sort.by(sortedModel.getDirection(), sortedModel.getField()));
			}
		}
		
		Pageable records = PageRequest.of(page, size, sort);
		Page<IamMsUser> userPage = this.iamMsUserRepository.findPageableByIamMsSystem_SystemIdAndIsDeleted(
				request.getSystemId(), "N", records);
		List<GetUserResponse> data = new ArrayList<>();
		for (IamMsUser user : userPage.getContent()) {
			data.add(GetUserResponse.builder()
					.systemId(user.getIamMsSystem().getSystemId())
					.userId(user.getUserId())
					.userCode(user.getUserCode())
					.localFirstName(user.getLocalFirstName())
					.localMiddleName(user.getLocalMiddleName())
					.localLastName(user.getLocalLastName())
					.engFirstName(user.getEngFirstName())
					.engMiddleName(user.getEngMiddleName())
					.engLastName(user.getEngLastName())
					.isIamAdmin(user.getIsIamAdmin())
					.build());
		}
		
		return IamMsUserPage.builder()
				.page(userPage.getNumber() + 1)
				.size(userPage.getSize())
				.totalPages(userPage.getTotalPages())
				.totalRecords(userPage.getTotalElements())
				.isFirst(userPage.isFirst())
				.isLast(userPage.isLast())
				.data(data)
				.build();
	}
	
	public GetUserResponse getUser(long userId) throws Exception {
		log.info("Service getUser userId: {}", userId);
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, "N")
				.orElseThrow(() -> new Exception("user not found"));
		
		return GetUserResponse.builder()
				.systemId(iamMsUser.getIamMsSystem().getSystemId())
				.userId(iamMsUser.getUserId())
				.userCode(iamMsUser.getUserCode())
				.localFirstName(iamMsUser.getLocalFirstName())
				.localMiddleName(iamMsUser.getLocalMiddleName())
				.localLastName(iamMsUser.getLocalLastName())
				.engFirstName(iamMsUser.getEngFirstName())
				.engMiddleName(iamMsUser.getEngMiddleName())
				.engLastName(iamMsUser.getEngLastName())
				.isIamAdmin(iamMsUser.getIsIamAdmin())
				.build();
	}
	
	@Transactional
	public void deleteUser(long userId) throws Exception {
		log.info("Service deleteUser userId: {}", userId);
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, "N")
				.orElseThrow(() -> new Exception("user not found"));
		
		iamMsUser.setIsDeleted("Y");
		this.iamMsUserRepository.save(iamMsUser);
	}
	
}
