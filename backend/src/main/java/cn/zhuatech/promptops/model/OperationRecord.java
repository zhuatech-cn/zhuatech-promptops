/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.model;
import jakarta.persistence.*;import java.time.Instant;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="operation_record",uniqueConstraints=@UniqueConstraint(name="uk_operation_request",columnNames="request_id"))
public class OperationRecord{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)private Long id;
 @Column(name="request_id",nullable=false,length=80)private String requestId;
 @Column(nullable=false,length=40)private String operationType;
 @Column(nullable=false,length=30)private String decision;
 @Column(nullable=false,length=20)private String riskLevel;
 @Column(nullable=false,length=500)private String summary;
 @Column(nullable=false,length=80)private String createdBy;
 @Column(nullable=false)private Instant createdAt;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 protected OperationRecord(){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public OperationRecord(String requestId,String operationType,String decision,String riskLevel,String summary,String createdBy){this.requestId=requestId;this.operationType=operationType;this.decision=decision;this.riskLevel=riskLevel;this.summary=summary;this.createdBy=createdBy;this.createdAt=Instant.now();}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public Long getId(){return id;}/**
                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                 */
public String getRequestId(){return requestId;}/**
                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                */
public String getOperationType(){return operationType;}/**
                                                                                                                                       * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                       */
public String getDecision(){return decision;}/**
                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                    */
public String getRiskLevel(){return riskLevel;}/**
                                                                                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                   */
public String getSummary(){return summary;}/**
                                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                              */
public String getCreatedBy(){return createdBy;}/**
                                                                                                                                                                                                                                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                             */
public Instant getCreatedAt(){return createdAt;}
}
