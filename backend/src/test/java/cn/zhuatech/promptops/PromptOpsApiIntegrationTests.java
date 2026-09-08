/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;import org.springframework.http.MediaType;import org.springframework.test.web.servlet.MockMvc;import java.nio.charset.StandardCharsets;import java.util.Base64;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc class PromptOpsApiIntegrationTests{
 @Autowired MockMvc mvc;private String auth(){return "Basic "+Base64.getEncoder().encodeToString("admin:test-admin".getBytes(StandardCharsets.UTF_8));}
 @Test void authenticatedOperatorCanEvaluateAndPersistRelease()throws Exception{String body="""
 {"requestId":"API-PROMPT-1","templateName":"制度问答","version":"v1","templateText":"依据{{policy}}回答{{question}}","variables":{"policy":"政策A","question":"如何申请"},"requiredVariables":["policy","question"],"qualityScore":0.95,"safetyScore":0.97,"regressionPassRate":0.99,"estimatedCostPer1kTokens":0.01,"maxCostPer1kTokens":0.03,"approved":true,"ownerId":"owner","approverId":"approver"}
 """;mvc.perform(post("/api/promptops/releases/evaluate").header("Authorization",auth()).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andExpect(jsonPath("$.data.decision").value("RELEASE")).andExpect(jsonPath("$.data.auditId").isNumber()).andExpect(jsonPath("$.data.renderedPrompt").value("依据政策A回答如何申请"));}
 @Test void anonymousAuditAccessIsDenied()throws Exception{mvc.perform(get("/api/promptops/audits")).andExpect(status().isUnauthorized());}
}
