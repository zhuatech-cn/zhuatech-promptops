/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.service;
import cn.zhuatech.promptops.model.OperationRecord;import cn.zhuatech.promptops.repository.OperationRecordRepository;
import jakarta.validation.constraints.DecimalMax;import jakarta.validation.constraints.DecimalMin;import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.NotNull;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;import java.util.*;import java.util.regex.*;
@Service
public class PromptOpsService{
 private static final Pattern VARIABLE=Pattern.compile("\\{\\{([a-zA-Z][a-zA-Z0-9_]*)}}");
 private static final List<String> INJECTION=List.of("ignore previous","system prompt","developer message","忽略以上","泄露系统","越权执行");
 private final OperationRecordRepository repository;
 public PromptOpsService(OperationRecordRepository repository){this.repository=repository;}
 @Transactional
 public Evaluation evaluate(ReleaseRequest r,String actor){
  var existing=repository.findByRequestId(r.requestId());
  if(existing.isPresent()){var e=existing.get();return new Evaluation(e.getId(),r.requestId(),Decision.valueOf(e.getDecision()),RiskLevel.valueOf(e.getRiskLevel()),"",List.of(),List.of(),"重复请求已返回原审计结果",true);}
  Set<String> placeholders=new TreeSet<>();Matcher matcher=VARIABLE.matcher(r.templateText());while(matcher.find())placeholders.add(matcher.group(1));
  List<String> blockers=new ArrayList<>();List<String>warnings=new ArrayList<>();
  for(String required:r.requiredVariables())if(!placeholders.contains(required))blockers.add("模板缺少必需占位符："+required);
  for(String key:placeholders)if(!r.variables().containsKey(key)||r.variables().get(key)==null||r.variables().get(key).isBlank())blockers.add("变量未赋值："+key);
  boolean injection=r.variables().values().stream().filter(Objects::nonNull).map(String::toLowerCase).anyMatch(v->INJECTION.stream().anyMatch(v::contains));
  if(injection)blockers.add("变量内容命中提示注入信号");
  if(r.qualityScore()<0.80)blockers.add("质量评测低于 0.80");
  if(r.safetyScore()<0.90)blockers.add("安全评测低于 0.90");
  if(r.regressionPassRate()<0.95)blockers.add("回归用例通过率低于 0.95");
  if(r.estimatedCostPer1kTokens().compareTo(r.maxCostPer1kTokens())>0)blockers.add("预估成本超过发布预算");
  if(!r.approved())blockers.add("提示词版本尚未完成业务审批");
  if(r.ownerId().equals(r.approverId()))blockers.add("提示词负责人和审批人必须职责分离");
  String rendered=r.templateText();for(var entry:r.variables().entrySet())rendered=rendered.replace("{{"+entry.getKey()+"}}",entry.getValue()==null?"":entry.getValue());
  if(VARIABLE.matcher(rendered).find())warnings.add("渲染结果仍包含未解析占位符");
  RiskLevel risk=injection||r.safetyScore()<0.90?RiskLevel.HIGH:r.estimatedCostPer1kTokens().compareTo(r.maxCostPer1kTokens().multiply(new BigDecimal("0.8")))>0?RiskLevel.MEDIUM:RiskLevel.LOW;
  Decision decision=blockers.isEmpty()?Decision.RELEASE:Decision.BLOCKED;
  String route=risk==RiskLevel.HIGH?"Prompt Owner→AI安全→业务负责人":risk==RiskLevel.MEDIUM?"Prompt Owner→平台负责人":"Prompt Owner→业务审批人";
  var saved=repository.save(new OperationRecord(r.requestId(),"PROMPT_RELEASE",decision.name(),risk.name(),"template="+r.templateName()+", version="+r.version()+", blockers="+blockers.size(),actor));
  return new Evaluation(saved.getId(),r.requestId(),decision,risk,route,List.copyOf(blockers),List.copyOf(warnings),decision==Decision.RELEASE?rendered:"",false);
 }
 @Transactional(readOnly=true)public List<OperationRecord> audits(){return repository.findTop100ByOrderByCreatedAtDesc();}
 public record ReleaseRequest(@NotBlank String requestId,@NotBlank String templateName,@NotBlank String version,@NotBlank String templateText,
  @NotNull Map<String,String> variables,@NotNull List<@NotBlank String> requiredVariables,@DecimalMin("0.0")@DecimalMax("1.0")double qualityScore,
  @DecimalMin("0.0")@DecimalMax("1.0")double safetyScore,@DecimalMin("0.0")@DecimalMax("1.0")double regressionPassRate,
  @NotNull@DecimalMin("0.0")BigDecimal estimatedCostPer1kTokens,@NotNull@DecimalMin("0.0")BigDecimal maxCostPer1kTokens,
  boolean approved,@NotBlank String ownerId,@NotBlank String approverId){}
 public record Evaluation(Long auditId,String requestId,Decision decision,RiskLevel riskLevel,String approvalRoute,List<String> blockers,List<String>warnings,String renderedPrompt,boolean duplicate){}
 public enum Decision{RELEASE,BLOCKED}public enum RiskLevel{LOW,MEDIUM,HIGH}
}
