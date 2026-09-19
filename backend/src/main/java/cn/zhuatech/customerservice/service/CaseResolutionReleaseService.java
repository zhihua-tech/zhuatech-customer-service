/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CaseResolutionReleaseService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.caseId() == null || request.caseId().isBlank()) blockers.add("客户工单编号不能为空");
        if (!request.customerIdentityVerified()) blockers.add("客户身份未核验");
        if (!request.entitlementVerified()) blockers.add("服务权益未核验");
        if (!request.severityAssessed()) blockers.add("事件严重度未评估");
        if (!request.resolutionTested()) blockers.add("解决方案未验证");
        if (!request.ownerSeparated()) blockers.add("处理人与关闭审批人未职责分离");
        if (!request.auditReady()) blockers.add("工单关闭审计证据不完整");
        if (!request.rootCauseCaptured()) actions.add("补充根因分析");
        if (!request.customerAccepted()) actions.add("取得客户验收确认");
        if (request.slaBreached() && !request.compensationApproved()) actions.add("审批 SLA 违约补偿");
        if (!request.knowledgeArticleLinked()) actions.add("关联或沉淀知识文章");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.RESOLVE : Decision.ESCALATE;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { RESOLVE, ESCALATE, BLOCKED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(String caseId, boolean customerIdentityVerified, boolean entitlementVerified,
                          boolean severityAssessed, boolean slaBreached, boolean rootCauseCaptured,
                          boolean resolutionTested, boolean customerAccepted, boolean compensationApproved,
                          boolean knowledgeArticleLinked, boolean ownerSeparated, boolean auditReady) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}
