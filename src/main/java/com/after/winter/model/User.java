package com.after.winter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String firstname;

  @Column
  private String lastname;

  @Column (unique = true)
  private String email;

  @JsonIgnore
  @Column
  private String password;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL,
  orphanRemoval = true)
  private List<Notebook> notebooks = new ArrayList<>();

}
