package com.after.winter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
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
//InitalValue using 8, cos we use insert.sql and last id - 7.
@SequenceGenerator(
    name = "for-user", sequenceName = "user_with_insert",initialValue = 8, allocationSize = 1)
public class User implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "for-user")
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
