package co.prior.iam.security;

import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.entity.IamMsUser;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.repository.IamMsUserRepository;
import co.prior.iam.repository.ParamInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class CustomDetailsService implements UserDetailsService {

    private final IamMsUserRepository iamMsUserRepository;

    private final ParamInfoRepository paramInfoRepository;

    public CustomDetailsService(IamMsUserRepository iamMsUserRepository, ParamInfoRepository paramInfoRepository) {
        this.iamMsUserRepository = iamMsUserRepository;
        this.paramInfoRepository = paramInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userCode) {
        log.info("Service loadUserByUsername userCode: {}", userCode);

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, AnswerFlag.N.toString())
                .orElseThrow(() -> new UsernameNotFoundException("user not found with code: " + userCode));


        boolean isAccountNonLocked = iamMsUser.getNoOfFailTimes() >= 3 ? Boolean.FALSE : Boolean.TRUE;
//    	boolean isEnabled = AnswerFlag.Y.toString().equalsIgnoreCase(iamMsUser.getFirstTimeLogin())
//    			|| AnswerFlag.Y.toString().equalsIgnoreCase(iamMsUser.getDisableFlag())? Boolean.FALSE : Boolean.TRUE;
        boolean isEnabled = AnswerFlag.Y.toString().equalsIgnoreCase(iamMsUser.getDisableFlag()) ? Boolean.FALSE : Boolean.TRUE;
            IamMsParameterInfo parameterInfo = this.paramInfoRepository.findByParamInfoIdAndIsDeleted(iamMsUser.getUserType(), AnswerFlag.N.toString())
                    .orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));



        if (iamMsUser.getProvince() != null) {

            return UserPrincipal.builder()
                    .userId(iamMsUser.getUserId())
                    .userCode(iamMsUser.getUserCode())
                    .userPassword(iamMsUser.getUserPassword())
                    .localFirstName(iamMsUser.getLocalFirstName())
                    .localMiddleName(iamMsUser.getLocalMiddleName())
                    .localLastName(iamMsUser.getLocalLastName())
                    .engFirstName(iamMsUser.getEngFirstName())
                    .engMiddleName(iamMsUser.getEngMiddleName())
                    .engLastName(iamMsUser.getEngLastName())
                    .provinceId(iamMsUser.getProvince().getProvinceId())
                    .userType(iamMsUser.getUserType())
					.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                    .userTypeCode(parameterInfo.getParamCode())
                    .isAccountNonLocked(isAccountNonLocked)
                    .isEnabled(isEnabled)
                    .build();
        } else if (iamMsUser.getSurvey() != null) {
            return UserPrincipal.builder()
                    .userId(iamMsUser.getUserId())
                    .userCode(iamMsUser.getUserCode())
                    .userPassword(iamMsUser.getUserPassword())
                    .localFirstName(iamMsUser.getLocalFirstName())
                    .localMiddleName(iamMsUser.getLocalMiddleName())
                    .localLastName(iamMsUser.getLocalLastName())
                    .engFirstName(iamMsUser.getEngFirstName())
                    .engMiddleName(iamMsUser.getEngMiddleName())
                    .engLastName(iamMsUser.getEngLastName())
                    .surveyId(iamMsUser.getSurvey().getSurveyId())
                    .userType(iamMsUser.getUserType())
					.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                    .userTypeCode(parameterInfo.getParamCode())
                    .isAccountNonLocked(isAccountNonLocked)
                    .isEnabled(isEnabled)
                    .build();
        }

        return UserPrincipal.builder()
                .userId(iamMsUser.getUserId())
                .userCode(iamMsUser.getUserCode())
                .userPassword(iamMsUser.getUserPassword())
                .localFirstName(iamMsUser.getLocalFirstName())
                .localMiddleName(iamMsUser.getLocalMiddleName())
                .localLastName(iamMsUser.getLocalLastName())
                .engFirstName(iamMsUser.getEngFirstName())
                .engMiddleName(iamMsUser.getEngMiddleName())
                .engLastName(iamMsUser.getEngLastName())
                .userType(iamMsUser.getUserType())
				.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                .userTypeCode(parameterInfo.getParamCode())
                .isAccountNonLocked(isAccountNonLocked)
                .isEnabled(isEnabled)
                .build();
    }

    public UserDetails loadUserById(Long userId) {
        log.info("Service loadUserById userId: {}", userId);

        IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
                .orElseThrow(() -> new UsernameNotFoundException("user not found with id: " + userId));

        IamMsParameterInfo parameterInfo = this.paramInfoRepository.findByParamInfoIdAndIsDeleted(iamMsUser.getUserType(), AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

        if (iamMsUser.getProvince() != null) {

            return UserPrincipal.builder()
                    .userId(iamMsUser.getUserId())
                    .userCode(iamMsUser.getUserCode())
                    .userPassword(iamMsUser.getUserPassword())
                    .localFirstName(iamMsUser.getLocalFirstName())
                    .localMiddleName(iamMsUser.getLocalMiddleName())
                    .localLastName(iamMsUser.getLocalLastName())
                    .engFirstName(iamMsUser.getEngFirstName())
                    .engMiddleName(iamMsUser.getEngMiddleName())
                    .engLastName(iamMsUser.getEngLastName())
                    .provinceId(iamMsUser.getProvince().getProvinceId())
                    .userType(iamMsUser.getUserType())
					.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                    .userTypeCode(parameterInfo.getParamCode())
                    .authorities(AnswerFlag.Y.toString().equals(iamMsUser.getIsIamAdmin()) ?
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_IAM_ADMIN")) : null)
                    .build();
        } else if (iamMsUser.getSurvey() != null) {
            return UserPrincipal.builder()
                    .userId(iamMsUser.getUserId())
                    .userCode(iamMsUser.getUserCode())
                    .userPassword(iamMsUser.getUserPassword())
                    .localFirstName(iamMsUser.getLocalFirstName())
                    .localMiddleName(iamMsUser.getLocalMiddleName())
                    .localLastName(iamMsUser.getLocalLastName())
                    .engFirstName(iamMsUser.getEngFirstName())
                    .engMiddleName(iamMsUser.getEngMiddleName())
                    .engLastName(iamMsUser.getEngLastName())
					.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                    .surveyId(iamMsUser.getSurvey().getSurveyId())
                    .userType(iamMsUser.getUserType())
                    .userTypeCode(parameterInfo.getParamCode())
                    .authorities(AnswerFlag.Y.toString().equals(iamMsUser.getIsIamAdmin()) ?
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_IAM_ADMIN")) : null)
                    .build();
        }

        return UserPrincipal.builder()
                .userId(iamMsUser.getUserId())
                .userCode(iamMsUser.getUserCode())
                .userPassword(iamMsUser.getUserPassword())
                .localFirstName(iamMsUser.getLocalFirstName())
                .localMiddleName(iamMsUser.getLocalMiddleName())
                .localLastName(iamMsUser.getLocalLastName())
                .engFirstName(iamMsUser.getEngFirstName())
                .engMiddleName(iamMsUser.getEngMiddleName())
                .userType(iamMsUser.getUserType())
				.firstTimeLogin(iamMsUser.getFirstTimeLogin())
                .userTypeCode(parameterInfo.getParamCode())
                .engLastName(iamMsUser.getEngLastName())
                .authorities(AnswerFlag.Y.toString().equals(iamMsUser.getIsIamAdmin()) ?
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_IAM_ADMIN")) : null)
                .build();


    }

}
