package com.after.winter.model;

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
@Table(name = "NOTEMARK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteMark {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Note.class)
  @JoinColumn(name = "id_note")
  private Note note;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Mark.class)
  @JoinColumn(name = "id_mark")
  private Mark mark;
}
