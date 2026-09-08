/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.model;
import jakarta.persistence.*;import java.time.Instant;
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
 protected OperationRecord(){}
 public OperationRecord(String requestId,String operationType,String decision,String riskLevel,String summary,String createdBy){this.requestId=requestId;this.operationType=operationType;this.decision=decision;this.riskLevel=riskLevel;this.summary=summary;this.createdBy=createdBy;this.createdAt=Instant.now();}
 public Long getId(){return id;}public String getRequestId(){return requestId;}public String getOperationType(){return operationType;}public String getDecision(){return decision;}public String getRiskLevel(){return riskLevel;}public String getSummary(){return summary;}public String getCreatedBy(){return createdBy;}public Instant getCreatedAt(){return createdAt;}
}
