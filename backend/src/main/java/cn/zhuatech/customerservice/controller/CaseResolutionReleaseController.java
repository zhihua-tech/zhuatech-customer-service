/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.controller;

import cn.zhuatech.customerservice.common.ApiResponse;
import cn.zhuatech.customerservice.service.CaseResolutionReleaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/customer-service")
public class CaseResolutionReleaseController {
    private final CaseResolutionReleaseService service;
    public CaseResolutionReleaseController(CaseResolutionReleaseService service) { this.service = service; }

    @PostMapping("/case-resolution-release")
    public ApiResponse<?> assess(@RequestBody CaseResolutionReleaseService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
