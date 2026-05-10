package org.psembass.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.psembass.financeflow.enums.CategoryType;

@Entity
@Table(name ="categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CategoryType type;
}
