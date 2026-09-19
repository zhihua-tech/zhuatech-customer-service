/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.service;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.time.*;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class CaseRoutingService{
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public RoutingResult route(@Valid RoutingRequest request){Map<String,Integer>load=new LinkedHashMap<>();request.agents().forEach(a->load.put(a.agentId(),a.openCases()));List<CaseItem>cases=request.cases().stream().sorted(Comparator.comparing(CaseItem::vip).reversed().thenComparing(CaseItem::slaDueAt)).toList();List<Assignment>assignments=new ArrayList<>();List<Unassigned>unassigned=new ArrayList<>();
  for(CaseItem item:cases){Agent best=null;int score=-1;for(Agent agent:request.agents()){int current=load.get(agent.agentId());if(!agent.active()||current>=agent.capacity()||!agent.channels().contains(item.channel())||!agent.languages().contains(item.language())||!agent.skills().contains(item.requiredSkill()))continue;int candidate=100-current*10+(item.vip()?15:0);if(best==null||candidate>score){best=agent;score=candidate;}}if(best==null){unassigned.add(new Unassigned(item.caseNo(),"无同时满足渠道、语言、技能和容量的客服"));continue;}load.compute(best.agentId(),(k,v)->v+1);long remaining=Math.max(0,Duration.between(request.routeAt(),item.slaDueAt()).toMinutes());assignments.add(new Assignment(item.caseNo(),best.agentId(),best.queue(),score,remaining,remaining<30?"SLA_RISK":"ON_TRACK"));}
  return new RoutingResult(assignments,unassigned,load,assignments.size(),unassigned.size(),assignments.stream().filter(a->"SLA_RISK".equals(a.slaStatus())).count());
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record RoutingRequest(@NotNull LocalDateTime routeAt,@NotEmpty List<@Valid Agent>agents,@NotEmpty List<@Valid CaseItem>cases){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Agent(@NotBlank String agentId,@NotBlank String queue,@NotEmpty Set<@NotBlank String>channels,@NotEmpty Set<@NotBlank String>languages,@NotEmpty Set<@NotBlank String>skills,@Min(1) int capacity,@Min(0) int openCases,boolean active){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record CaseItem(@NotBlank String caseNo,@NotBlank String channel,@NotBlank String language,@NotBlank String requiredSkill,boolean vip,@NotNull LocalDateTime slaDueAt){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Assignment(String caseNo,String agentId,String queue,int score,long slaRemainingMinutes,String slaStatus){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Unassigned(String caseNo,String reason){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record RoutingResult(List<Assignment>assignments,List<Unassigned>unassigned,Map<String,Integer>resultingLoad,int assignedCount,int unassignedCount,long slaRiskCount){}
}
