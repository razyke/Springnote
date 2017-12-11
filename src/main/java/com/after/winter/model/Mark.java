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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mark")
public class Mark {

  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String type;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "marks", cascade = CascadeType.ALL)
  private List<Note> notes = new ArrayList<>();

}
