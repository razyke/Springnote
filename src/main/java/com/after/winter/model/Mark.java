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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
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
@ToString(exclude = {"notes"})
//InitalValue using 9, cos we use insert.sql and last id - 8.
@SequenceGenerator(
    name = "for-mark", sequenceName = "mark_with_insert",initialValue = 9, allocationSize = 1)
public class Mark implements Serializable {

  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "for-mark")
  private Long id;

  @Column
  private String type;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "marks")
  private List<Note> notes = new ArrayList<>();

}
