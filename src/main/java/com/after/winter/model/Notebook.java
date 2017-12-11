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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "NOTEBOOK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "notes"})
public class Notebook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
  @JoinColumn(name = "id_user", nullable = false)
  private User user;

  @Column(unique = true)
  private String title;

  @Column
  private String description;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "notebook", cascade = CascadeType.ALL,
  orphanRemoval = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Note> notes = new ArrayList<>();

}