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


  constructor(private groupService: GroupService, private router: Router,public authService : NewAuthService) { }

  ngOnInit() {
    this.isStudent = this.authService.isStudent();
    this.isTeacher = this.authService.isTeacher();
    
    this.groupService.getAll().subscribe(response => { this.groups = response.content; console.log(this.groups) });
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
