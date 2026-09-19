/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.repository;
import cn.zhuatech.promptops.model.OperationRecord;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface OperationRecordRepository extends JpaRepository<OperationRecord,Long>{
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 Optional<OperationRecord> findByRequestId(String requestId);
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 List<OperationRecord> findTop100ByOrderByCreatedAtDesc();
}
