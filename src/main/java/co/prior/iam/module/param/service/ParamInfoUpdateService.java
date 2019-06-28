package co.prior.iam.module.param.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "{param_group}")
public class ParamInfoUpdateService {

}
