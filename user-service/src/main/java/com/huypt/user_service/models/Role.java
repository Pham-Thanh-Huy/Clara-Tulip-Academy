package com.huypt.user_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    @JsonIgnore
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Resource> resources = new ArrayList<>();
}
