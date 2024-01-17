import { Group } from "./Group";
import { Question } from "./Question";

export class Exam {

    public constructor(
        public id: number,
        public title: string,        
        public description: string,   
        public questions: Question[], 
        public group : Group
    ) { }

}
