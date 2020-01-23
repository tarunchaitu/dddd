package com.github.tarunchaitu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.tarunchaitu.web.rest.TestUtil;

public class SharedAgentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharedAgent.class);
        SharedAgent sharedAgent1 = new SharedAgent();
        sharedAgent1.setId(1L);
        SharedAgent sharedAgent2 = new SharedAgent();
        sharedAgent2.setId(sharedAgent1.getId());
        assertThat(sharedAgent1).isEqualTo(sharedAgent2);
        sharedAgent2.setId(2L);
        assertThat(sharedAgent1).isNotEqualTo(sharedAgent2);
        sharedAgent1.setId(null);
        assertThat(sharedAgent1).isNotEqualTo(sharedAgent2);
    }
}
