package com.ace.studentregistrationjpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Course {
  @Id
  private String id;
  private String name;

  public Course(String name) {
    super();
    this.name = name;
  }
}
