package com.github.tarunchaitu.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SshCredentials.
 */
@Entity
@Table(name = "ssh_credentials")
public class SshCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "ssh_key")
    private String sshKey;

    @OneToMany(mappedBy = "sshCredentials")
    private Set<SharedAgent> sharedAgents = new HashSet<>();

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

    public SshCredentials name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public SshCredentials username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public SshCredentials password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSshKey() {
        return sshKey;
    }

    public SshCredentials sshKey(String sshKey) {
        this.sshKey = sshKey;
        return this;
    }

    public void setSshKey(String sshKey) {
        this.sshKey = sshKey;
    }

    public Set<SharedAgent> getSharedAgents() {
        return sharedAgents;
    }

    public SshCredentials sharedAgents(Set<SharedAgent> sharedAgents) {
        this.sharedAgents = sharedAgents;
        return this;
    }

    public SshCredentials addSharedAgent(SharedAgent sharedAgent) {
        this.sharedAgents.add(sharedAgent);
        sharedAgent.setSshCredentials(this);
        return this;
    }

    public SshCredentials removeSharedAgent(SharedAgent sharedAgent) {
        this.sharedAgents.remove(sharedAgent);
        sharedAgent.setSshCredentials(null);
        return this;
    }

    public void setSharedAgents(Set<SharedAgent> sharedAgents) {
        this.sharedAgents = sharedAgents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SshCredentials)) {
            return false;
        }
        return id != null && id.equals(((SshCredentials) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SshCredentials{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", sshKey='" + getSshKey() + "'" +
            "}";
    }
}
