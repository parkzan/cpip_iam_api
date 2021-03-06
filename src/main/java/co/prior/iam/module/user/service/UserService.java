package co.prior.iam.module.user.service;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.entity.PpiMsProvince;
import co.prior.iam.entity.PpiMsSurvey;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.model.PageableRequest;
import co.prior.iam.model.SortedModel;
import co.prior.iam.module.user.model.request.DeleteAllUserRoleInUserRequest;
import co.prior.iam.module.user.model.request.EditUserRequest;
import co.prior.iam.module.user.model.request.GetUsersRequest;
import co.prior.iam.module.user.model.request.ResignUserRequest;
import co.prior.iam.module.user.model.response.GetUserResponse;
import co.prior.iam.module.user.model.response.IamMsUserPage;
import co.prior.iam.repository.IamMsUserRepository;
import co.prior.iam.repository.IamMsUserRoleRepository;
import co.prior.iam.repository.PpiMsProvinceRepository;
import co.prior.iam.repository.PpiMsSurveyRepository;
import co.prior.iam.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final IamMsUserRepository iamMsUserRepository;
    private final PpiMsSurveyRepository ppiMsSurveyRepository;
    private final PpiMsProvinceRepository ppiMsProvinceRepository;
    private final IamMsUserRoleRepository iamMsUserRoleRepository;

    public UserService(IamMsUserRepository iamMsUserRepository, PpiMsSurveyRepository ppiMsSurveyRepository, PpiMsProvinceRepository ppiMsProvinceRepository, IamMsUserRoleRepository iamMsUserRoleRepository) {
        this.iamMsUserRepository = iamMsUserRepository;
        this.ppiMsSurveyRepository = ppiMsSurveyRepository;
        this.ppiMsProvinceRepository = ppiMsProvinceRepository;
        this.iamMsUserRoleRepository = iamMsUserRoleRepository;
    }

    public IamMsUserPage getUsers(GetUsersRequest request, HttpServletRequest httpRequest) {
        log.info("Services getUsers systemId: {}", request);

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

        Principal principal = httpRequest.getUserPrincipal();

        Pageable records = PageRequest.of(page, size, sort);
        Page<IamMsUser> userPage = this.iamMsUserRepository.findPageableByUserCodeNotAndIsDeletedOrderByUserCode(
				principal.getName(), AnswerFlag.N.toString(), records);
        List<GetUserResponse> data = new ArrayList<>();
        for (IamMsUser user : userPage.getContent()) {

            if (user.getProvince() != null) {
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
                        .noOfFailTimes(user.getNoOfFailTimes())
                        .updatedDate(user.getUpdatedDate())
                        .updatedBy(user.getUpdatedBy())
                        .createdDate(user.getCreatedDate())
                        .createdBy(user.getCreatedBy())
                        .userType(user.getUserType())
                        .provinceId(user.getProvince().getProvinceId())
                        .lineManager(user.getLineManager() != null ? user.getLineManager().getUserId() : null)
                        .position(user.getPosition())
                        .division(user.getDivision())
                        .unit(user.getUnit())
                        .department(user.getDepartment())
                        .build());
            } else if (user.getSurvey() != null) {
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
                        .noOfFailTimes(user.getNoOfFailTimes())
                        .updatedDate(user.getUpdatedDate())
                        .updatedBy(user.getUpdatedBy())
                        .createdDate(user.getCreatedDate())
                        .createdBy(user.getCreatedBy())
                        .userType(user.getUserType())
                        .surveyId(user.getSurvey().getSurveyId())
                        .lineManager(user.getLineManager() != null ? user.getLineManager().getUserId() : null)
                        .position(user.getPosition())
                        .division(user.getDivision())
                        .unit(user.getUnit())
                        .department(user.getDepartment())
                        .build());
            } else if (user.getSurvey() == null && user.getProvince() == null) {
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
                        .noOfFailTimes(user.getNoOfFailTimes())
                        .updatedDate(user.getUpdatedDate())
                        .updatedBy(user.getUpdatedBy())
                        .createdDate(user.getCreatedDate())
                        .createdBy(user.getCreatedBy())
                        .userType(user.getUserType())
                        .lineManager(user.getLineManager() != null ? user.getLineManager().getUserId() : null )
                        .position(user.getPosition())
                        .division(user.getDivision())
                        .unit(user.getUnit())
                        .department(user.getDepartment())
                        .build());
            }

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
        if (iamMsUser.getProvince() != null) {
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
                    .noOfFailTimes(iamMsUser.getNoOfFailTimes())
                    .createdBy(iamMsUser.getCreatedBy())
                    .createdDate(iamMsUser.getCreatedDate())
                    .updatedBy(iamMsUser.getUpdatedBy())
                    .updatedDate(iamMsUser.getUpdatedDate())
                    .userType(iamMsUser.getUserType())
                    .provinceId(iamMsUser.getProvince().getProvinceId())
                    .build();
        } else if (iamMsUser.getSurvey() != null) {
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
                    .noOfFailTimes(iamMsUser.getNoOfFailTimes())
                    .createdBy(iamMsUser.getCreatedBy())
                    .createdDate(iamMsUser.getCreatedDate())
                    .updatedBy(iamMsUser.getUpdatedBy())
                    .updatedDate(iamMsUser.getUpdatedDate())
                    .userType(iamMsUser.getUserType())
                    .surveyId(iamMsUser.getSurvey().getSurveyId())
                    .build();
        }
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
                .noOfFailTimes(iamMsUser.getNoOfFailTimes())
                .createdBy(iamMsUser.getCreatedBy())
                .createdDate(iamMsUser.getCreatedDate())
                .updatedBy(iamMsUser.getUpdatedBy())
                .updatedDate(iamMsUser.getUpdatedDate())
                .userType(iamMsUser.getUserType())
                .build();
    }

    @Transactional
    public void editUser(EditUserRequest request) {
        log.info("Service editUser userId: {}", request.getUserId());

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

        Optional<IamMsUser> lineManager = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getLineManager(),AnswerFlag.N.toString());

        if (request.getSurveyId() != null) {
            Optional<PpiMsSurvey> survey = this.ppiMsSurveyRepository.findBySurveyIdAndIsDeleted(request.getSurveyId(), AnswerFlag.N.toString());
            if (!survey.isPresent()) {
                throw new DataNotFoundException(ErrorCode.SURVEY_NOT_FOUND);
            }
            iamMsUser.setSurvey(survey.get());
            iamMsUser.setProvince(null);

        } else if (request.getProvinceId() != null) {
            Optional<PpiMsProvince> province = this.ppiMsProvinceRepository.findByProvinceIdAndIsDeleted(request.getProvinceId(), AnswerFlag.N.toString());
            if (!province.isPresent()) {
                throw new DataNotFoundException(ErrorCode.PROVINCE_NOT_FOUND);
            }
            iamMsUser.setSurvey(null);
            iamMsUser.setProvince(province.get());
        } else if (request.getProvinceId() == null && request.getSurveyId() == null) {
            iamMsUser.setSurvey(null);
            iamMsUser.setProvince(null);
        }

        iamMsUser.setLocalFirstName(request.getLocalFirstName());
        iamMsUser.setLocalMiddleName(request.getLocalMiddleName());
        iamMsUser.setLocalLastName(request.getLocalLastName());
        iamMsUser.setEngFirstName(request.getEngFirstName());
        iamMsUser.setEngMiddleName(request.getEngMiddleName());
        iamMsUser.setEngLastName(request.getEngLastName());
        iamMsUser.setDisableFlag(request.getDisableFlag().toString());
        iamMsUser.setUserType(request.getUserType());
        iamMsUser.setLineManager(lineManager.get());
        iamMsUser.setDepartment(request.getDepartment());
        iamMsUser.setDivision(request.getDivision());
        iamMsUser.setUnit(request.getUnit());
        iamMsUser.setPosition(request.getPosition());
        this.iamMsUserRepository.save(iamMsUser);
    }

    @Transactional
    public void deleteUser(long userId) {
        log.info("Service deleteUser userId: {}", userId);

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

        iamMsUser.setIsDeleted(AnswerFlag.Y.toString());
        this.iamMsUserRepository.save(iamMsUser);

        List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(userId , AnswerFlag.N.toString());
        if(!iamMsUserRoles.isEmpty()){
            for (IamMsUserRole iamMsUserRole : iamMsUserRoles){
                iamMsUserRole.setIsDeleted(AnswerFlag.Y.toString());
            }
        }

    }

    public long getUserId(String userCode) {
        log.info("Service getUserId userCode: {}", userCode);

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

        return iamMsUser.getUserId();
    }

    @Transactional
    public void resignUser(ResignUserRequest request) {
        log.info("Service resignUser userId: {}", request.getUserId());

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

        iamMsUser.setDisableFlag(AnswerFlag.N.toString().equalsIgnoreCase(
                request.getDisabledFlag()) ? AnswerFlag.N.toString() : AnswerFlag.Y.toString());
        iamMsUser.setNoOfFailTimes(0);

        this.iamMsUserRepository.save(iamMsUser);
    }


    public List<IamMsUser> searchUser() {
        log.info("Service searchUser: {}");

        List<IamMsUser> user = this.iamMsUserRepository.findAllByIsDeleted(AnswerFlag.N.toString());

        return user;
    }


}
