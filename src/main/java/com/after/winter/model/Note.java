package com.after.winter.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "NOTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"notebook", "marks"})
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Notebook.class)
  @JoinColumn(name = "id_notebook")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Notebook notebook;

  @Column(unique = true)
  private String title;

  @Column
  private String body;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "note_mark",
      joinColumns = @JoinColumn(name = "id_note"),
      inverseJoinColumns = @JoinColumn(name = "id_mark"))
  private List<Mark> marks = new ArrayList<>();

}