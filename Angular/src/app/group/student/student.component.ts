import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Student } from '../../../Models/Student';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { StudentService } from './student.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Group } from '../../../Models/Group';
import { GroupService } from '../group.service';
import { NewAuthService } from '../../Auths/new-auth.service';

@Component({
  selector: 'app-student',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './student.component.html',
  styleUrl: './student.component.css',
})
export class StudentComponent implements OnInit {
  addNewStudent() {

  }

  isTeacher: boolean = false;
  isAdmin: boolean = false;
  isStudent: boolean = false;
  students: Student[] = [];
  group!: Group;
  addingStudentsToGroup: boolean = false;

  constructor(private studentService: StudentService, private router: Router,
    private route: ActivatedRoute, private groupService: GroupService, public authService: NewAuthService) { }

  ngOnInit() {

    this.isTeacher = this.authService.isTeacher();
    this.isStudent = this.authService.isStudent();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.groupService.get(+params['id']).subscribe(response => {
          this.group = response;
          // existing group id
          if (this.group != undefined) {
            this.studentService.getGroupStudents(this.group.id).subscribe(response => {
              this.students = response.content;
            });
          }
          // wrong group id redirect to groups page or make an error page
          else {

          }
        })
      }
      // Get All Students No Group Id found
      else {
        this.studentService.getAll().subscribe(response => {
          this.students = response.content;
        });
      }
    });
  }

  deleteStudentFromGroup(studentId: number) {
    if (this.group != null) {
      this.studentService.deleteStudentFromGroup(this.group.id, studentId).subscribe(response => {
        this.students = this.students.filter(student => student.id !== studentId);
        this.group.students = this.students
      });
    }
    else {
      if(confirm("Are you sure to delete "+name)) {

      this.studentService.delete(studentId).subscribe(response => {
        this.students = this.students.filter(student => student.id !== studentId);
      });
    }
    }
  }
  editStudent(studentId: number) {
    this.router.navigateByUrl("/students/" + studentId + "/edit");
  }
  addStudentsToGroup(studentId: number) {

    if (this.group != null) {
      this.studentService.getAll().subscribe(allStudents => {
        this.students = allStudents.content;

        // Filter out students already in this.group.students from this.students
        if (this.group.students && this.group.students.length > 0) {
          this.students = this.students.filter(student => !this.group.students.some(groupStudent => groupStudent.id === student.id));
        }
        this.addingStudentsToGroup = true;
      });
    }
  }

  addThisStudentToTheGroup(studentId: number) {
    if (this.group != null) {
      this.studentService.postStudentToGroup(this.group.id, studentId).subscribe(response => {
        this.students = this.students.filter(student => student.id !== studentId);
      });
    }
  }

  backToGroup(arg0: number) {
    this.addingStudentsToGroup = false;
    this.ngOnInit();
  }


}

class content {
  groups: Group[] = [];
  students: Student[] = [];
}
