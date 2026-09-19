/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.deep;import cn.zhuatech.customerservice.common.ApiResponse;import org.springframework.web.bind.annotation.*;/**
                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                 */
@RestController @RequestMapping("/api/admin/service") public class DeepAdminController{private final DeepDomainService s;/**
                                                                                                                                                                                                                                                                          * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                          */
public DeepAdminController(DeepDomainService s){this.s=s;}/**
                                                                                                                                                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                    */
@PostMapping("/knowledge/{id}/publish")ApiResponse<?> publish(@PathVariable Long id){return ApiResponse.ok(s.publishArticle(id));}}
