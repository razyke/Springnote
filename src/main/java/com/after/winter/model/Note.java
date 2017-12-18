package com.after.winter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"notebook", "marks"})
@ToString(exclude = {"notebook", "marks"})
//InitalValue using 11, cos we use insert.sql and last id - 10.
@SequenceGenerator(
    name = "for-note", sequenceName = "note_with_insert",initialValue = 11, allocationSize = 1)
public class Note implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "for-note")
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Notebook.class)
  @JoinColumn(name = "notebook_id", nullable = false)
  private Notebook notebook;

  @Column (nullable = false)
  private String title;

  @Column
  private String body;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "note_mark",
      joinColumns = @JoinColumn(name = "note_id"),
      inverseJoinColumns = @JoinColumn(name = "mark_id"))
  private List<Mark> marks = new ArrayList<>();

}
