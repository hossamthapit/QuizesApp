import { Exam } from "./Exam";
import { Student } from "./Student";

export class ExamRecord {

    public constructor(
        public id: number,
        public score: number,   
        public student : Student,
        public exam : Exam,
        public examDate: Date
    ) { }

}
