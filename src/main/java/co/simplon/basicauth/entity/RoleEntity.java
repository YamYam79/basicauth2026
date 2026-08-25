package co.simplon.basicauth.entity;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
//RoleEntity implémente l'interface GrantedAuthority, utilisée par Spring Security pour représenter les rôles ou permissions.
public class RoleEntity implements GrantedAuthority {
    @Id
    private String authority;

    public RoleEntity() {
    }

    @Override
    public String getAuthority() {
        // permet à Spring Security de connaître le nom correspondant au role
        return this.authority;
    }
//La méthode getAuthority retourne le nom du rôle.
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}