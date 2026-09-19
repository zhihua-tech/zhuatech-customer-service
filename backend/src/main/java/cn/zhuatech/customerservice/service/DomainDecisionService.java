/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.service;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class DomainDecisionService {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public DecisionResult assess(DecisionRequest request) { double consumed=request.elapsedMinutes()*100d/request.slaMinutes();int score=100;List<String> actions=new ArrayList<>();if(consumed>=100&&!request.resolved()){score-=50;actions.add("立即升级SLA违约工单");}else if(consumed>=80&&!request.resolved()){score-=25;actions.add("在SLA到期前升级处理");}if(request.firstResponseMinutes()>Math.max(15,request.slaMinutes()/4)){score-=20;actions.add("复盘首次响应时效");}if("P1".equalsIgnoreCase(request.priority())&&!request.escalated()){score-=20;actions.add("启动P1事件升级机制");}return result(score,actions,"ON_TRACK","AT_RISK","BREACHED",Map.of("slaConsumedPercent",Math.round(consumed),"remainingMinutes",Math.max(0,request.slaMinutes()-request.elapsedMinutes()),"resolved",request.resolved())); }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private DecisionResult result(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=80?good:score>=50?warn:bad;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private DecisionResult riskResult(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=70?bad:score>=40?warn:good;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record DecisionRequest(
        @NotBlank String ticketNo,
        @NotBlank String priority,
        @PositiveOrZero int elapsedMinutes,
        @Positive int slaMinutes,
        @PositiveOrZero int firstResponseMinutes,
        boolean resolved,
        boolean escalated) {}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record DecisionResult(String decision,int score,Map<String,Object> metrics,List<String> actions) {}
}
