package com.after.winter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NOTEBOOK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notebook {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
  @JoinColumn
  private User user;

  @Column
  private String name;

  @Column
  private String description;

}
