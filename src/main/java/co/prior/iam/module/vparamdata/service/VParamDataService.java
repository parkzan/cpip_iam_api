package co.prior.iam.module.vparamdata.service;

import co.prior.iam.module.vparamdata.model.respone.VparamDataReponse;
import co.prior.iam.repository.VParameterDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VParamDataService {

    private final VParameterDataRepository vParameterDataRepository;

    public VParamDataService(VParameterDataRepository vParameterDataRepository) {
        this.vParameterDataRepository = vParameterDataRepository;
    }

    public List<VparamDataReponse> getMenuObject(){
        List<VparamDataReponse> menu = this.vParameterDataRepository.getParamMenu();

        return menu;
    }
}
