package co.prior.iam.module.param.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "param")
public class ParamInfoUpdateService {

}
