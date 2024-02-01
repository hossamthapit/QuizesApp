import { Routes } from '@angular/router';
import { GroupComponent } from './group/group.component';
import { StudentComponent } from './group/user/student/student.component';
import { NewStudentComponent } from './group/user/student/new-student/new-student.component';
import { TeacherComponent } from './group/user/teacher/teacher.component';
import { TeacherInfoComponent } from './group/user/teacher/teacher-info/teacher-info.component';
import { ExamComponent } from './group/exam/exam.component';
import { QuestionComponent } from './group/exam/question/question.component';
import { QuestionDetailsComponent } from './group/exam/question/question-details/question-details.component';
import { ExamDetailsComponent } from './group/exam/exam-details/exam-details.component';
import { UserProfileComponent } from './group/user-profile/user-profile.component';
import { ExamProcessComponent } from './group/exam/exam-process/exam-process.component';
import { ExamResultComponent } from './group/exam/exam-process/exam-result/exam-result.component';
import { GroupDetailsComponent } from './group/group-details/group-details.component';
import { CanActivateRouteGuardService } from './Guard/can-activate-route-guard.service';
import { LoggingComponent } from './Component/logging/logging.component';

export const routes: Routes = [

    { path: '', component: LoggingComponent },

    { path: 'students', component: StudentComponent ,canActivate: [CanActivateRouteGuardService] },
    { path: 'students/new', component: NewStudentComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'students/:id/edit', component: NewStudentComponent ,canActivate: [CanActivateRouteGuardService] },
    
    { path: 'teachers', component: TeacherComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'teachers/new', component: TeacherInfoComponent ,canActivate: [CanActivateRouteGuardService] },
    { path: 'teachers/:id/edit', component: TeacherInfoComponent,canActivate: [CanActivateRouteGuardService]  },
    
    { path: 'questions', component: QuestionComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'questions/:id/edit', component: QuestionDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    
    { path: 'exams', component: ExamComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'exams/new', component: ExamDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'exams/:id/questions', component: QuestionComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'exams/:examId/students/:studentId/start', component: ExamProcessComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'exams/:examId/questions/new', component: QuestionDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'exams/:examId/result', component: ExamResultComponent,canActivate: [CanActivateRouteGuardService]  },
    
    { path: 'groups', component: GroupComponent,canActivate: [CanActivateRouteGuardService] },
    { path: 'groups/:id/teachers', component: TeacherComponent ,canActivate: [CanActivateRouteGuardService] },
    { path: 'groups/:id/students', component: StudentComponent ,canActivate: [CanActivateRouteGuardService] },
    { path: 'groups/new', component: GroupDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'groups/:groupId/edit', component: GroupDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'groups/:groupId/exams', component: ExamComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: 'groups/:groupId/exams/new', component: ExamDetailsComponent ,canActivate: [CanActivateRouteGuardService] },
    { path: 'groups/:groupId/exams/:examId/edit', component: ExamDetailsComponent,canActivate: [CanActivateRouteGuardService]  },
    
    { path: 'user/:userId/profile', component: UserProfileComponent,canActivate: [CanActivateRouteGuardService]  },
    { path: '**', component: LoggingComponent, canActivate: [CanActivateRouteGuardService] },
    
    
];
