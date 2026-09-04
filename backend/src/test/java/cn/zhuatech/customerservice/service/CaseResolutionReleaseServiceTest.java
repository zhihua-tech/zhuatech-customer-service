/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class CaseResolutionReleaseServiceTest {
    private final CaseResolutionReleaseService service = new CaseResolutionReleaseService();

    @Test void resolvesVerifiedCustomerCase() {
        var result = service.assess(new CaseResolutionReleaseService.Request("CASE-100", true, true, true,
                false, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(CaseResolutionReleaseService.Decision.RESOLVE);
    }

    @Test void escalatesIncompleteClosureWork() {
        var result = service.assess(new CaseResolutionReleaseService.Request("CASE-101", true, true, true,
                true, false, true, false, false, false, true, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(CaseResolutionReleaseService.Decision.ESCALATE);
    }

    @Test void blocksUnsafeOrUncontrolledClosure() {
        var result = service.assess(new CaseResolutionReleaseService.Request("", false, false, false,
                true, false, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(7);
        assertThat(result.decision()).isEqualTo(CaseResolutionReleaseService.Decision.BLOCKED);
    }
}
