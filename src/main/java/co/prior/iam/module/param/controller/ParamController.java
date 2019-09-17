package co.prior.iam.module.param.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.module.param.model.request.CreateParamGroupRequest;
import co.prior.iam.module.param.model.request.CreateParamInfoRequest;
import co.prior.iam.module.param.model.response.GetParamsResponse;
import co.prior.iam.module.param.service.ParamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/preference")
public class ParamController {

	private final ParamService paramService;

	public ParamController(ParamService paramService) {
		this.paramService = paramService;
	}

	@GetMapping("/params")
	public ResponseEntity<List<GetParamsResponse>> getParams() {
		log.info("Controller getParams");

		List<GetParamsResponse> response = this.paramService.getParams();
		
		return ResponseEntity.ok(response);
	}

	@PostMapping("/param/group")
	public ResponseEntity<Void> createParamGroup(@RequestBody CreateParamGroupRequest request) {
		log.info("Controller createParamGroup paramGroup: {}", request.getParamGroup());
		
		this.paramService.createParamGroup(request);

		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/param/info")
	public ResponseEntity<Void> createParamInfo(@RequestBody CreateParamInfoRequest request) {
		log.info("Controller createParamInfo paramInfo: {}", request.getParamCode());
		
		this.paramService.createParamInfo(request);

		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/params/refresh")
	public ResponseEntity<Void> refreshParams() {
		log.info("Controller refreshParams");
		
		this.paramService.refreshParams();

		return ResponseEntity.noContent().build();
	}

}
