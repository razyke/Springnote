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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"user", "notes"})
@ToString(exclude = {"user", "notes"})
//InitalValue using 15, cos we use insert.sql and last id - 14.
@SequenceGenerator(
    name = "for-notebook", sequenceName = "notebook_with_insert",initialValue = 15, allocationSize = 1)
public class Notebook implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "for-notebook")
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column (nullable = false)
  private String title;

  @Column
  private String description;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "notebook", cascade = CascadeType.ALL,
  orphanRemoval = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Note> notes = new ArrayList<>();

}
