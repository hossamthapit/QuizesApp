import { Component } from '@angular/core';
import { GroupService } from '../group.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Group } from '../../../Models/Group';
import { NewAuthService } from '../../Auths/new-auth.service';

@Component({
  selector: 'app-group-details',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, ReactiveFormsModule],
  templateUrl: './group-details.component.html',
  styleUrl: './group-details.component.css',
})
export class GroupDetailsComponent {

  groupForm!: FormGroup;
  group: Group = {} as Group;
  groupId !: number;
  editing: boolean = false;


  constructor(private formBuilder: FormBuilder, 
    private route: ActivatedRoute,private router: Router,private groupService: GroupService, public authService : NewAuthService) { }

  ngOnInit(): void {

    console.log("Group details ngOnInit() called");
    this.groupForm = this.formBuilder.group({
      id: 0,
      description: ['', Validators.required],
      title: ['', Validators.required],
    });

    this.route.params.subscribe(params => {
      if (params['groupId']) {
        this.groupId = +params['groupId'];
        this.groupService.get(this.groupId).subscribe(response => {
          console.log("group: ",response);
          this.groupForm.patchValue(response);
        })
      }
    })

    this.route.url.subscribe(segments => {
      const urlSegments = segments.map(segment => segment.path);
      const isEditRoute = urlSegments.includes('edit');
      if (isEditRoute) {
        this.editing = true;
      }
    });
  

  }

  onSubmit(): void {
    console.log("group details onSubmit() called",this.editing);
    if (this.groupForm.valid) {

      this.group = this.groupForm.value;

      if(this.editing){
        this.groupService.put(this.group).subscribe(response => {
          console.log("edited group : ",response);
          this.group = response;
        });
      }
      else {
        this.groupService.post(this.group).subscribe(response => {
          console.log("saved group : ",response);
        });
      }
      // this.router.navigateByUrl('/exams');
      this.groupForm.reset();
    } else {
      // Handle form validation errors or incomplete form
    }
  }


  

}
