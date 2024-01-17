import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TeacherService } from './teacher.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Group } from '../../../../Models/Group';
import { GroupService } from '../../group.service';
import { Teacher } from '../../../../Models/Teacher';
import { NewAuthService } from '../../../Auths/new-auth.service';

@Component({
  selector: 'app-teacher',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './teacher.component.html',
  styleUrl: './teacher.component.css',
})
export class TeacherComponent implements OnInit {
  

  isTeacher : boolean = false;
  isAdmin : boolean = false;
  teachers: Teacher[] = [];
  group!: Group;
  addingTeachersToGroup: boolean = false;
  
  constructor(private teacherService: TeacherService, private router: Router,
    private route: ActivatedRoute, private groupService: GroupService, public authService : NewAuthService) { }
    
    ngOnInit() {

      this.isTeacher = this.authService.isTeacher();
      this.isAdmin = this.authService.isAdmin();
      
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.groupService.get(+params['id']).subscribe(response => {
            this.group = response;
            // existing group id
            if (this.group != undefined) {
            this.teacherService.getGroupTeachers(this.group.id).subscribe(response => {
              this.teachers = response.content;
            });
          }
          // wrong group id redirect to groups page or make an error page
          else {
            
          }
        })
      }
      // Get All teacher No Group Id found
      else {
        this.teacherService.getAll().subscribe(response => {
          this.teachers = response.content;
        });
      }
    });
  }

  deleteTeacherFromGroup(teacherId: number) {
    if (this.group != null) {
    this.teacherService.deleteTeacherFromGroup(this.group.id, teacherId).subscribe(response => {
      this.teachers = this.teachers.filter(teacher => teacher.id !== teacherId);
      this.group.teachers = this.teachers
    });
    }
    else {
      if(confirm("Are you sure to delete "+name)) {

      this.teacherService.delete(teacherId).subscribe(response => {
        this.teachers = this.teachers.filter(teacher => teacher.id !== teacherId);
      });
    }
    }
  }
  editTeacher(teacherId: number) {
    this.router.navigateByUrl("/teachers/" + teacherId + "/edit");
  }
  addTeachersToGroup(teacherId: number) {
    
    console.log("asd");
    if (this.group != null) {
      this.teacherService.getAll().subscribe(allTeachers => {
        this.teachers = allTeachers.content;
        
        // Filter out teachers already in this.group.teachers from this.teachers
        if (this.group.teachers && this.group.teachers.length > 0) {
          this.teachers = this.teachers.filter(teacher => !this.group.teachers.some(groupTeachers => groupTeachers.id === teacher.id));
        }
        this.addingTeachersToGroup = true;
      });
    }
  }

  addThisTeacherToTheGroup(teacherId: number) {
    if (this.group != null) {
      this.teacherService.postTeacherToGroup(this.group.id, teacherId).subscribe(response => {
        this.teachers = this.teachers.filter(teacher => teacher.id !== teacherId);
      });
    }
  }

  backToGroup(arg0: number) {
    this.addingTeachersToGroup = false;
    this.ngOnInit();
  }
    
  
}

class content {
  group: Group[] = [];
}
