import { Component, OnInit } from '@angular/core';
import { GroupService } from './group.service';
import { Router, RouterLink } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Group } from '../../Models/Group';
import { NewAuthService } from '../Auths/new-auth.service';

@Component({
  selector: 'app-group',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './group.component.html',
  styleUrl: './group.component.css',
  providers: [GroupService]
})
export class GroupComponent implements OnInit {

  groups: Group[] = [];
  isTeacher : boolean = false;
  isStudent : boolean = false;
  isMemberOfTheGroup: Map<number, boolean> = new Map<number, boolean>();


  constructor(private groupService: GroupService, private router: Router,public authService : NewAuthService) { }

  ngOnInit() {
    this.isStudent = this.authService.isStudent();
    this.isTeacher = this.authService.isTeacher();

    const loggedUserId = this.authService.loggedUserId();

    this.groupService.getAll().subscribe(response => { 
      this.groups = response.content;
       //console.log(this.groups) ;
       //this.groups = this.groups.filter(group => group.students.some(student => student.id === +(loggedUserId!))); // work 
      
       //  this.groups = this.groups.filter(group => {
      //   if(group.students.some(student => student.id === +(loggedUserId!)) ||group.teachers.some(teacher => teacher.id === +(loggedUserId!)) ){
      //     this.isMemberOfTheGroup.set(group.id, true);
      //     console.log(group.id);
      //     return true;
      //   }
      //   return false;
      // }
      //   ); // work 
      });

    
  }
  
   isUserInGroup(groupId: number) {
    //if(groupId === 41)return ;
    const loggedUserId = this.authService.loggedUserId();
    if(this.groups.find(group => group.id === groupId)?.students.some(student => student.id === +(loggedUserId!)) || 
        this.groups.find(group => group.id === groupId)?.teachers.some(teacher => teacher.id === +(loggedUserId!)) ){
      return true;
    }
    return false;
  }

  groupTeachers(arg0: number) {
    this.router.navigateByUrl("/groups/" + arg0 + "/teachers" );
  }
  groupStudents(arg0: number) {
    this.router.navigateByUrl("/groups/" + arg0 + "/students" );
  }
  deleteGroup(groupId: number) {
    if(confirm("Are you sure to delete "+name)) {
      this.groupService.delete(groupId).subscribe(response => { this.groups = response.content; console.log(this.groups) });
      this.groups = this.groups.filter(group => group.id !== groupId);
    }
  
  }
  updateGroup(arg0: number) {
    throw new Error('Method not implemented.');
  }
  grouplink(arg0: any) {
    throw new Error('Method not implemented.');
  }

}

class content {
  group: Group[] = [];
}
