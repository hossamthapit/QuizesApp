import { Exam } from "./Exam";

export class Question {

    public constructor(
        public id: number,
        public description: string,        
        public answer: string,        
        public score: number,   
        public exams: Exam,
        public seconds: number
    ) { }

}
