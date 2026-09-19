/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.controller;import cn.zhuatech.promptops.common.ApiResponse;import cn.zhuatech.promptops.service.PromptExperimentDecisionService;import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController@RequestMapping("/api/promptops/experiments")public class PromptExperimentDecisionController{private final PromptExperimentDecisionService service;/**
                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                   */
public PromptExperimentDecisionController(PromptExperimentDecisionService s){service=s;}/**
                                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                           */
@PostMapping("/decision")@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")public ApiResponse<PromptExperimentDecisionService.Result>decide(@Valid@RequestBody PromptExperimentDecisionService.Request r){return ApiResponse.ok("提示词实验决策完成",service.decide(r));}}
