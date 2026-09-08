/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.controller;
import cn.zhuatech.promptops.common.ApiResponse;import cn.zhuatech.promptops.model.OperationRecord;import cn.zhuatech.promptops.service.PromptOpsService;
import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/promptops")
public class PromptOpsController{
 private final PromptOpsService service;public PromptOpsController(PromptOpsService service){this.service=service;}
 @PostMapping("/releases/evaluate")@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
 public ApiResponse<PromptOpsService.Evaluation>evaluate(@Valid@RequestBody PromptOpsService.ReleaseRequest request,Authentication auth){return ApiResponse.ok("提示词发布评估完成",service.evaluate(request,auth.getName()));}
 @GetMapping("/audits")@PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
 public ApiResponse<List<OperationRecord>>audits(){return ApiResponse.ok("审计记录查询成功",service.audits());}
}
