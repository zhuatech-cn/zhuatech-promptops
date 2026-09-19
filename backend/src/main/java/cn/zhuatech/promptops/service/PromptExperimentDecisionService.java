/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.service;
import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.math.*;import java.util.*;
/**
 * 依据效果、置信度、安全、延迟和成本决定提示词实验去留。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class PromptExperimentDecisionService{
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public Result decide(Request r){BigDecimal successLift=r.variantSuccessRate().subtract(r.controlSuccessRate());BigDecimal safetyRegression=r.variantSafetyIncidentRate().subtract(r.controlSafetyIncidentRate());List<String>reasons=new ArrayList<>(),actions=new ArrayList<>();Decision d;
  if(!r.guardrailsPassed()||safetyRegression.compareTo(r.maxSafetyRegression())>0){d=Decision.ROLLBACK;reasons.add(!r.guardrailsPassed()?"变体未通过安全护栏":"变体安全事件率回归超过阈值");actions.add("停止变体流量并恢复已批准基线版本");}
  else if(r.controlSamples()<r.minSamplesPerArm()||r.variantSamples()<r.minSamplesPerArm()||r.statisticalConfidence().compareTo(r.minConfidence())<0){d=Decision.CONTINUE;reasons.add("样本量或统计置信度不足");actions.add("继续限量实验，不扩大流量或替换生产基线");}
  else if(successLift.compareTo(r.minSuccessLift())>=0&&r.variantP95LatencyMs()<=r.maxP95LatencyMs()&&r.variantCostPer1k().compareTo(r.maxCostPer1k())<=0&&r.ownerApproved()){d=Decision.PROMOTE;actions.add("提升变体为新基线并保留一键回滚版本");}
  else{d=Decision.STOP;reasons.add("业务提升、延迟、成本或负责人审批未达到发布条件");actions.add("结束实验并保留当前生产基线");}
  return new Result(d,successLift,safetyRegression,List.copyOf(reasons),List.copyOf(actions));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Request(@NotBlank String experimentId,@Min(0)int controlSamples,@Min(0)int variantSamples,@Min(1)int minSamplesPerArm,@DecimalMin("0")@DecimalMax("1")BigDecimal controlSuccessRate,@DecimalMin("0")@DecimalMax("1")BigDecimal variantSuccessRate,@DecimalMin("0")@DecimalMax("1")BigDecimal controlSafetyIncidentRate,@DecimalMin("0")@DecimalMax("1")BigDecimal variantSafetyIncidentRate,@DecimalMin("0")@DecimalMax("1")BigDecimal maxSafetyRegression,@DecimalMin("0")@DecimalMax("1")BigDecimal statisticalConfidence,@DecimalMin("0")@DecimalMax("1")BigDecimal minConfidence,@DecimalMin("-1")@DecimalMax("1")BigDecimal minSuccessLift,@Min(1)long variantP95LatencyMs,@Min(1)long maxP95LatencyMs,@DecimalMin("0")BigDecimal variantCostPer1k,@DecimalMin("0")BigDecimal maxCostPer1k,boolean guardrailsPassed,boolean ownerApproved){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Result(Decision decision,BigDecimal successLift,BigDecimal safetyRegression,List<String>reasons,List<String>actions){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public enum Decision{PROMOTE,CONTINUE,STOP,ROLLBACK}
}
