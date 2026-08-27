/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.domain;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();
    public DomainCatalog() {
        actions.put("ACCEPT", new WorkflowAction("ACCEPT", "受理工单", List.of("草稿"), "处理中", "OPERATOR"));
        actions.put("RESOLVE", new WorkflowAction("RESOLVE", "提交解决", List.of("处理中"), "待确认", "OPERATOR"));
        actions.put("CLOSE", new WorkflowAction("CLOSE", "确认关闭", List.of("待确认"), "已关闭", "ADMIN"));
    }
    public String systemName() { return "知华科技全渠道客户服务系统"; }
    public String scene() { return "全渠道会话、客户档案、工单、路由、SLA、升级、知识库、质检与满意度"; }
    public String initialStatus() { return "草稿"; }
    public String partyLabel() { return "客户/服务请求"; }
    public String amountLabel() { return "服务价值"; }
    public String quantityLabel() { return "请求数量"; }
    public String dueLabel() { return "SLA期限"; }
    public List<ModuleDefinition> modules() { return List.of(
            new ModuleDefinition("OMNICHANNEL", "全渠道接入", "统一接入网页、微信、邮件、电话和API"),
            new ModuleDefinition("CUSTOMER_PROFILE", "客户视图", "聚合客户、合同、产品和历史服务记录"),
            new ModuleDefinition("TICKET", "服务工单", "登记、分类、分派和跟踪服务请求"),
            new ModuleDefinition("ROUTING", "路由队列", "按技能、区域、负载和优先级智能分配"),
            new ModuleDefinition("SLA", "SLA管理", "配置响应、解决时限与升级规则"),
            new ModuleDefinition("KNOWLEDGE", "服务知识库", "沉淀标准答案、操作指引和版本"),
            new ModuleDefinition("QUALITY", "服务质检", "执行抽检、评分、申诉与改进"),
            new ModuleDefinition("SATISFACTION", "客户满意度", "采集评价、回访和负面反馈"),
            new ModuleDefinition("REPORTING", "服务分析", "分析渠道、效率、一次解决率和趋势")
        ); }
    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }
    public record ModuleDefinition(String code,String name,String description) {}
    public record WorkflowAction(String code,String label,List<String> from,String to,String requiredRole) {}
}
