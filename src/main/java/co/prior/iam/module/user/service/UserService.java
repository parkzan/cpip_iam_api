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
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.model.PageableRequest;
import co.prior.iam.model.SortedModel;
import co.prior.iam.module.user.model.request.EditUserRequest;
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
		log.info("Service getUsers systemId: {}", request);
		
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
		Page<IamMsUser> userPage = this.iamMsUserRepository.findPageableByIsIamAdminAndIsDeletedOrderByUserCode(
				 AnswerFlag.N.toString(), AnswerFlag.N.toString(), records);
		List<GetUserResponse> data = new ArrayList<>();
		for (IamMsUser user : userPage.getContent()) {
			data.add(GetUserResponse.builder()
					.userId(user.getUserId())
					.userCode(user.getUserCode())
					.localFirstName(user.getLocalFirstName())
					.localMiddleName(user.getLocalMiddleName())
					.localLastName(user.getLocalLastName())
					.engFirstName(user.getEngFirstName())
					.engMiddleName(user.getEngMiddleName())
					.engLastName(user.getEngLastName())
					.isIamAdmin(user.getIsIamAdmin())
					.disableFlag(user.getDisableFlag())
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
	
	public GetUserResponse getUser(long userId) {
		log.info("Service getUser userId: {}", userId);
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		
		return GetUserResponse.builder()
				.userId(iamMsUser.getUserId())
				.userCode(iamMsUser.getUserCode())
				.localFirstName(iamMsUser.getLocalFirstName())
				.localMiddleName(iamMsUser.getLocalMiddleName())
				.localLastName(iamMsUser.getLocalLastName())
				.engFirstName(iamMsUser.getEngFirstName())
				.engMiddleName(iamMsUser.getEngMiddleName())
				.engLastName(iamMsUser.getEngLastName())
				.isIamAdmin(iamMsUser.getIsIamAdmin())
				.disableFlag(iamMsUser.getDisableFlag())
				.createdBy(iamMsUser.getCreatedBy())
				.createdDate(iamMsUser.getCreatedDate())
				.updatedBy(iamMsUser.getUpdatedBy())
				.updatedDate(iamMsUser.getUpdatedDate())
				.build();
	}
	
	@Transactional
	public void editUser(EditUserRequest request) {
		log.info("Service editUser userId: {}", request.getUserId());
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		
		iamMsUser.setLocalFirstName(request.getLocalFirstName());
		iamMsUser.setLocalMiddleName(request.getLocalMiddleName());
		iamMsUser.setLocalLastName(request.getLocalLastName());
		iamMsUser.setEngFirstName(request.getEngFirstName());
		iamMsUser.setEngMiddleName(request.getEngMiddleName());
		iamMsUser.setEngLastName(request.getEngLastName());
		iamMsUser.setDisableFlag(request.getDisableFlag().toString());
		this.iamMsUserRepository.save(iamMsUser);
	}
	
	@Transactional
	public void deleteUser(long userId) {
		log.info("Service deleteUser userId: {}", userId);
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		
		iamMsUser.setIsDeleted(AnswerFlag.Y.toString());
		this.iamMsUserRepository.save(iamMsUser);
	}
	
	public long getUserId(String userCode) {
		log.info("Service getUserId userCode: {}", userCode);
		
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		
		return iamMsUser.getUserId();
	}
	
}
