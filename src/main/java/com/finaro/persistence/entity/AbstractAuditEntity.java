package com.finaro.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractAuditEntity extends AbstractTimeAuditEntity {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;
}
