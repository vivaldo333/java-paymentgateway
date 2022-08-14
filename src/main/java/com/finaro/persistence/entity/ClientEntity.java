package com.finaro.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "tb_client")
public class ClientEntity extends AbstractAuditEntity {
    @Serial
    private static final long serialVersionUID = 5014732604785530508L;

    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "cardholder")
    private Set<TransactionEntity> transactions = new HashSet<>();

    public void addTransaction(TransactionEntity transaction) {
        if (CollectionUtils.isNotEmpty(this.getTransactions())) {
            this.getTransactions().add(transaction);
        } else {
            this.setTransactions(Collections.singleton(transaction));
        }
    }
}
