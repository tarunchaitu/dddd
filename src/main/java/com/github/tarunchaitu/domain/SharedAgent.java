package com.github.tarunchaitu.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.github.tarunchaitu.domain.enumeration.AgentState;

import com.github.tarunchaitu.domain.enumeration.BootstrapMethod;

import com.github.tarunchaitu.domain.enumeration.Os;

/**
 * A SharedAgent.
 */
@Entity
@Table(name = "shared_agent")
public class SharedAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "no_of_executors", nullable = false)
    private Integer noOfExecutors;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "workspace", nullable = false)
    private String workspace;

    @NotNull
    @Column(name = "dns_name", nullable = false)
    private String dnsName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "agent_state", nullable = false)
    private AgentState agentState;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bootstrap_metod", nullable = false)
    private BootstrapMethod bootstrapMetod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    private Os os;

    @Column(name = "ssh_port")
    private Integer sshPort;

    @Column(name = "jvm_options")
    private String jvmOptions;

    @ManyToOne
    @JsonIgnoreProperties("sharedAgents")
    private SshCredentials sshCredentials;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SharedAgent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SharedAgent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfExecutors() {
        return noOfExecutors;
    }

    public SharedAgent noOfExecutors(Integer noOfExecutors) {
        this.noOfExecutors = noOfExecutors;
        return this;
    }

    public void setNoOfExecutors(Integer noOfExecutors) {
        this.noOfExecutors = noOfExecutors;
    }

    public String getLabel() {
        return label;
    }

    public SharedAgent label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWorkspace() {
        return workspace;
    }

    public SharedAgent workspace(String workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getDnsName() {
        return dnsName;
    }

    public SharedAgent dnsName(String dnsName) {
        this.dnsName = dnsName;
        return this;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public AgentState getAgentState() {
        return agentState;
    }

    public SharedAgent agentState(AgentState agentState) {
        this.agentState = agentState;
        return this;
    }

    public void setAgentState(AgentState agentState) {
        this.agentState = agentState;
    }

    public BootstrapMethod getBootstrapMetod() {
        return bootstrapMetod;
    }

    public SharedAgent bootstrapMetod(BootstrapMethod bootstrapMetod) {
        this.bootstrapMetod = bootstrapMetod;
        return this;
    }

    public void setBootstrapMetod(BootstrapMethod bootstrapMetod) {
        this.bootstrapMetod = bootstrapMetod;
    }

    public Os getOs() {
        return os;
    }

    public SharedAgent os(Os os) {
        this.os = os;
        return this;
    }

    public void setOs(Os os) {
        this.os = os;
    }

    public Integer getSshPort() {
        return sshPort;
    }

    public SharedAgent sshPort(Integer sshPort) {
        this.sshPort = sshPort;
        return this;
    }

    public void setSshPort(Integer sshPort) {
        this.sshPort = sshPort;
    }

    public String getJvmOptions() {
        return jvmOptions;
    }

    public SharedAgent jvmOptions(String jvmOptions) {
        this.jvmOptions = jvmOptions;
        return this;
    }

    public void setJvmOptions(String jvmOptions) {
        this.jvmOptions = jvmOptions;
    }

    public SshCredentials getSshCredentials() {
        return sshCredentials;
    }

    public SharedAgent sshCredentials(SshCredentials sshCredentials) {
        this.sshCredentials = sshCredentials;
        return this;
    }

    public void setSshCredentials(SshCredentials sshCredentials) {
        this.sshCredentials = sshCredentials;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SharedAgent)) {
            return false;
        }
        return id != null && id.equals(((SharedAgent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SharedAgent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", noOfExecutors=" + getNoOfExecutors() +
            ", label='" + getLabel() + "'" +
            ", workspace='" + getWorkspace() + "'" +
            ", dnsName='" + getDnsName() + "'" +
            ", agentState='" + getAgentState() + "'" +
            ", bootstrapMetod='" + getBootstrapMetod() + "'" +
            ", os='" + getOs() + "'" +
            ", sshPort=" + getSshPort() +
            ", jvmOptions='" + getJvmOptions() + "'" +
            "}";
    }
}
