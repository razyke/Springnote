package com.after.winter.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@ToString(exclude = "notes")
public class Mark implements Serializable {

  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String type;


  @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "marks", cascade = CascadeType.ALL)
  private List<Note> notes = new ArrayList<>();

}
