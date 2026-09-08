/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.repository;
import cn.zhuatech.promptops.model.OperationRecord;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface OperationRecordRepository extends JpaRepository<OperationRecord,Long>{
 Optional<OperationRecord> findByRequestId(String requestId);
 List<OperationRecord> findTop100ByOrderByCreatedAtDesc();
}
