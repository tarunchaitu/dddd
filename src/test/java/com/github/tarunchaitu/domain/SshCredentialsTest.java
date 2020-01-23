package com.github.tarunchaitu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.tarunchaitu.web.rest.TestUtil;

public class SshCredentialsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SshCredentials.class);
        SshCredentials sshCredentials1 = new SshCredentials();
        sshCredentials1.setId(1L);
        SshCredentials sshCredentials2 = new SshCredentials();
        sshCredentials2.setId(sshCredentials1.getId());
        assertThat(sshCredentials1).isEqualTo(sshCredentials2);
        sshCredentials2.setId(2L);
        assertThat(sshCredentials1).isNotEqualTo(sshCredentials2);
        sshCredentials1.setId(null);
        assertThat(sshCredentials1).isNotEqualTo(sshCredentials2);
    }
}
