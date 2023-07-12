package com.example.springsecurityjwtsecurity.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "user1234")
public class User {
@Column(name = "id")
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "email")
    private String email;
@Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
