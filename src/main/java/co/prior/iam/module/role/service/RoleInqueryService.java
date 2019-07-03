package co.prior.iam.module.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleInqueryService {

	private final RoleRepository roleRepository;

	public RoleInqueryService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<IamMsRole> inqueryRole(long systemId) {
		log.info("Service inqueryRole systemId: {}", systemId);

		List<IamMsRole> roleList = this.roleRepository.findByIamMsSystem_SystemIdAndIsDeletedOrderByRoleId(systemId, AnswerFlag.N.toString());
		if (roleList.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND);
		}
		
		return roleList;
	}

}
