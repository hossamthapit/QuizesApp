import { Component } from '@angular/core';
import { ExamService } from '../../exam.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-exam-result',
  standalone: true,
  imports: [],
  templateUrl: './exam-result.component.html',
  styleUrl: './exam-result.component.css',
})
export class ExamResultComponent {

  score : number = 0;

  constructor(private examService: ExamService, private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      console.log("score : ",params['score']);
      if (params['score']) {
        this.score = +params['score'];
      }
    })
  }
}
