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
@Table(name = "NOTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Notebook.class)
  @JoinColumn
  private Notebook notebook;

  @Column
  private String title;

  @Column
  private String body;

}
